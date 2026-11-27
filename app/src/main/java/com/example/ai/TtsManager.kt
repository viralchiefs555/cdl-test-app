package com.example.ai

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.UUID

private const val TAG = "TtsManager"

/**
 * Coach Mac's voice. Uses Gemini's studio-quality voice when the AI connection works, and the
 * best voice installed on the phone otherwise (or when the student picks a phone voice).
 */
class TtsManager(context: Context) : TextToSpeech.OnInitListener {

    companion object {
        private const val PREFS = "coach_voice_prefs"
        private const val KEY_VOICE = "voice_choice"
        const val GEMINI_CHOICE = "gemini"

        /** After the studio voice fails, use the phone voice for this long before trying again. */
        private const val GEMINI_RETRY_MS = 5 * 60_000L

        fun cleanMarkdownForSpeech(text: String): String {
            return text
                .replace(Regex("""[*#_~`>]"""), "") // Remove markdown formatting characters
                .replace(Regex("""\[(.*?)\]\(.*?\)"""), "$1") // Simplify links
                .replace(Regex("""^[\s-•*]+""", RegexOption.MULTILINE), "") // Remove bullet dashes
                .replace(Regex("""\n+"""), ". ") // Replace line breaks with pause periods
                .replace(Regex("""\s+"""), " ") // Normalize spaces
                .replace("PSI", "P S I") // Spell out PSI so TTS doesn't say "sigh"
                .replace("psi", "P S I")
                .replace("FMCSA", "F M C S A")
                .replace("FMCSR", "F M C S R")
                .replace("DOT", "D O T")
                .replace("CDL", "C D L")
                .replace("mph", "miles per hour")
                .replace("min.", "minutes")
                .trim()
        }
    }

    private val appContext = context.applicationContext
    private val prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private var tts: TextToSpeech? = TextToSpeech(appContext, this)

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _speechRate = MutableStateFlow(1.0f)
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _currentUtteranceId = MutableStateFlow<String?>(null)
    val currentUtteranceId: StateFlow<String?> = _currentUtteranceId.asStateFlow()

    /** What the voice picker shows, e.g. "Studio voice (Gemini)" or "Phone voice 2". */
    private val _voiceLabel = MutableStateFlow("Studio voice (Gemini)")
    val voiceLabel: StateFlow<String> = _voiceLabel.asStateFlow()

    private var pendingSpeech: Pair<String, Int>? = null

    // Voice choice: GEMINI_CHOICE, or the name of an installed phone voice.
    private var voiceChoice: String = prefs.getString(KEY_VOICE, GEMINI_CHOICE) ?: GEMINI_CHOICE
    private var phoneVoices: List<Voice> = emptyList()

    // Studio voice playback.
    private val geminiVoice = GeminiVoice()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var geminiQueue = Channel<String>(Channel.UNLIMITED)
    private var geminiJob: Job? = null
    @Volatile private var geminiBlockedUntil = 0L

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            val isUsSupported = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
            if (!isUsSupported) {
                val fallback = tts?.setLanguage(Locale.getDefault())
                Log.w(TAG, "US English language not pre-installed, fell back to default locale ($fallback).")
            }
            try {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
                tts?.setAudioAttributes(audioAttributes)
            } catch (e: Exception) {
                Log.w(TAG, "Could not set audio attributes on TTS: ${e.message}")
            }
            loadPhoneVoices()
            applyPhoneVoice()
            refreshVoiceLabel()
            _isReady.value = true
            tts?.setSpeechRate(_speechRate.value)
            setupProgressListener()
            Log.d(TAG, "TextToSpeech engine initialized successfully.")

            // Immediately speak any text that was requested before TTS finished initializing
            pendingSpeech?.let { (pendingText, mode) ->
                pendingSpeech = null
                speakWithPhone(pendingText, mode)
            }
        } else {
            Log.e(TAG, "TextToSpeech initialization failed with status $status.")
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Phone voices: pick the best-sounding US English voice installed on the device.
    // ---------------------------------------------------------------------------------------------

    private fun loadPhoneVoices() {
        val all: List<Voice> = try {
            tts?.voices?.toList() ?: emptyList()
        } catch (e: Exception) {
            Log.w(TAG, "Could not list TTS voices", e)
            emptyList()
        }
        val english = all.filter { v ->
            v.locale.language == "en" &&
                v.locale.country.equals("US", ignoreCase = true) &&
                v.features?.contains(TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED) != true
        }
        // Prefer voices that work offline, so Coach Mac never goes silent without internet.
        val offline = english.filter { !it.isNetworkConnectionRequired }
        phoneVoices = offline.ifEmpty { english }
            .sortedWith(
                compareByDescending<Voice> { it.quality }
                    .thenBy { preferredRank(it.name) }
                    .thenBy { it.latency }
            )
            .distinctBy { it.name }
            .take(6)
    }

    /** Google's deeper voices first; they suit a truck-driving coach. */
    private fun preferredRank(name: String): Int {
        val n = name.lowercase()
        val preferred = listOf("tpd", "iom", "iol")
        val idx = preferred.indexOfFirst { n.contains(it) }
        return if (idx >= 0) idx else preferred.size
    }

    private fun applyPhoneVoice() {
        val chosen = phoneVoices.firstOrNull { it.name == voiceChoice } ?: phoneVoices.firstOrNull() ?: return
        try {
            tts?.voice = chosen
        } catch (e: Exception) {
            Log.w(TAG, "Could not set TTS voice ${chosen.name}", e)
        }
    }

    private fun refreshVoiceLabel() {
        _voiceLabel.value = if (voiceChoice == GEMINI_CHOICE) {
            if (System.currentTimeMillis() < geminiBlockedUntil) "Studio voice (offline, using phone voice)" else "Studio voice (Gemini)"
        } else {
            val i = phoneVoices.indexOfFirst { it.name == voiceChoice }
            if (i >= 0) {
                val hd = if (phoneVoices[i].quality >= Voice.QUALITY_HIGH) " HD" else ""
                "Phone voice ${i + 1}$hd"
            } else {
                "Phone voice"
            }
        }
    }

    /** Moves to the next voice (studio voice, then each good phone voice) and returns its label. */
    fun cycleVoice(): String {
        val options = listOf(GEMINI_CHOICE) + phoneVoices.map { it.name }
        val current = options.indexOf(voiceChoice).coerceAtLeast(0)
        voiceChoice = options[(current + 1) % options.size]
        prefs.edit().putString(KEY_VOICE, voiceChoice).apply()
        if (voiceChoice == GEMINI_CHOICE) {
            geminiBlockedUntil = 0L // give the studio voice a fresh try
        }
        stop()
        applyPhoneVoice()
        refreshVoiceLabel()
        return _voiceLabel.value
    }

    val isStudioVoiceSelected: Boolean
        get() = voiceChoice == GEMINI_CHOICE

    private fun hasNetwork(): Boolean {
        return try {
            val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return true
            val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } catch (e: Exception) {
            true
        }
    }

    private fun useStudioVoice(): Boolean {
        return voiceChoice == GEMINI_CHOICE &&
            System.currentTimeMillis() >= geminiBlockedUntil &&
            hasNetwork()
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isSpeaking.value = true
                _currentUtteranceId.value = utteranceId
            }

            override fun onDone(utteranceId: String?) {
                _isSpeaking.value = false
                _currentUtteranceId.value = null
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _isSpeaking.value = false
                _currentUtteranceId.value = null
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                _isSpeaking.value = false
                _currentUtteranceId.value = null
                Log.e(TAG, "TTS utterance error: $errorCode for $utteranceId")
            }
        })
    }

    fun speak(
        text: String,
        queueMode: Int = TextToSpeech.QUEUE_FLUSH,
        utteranceId: String = UUID.randomUUID().toString()
    ) {
        if (_isMuted.value) return
        val cleanText = cleanMarkdownForSpeech(text)
        if (cleanText.isBlank()) return

        if (useStudioVoice()) {
            if (queueMode == TextToSpeech.QUEUE_FLUSH) {
                stopStudioVoice()
                try {
                    tts?.stop()
                } catch (_: Exception) {
                }
            }
            enqueueStudio(cleanText)
            return
        }
        if (queueMode == TextToSpeech.QUEUE_FLUSH) stopStudioVoice()
        speakWithPhone(cleanText, queueMode, utteranceId)
    }

    private fun speakWithPhone(
        cleanText: String,
        queueMode: Int,
        utteranceId: String = UUID.randomUUID().toString()
    ) {
        if (!_isReady.value) {
            Log.d(TAG, "TTS not ready yet, buffering pending speech for when engine finishes init.")
            pendingSpeech = Pair(cleanText, queueMode)
            return
        }

        try {
            tts?.setSpeechRate(_speechRate.value)
            val params = android.os.Bundle()
            tts?.speak(cleanText, queueMode, params, utteranceId)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to speak text", e)
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Studio voice: each piece of text is turned into audio by Gemini and played in order. The next
    // piece is fetched while the current one plays, so sentences follow each other without gaps.
    // ---------------------------------------------------------------------------------------------

    private fun enqueueStudio(text: String) {
        geminiQueue.trySend(text)
        if (geminiJob?.isActive != true) {
            startStudioWorker(geminiQueue)
        }
    }

    private fun startStudioWorker(queue: Channel<String>) {
        geminiJob = scope.launch {
            var prefetched: Pair<String, Deferred<GeminiVoice.Pcm>>? = null
            try {
                while (isActive) {
                    val current = prefetched ?: run {
                        val waiting = queue.tryReceive().getOrNull()
                        val text = if (waiting != null) {
                            waiting
                        } else {
                            _isSpeaking.value = false
                            queue.receive()
                        }
                        text to scope.async { geminiVoice.synthesize(text) }
                    }
                    prefetched = null
                    _isSpeaking.value = true

                    val pcm = try {
                        current.second.await()
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Log.w(TAG, "Studio voice unavailable, switching to the phone voice", e)
                        geminiBlockedUntil = System.currentTimeMillis() + GEMINI_RETRY_MS
                        refreshVoiceLabel()
                        val rest = mutableListOf(current.first)
                        while (true) {
                            rest.add(queue.tryReceive().getOrNull() ?: break)
                        }
                        _isSpeaking.value = false
                        rest.forEachIndexed { i, t ->
                            speakWithPhone(t, if (i == 0) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD)
                        }
                        return@launch
                    }

                    queue.tryReceive().getOrNull()?.let { next ->
                        prefetched = next to scope.async { geminiVoice.synthesize(next) }
                    }
                    playPcm(pcm)
                }
            } catch (e: CancellationException) {
                prefetched?.second?.cancel()
                throw e
            } catch (e: Exception) {
                // The queue was closed by stop(); nothing left to say.
                prefetched?.second?.cancel()
            } finally {
                if (geminiJob == null || geminiJob?.isActive != true) _isSpeaking.value = false
            }
        }
    }

    private suspend fun playPcm(pcm: GeminiVoice.Pcm) = withContext(Dispatchers.IO) {
        val minBuffer = AudioTrack.getMinBufferSize(
            pcm.sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(pcm.sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(maxOf(minBuffer, 8_192))
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()
        try {
            val rate = _speechRate.value
            if (rate != 1.0f) {
                try {
                    track.playbackParams = track.playbackParams.setSpeed(rate)
                } catch (e: Exception) {
                    Log.w(TAG, "Could not change studio voice speed", e)
                }
            }
            track.play()
            val data = pcm.data
            var offset = 0
            while (offset < data.size && isActive) {
                val written = track.write(data, offset, minOf(4_096, data.size - offset))
                if (written <= 0) break
                offset += written
            }
            // Wait until the last sample has actually played.
            val totalFrames = data.size / 2
            val expectedMs = (totalFrames * 1000L / pcm.sampleRate / rate.coerceAtLeast(0.5f)).toLong()
            val deadline = System.currentTimeMillis() + expectedMs + 1_500L
            while (isActive && track.playbackHeadPosition < totalFrames && System.currentTimeMillis() < deadline) {
                delay(30)
            }
        } finally {
            try {
                track.stop()
            } catch (_: Exception) {
            }
            track.release()
        }
    }

    private fun stopStudioVoice() {
        geminiJob?.cancel()
        geminiJob = null
        geminiQueue.close()
        geminiQueue = Channel(Channel.UNLIMITED)
    }

    fun stop() {
        try {
            stopStudioVoice()
            tts?.stop()
            _isSpeaking.value = false
            _currentUtteranceId.value = null
        } catch (e: Exception) {
            Log.e(TAG, "Failed to stop TTS", e)
        }
    }

    fun toggleMute() {
        val newMute = !_isMuted.value
        _isMuted.value = newMute
        if (newMute) {
            stop()
        }
    }

    fun cycleSpeed(): Float {
        val current = _speechRate.value
        val newRate = when {
            current < 1.0f -> 1.0f
            current < 1.25f -> 1.25f
            current < 1.5f -> 1.5f
            else -> 0.9f
        }
        _speechRate.value = newRate
        tts?.setSpeechRate(newRate)
        return newRate
    }

    fun shutdown() {
        try {
            stopStudioVoice()
            scope.cancel()
            tts?.stop()
            tts?.shutdown()
            tts = null
            _isReady.value = false
            _isSpeaking.value = false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to shutdown TTS", e)
        }
    }
}

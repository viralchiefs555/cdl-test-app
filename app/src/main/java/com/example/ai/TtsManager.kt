package com.example.ai

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import java.util.UUID

private const val TAG = "TtsManager"

class TtsManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)

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

    private var pendingSpeech: Pair<String, Int>? = null

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            val isUsSupported = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
            if (!isUsSupported) {
                val fallback = tts?.setLanguage(Locale.getDefault())
                Log.w(TAG, "US English language not pre-installed, fell back to default locale ($fallback).")
            }
            try {
                val audioAttributes = android.media.AudioAttributes.Builder()
                    .setUsage(android.media.AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                    .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
                tts?.setAudioAttributes(audioAttributes)
            } catch (e: Exception) {
                Log.w(TAG, "Could not set audio attributes on TTS: ${e.message}")
            }
            _isReady.value = true
            tts?.setSpeechRate(_speechRate.value)
            setupProgressListener()
            Log.d(TAG, "TextToSpeech engine initialized successfully.")

            // Immediately speak any text that was requested before TTS finished initializing
            pendingSpeech?.let { (pendingText, mode) ->
                pendingSpeech = null
                speak(pendingText, mode)
            }
        } else {
            Log.e(TAG, "TextToSpeech initialization failed with status $status.")
        }
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

    fun stop() {
        try {
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
            tts?.stop()
            tts?.shutdown()
            tts = null
            _isReady.value = false
            _isSpeaking.value = false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to shutdown TTS", e)
        }
    }

    companion object {
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
}

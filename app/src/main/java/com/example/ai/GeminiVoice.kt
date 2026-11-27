package com.example.ai

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.InlineDataPart
import com.google.firebase.ai.type.PublicPreviewAPI
import com.google.firebase.ai.type.ResponseModality
import com.google.firebase.ai.type.SpeechConfig
import com.google.firebase.ai.type.Voice
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.withTimeout

/**
 * Coach Mac's studio-quality voice: turns text into speech with a Gemini text-to-speech model
 * through Firebase AI Logic. Needs the same Firebase connection as the AI answers; when that
 * fails, TtsManager falls back to the best voice installed on the phone.
 */
@OptIn(PublicPreviewAPI::class)
class GeminiVoice {

    companion object {
        const val TTS_MODEL_NAME = "gemini-3.1-flash-tts-preview"
        const val VOICE_NAME = "Charon"
        private const val DEFAULT_SAMPLE_RATE = 24_000
    }

    /** 16-bit mono PCM audio and its sample rate. */
    class Pcm(val data: ByteArray, val sampleRate: Int)

    private val model by lazy {
        Firebase.ai.generativeModel(
            modelName = TTS_MODEL_NAME,
            generationConfig = generationConfig {
                responseModalities = listOf(ResponseModality.AUDIO)
                speechConfig = SpeechConfig(voice = Voice(VOICE_NAME), languageCode = "en-US")
            }
        )
    }

    /** Speaks [text] in Coach Mac's voice. Throws if Gemini can't be reached or returns no audio. */
    suspend fun synthesize(text: String): Pcm {
        val prompt = "Say in a warm, confident, encouraging voice, like a veteran truck-driving instructor talking to a student: $text"
        val response = withTimeout(15_000L) { model.generateContent(prompt) }
        val audio = response.candidates.firstOrNull()
            ?.content
            ?.parts
            ?.filterIsInstance<InlineDataPart>()
            ?.firstOrNull()
            ?: throw IllegalStateException("The voice model returned no audio")
        return toPcm(audio.inlineData, audio.mimeType)
    }

    private fun toPcm(bytes: ByteArray, mimeType: String): Pcm {
        // A WAV file: read the sample rate from its header and skip to the samples.
        if (bytes.size > 44 && String(bytes, 0, 4, Charsets.US_ASCII) == "RIFF") {
            val rate = (bytes[24].toInt() and 0xFF) or
                ((bytes[25].toInt() and 0xFF) shl 8) or
                ((bytes[26].toInt() and 0xFF) shl 16) or
                ((bytes[27].toInt() and 0xFF) shl 24)
            var offset = 12
            while (offset + 8 <= bytes.size) {
                val chunkId = String(bytes, offset, 4, Charsets.US_ASCII)
                val chunkSize = (bytes[offset + 4].toInt() and 0xFF) or
                    ((bytes[offset + 5].toInt() and 0xFF) shl 8) or
                    ((bytes[offset + 6].toInt() and 0xFF) shl 16) or
                    ((bytes[offset + 7].toInt() and 0xFF) shl 24)
                if (chunkId == "data") {
                    val start = offset + 8
                    val end = (start + chunkSize).coerceAtMost(bytes.size)
                    return Pcm(bytes.copyOfRange(start, end), if (rate in 8_000..96_000) rate else DEFAULT_SAMPLE_RATE)
                }
                offset += 8 + chunkSize
            }
            return Pcm(bytes.copyOfRange(44, bytes.size), if (rate in 8_000..96_000) rate else DEFAULT_SAMPLE_RATE)
        }
        // Raw PCM, usually "audio/L16;codec=pcm;rate=24000".
        val rate = Regex("""rate=(\d+)""").find(mimeType)?.groupValues?.get(1)?.toIntOrNull()
        return Pcm(bytes, rate ?: DEFAULT_SAMPLE_RATE)
    }
}

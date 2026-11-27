package com.example.ai

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.LiveSession
import com.google.firebase.ai.type.PublicPreviewAPI
import com.google.firebase.ai.type.ResponseModality
import com.google.firebase.ai.type.SpeechConfig
import com.google.firebase.ai.type.Voice
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.liveGenerationConfig

private const val LIVE_TAG = "CoachLiveVoice"

/** Where a live voice conversation with Coach Mac stands. */
enum class LiveVoiceStatus {
    OFF,
    CONNECTING,
    LIVE,
    ERROR
}

/**
 * Hands-free, natural-voice conversation with Coach Mac through the Gemini Live API (Firebase AI
 * Logic). The SDK records the microphone and plays Gemini's voice itself. This uses a preview
 * model, so callers treat any failure as "live voice unavailable" and keep the regular voice.
 */
@OptIn(PublicPreviewAPI::class)
class CoachLiveVoice {

    companion object {
        const val LIVE_MODEL_NAME = "gemini-3.1-flash-live-preview"
        const val VOICE_NAME = "CHARON"
    }

    @Volatile
    private var session: LiveSession? = null

    val isActive: Boolean
        get() = session != null

    /** Connects and starts the two-way audio. The microphone permission must already be granted. */
    @SuppressLint("MissingPermission")
    suspend fun start(systemInstruction: String) {
        stop()
        val model = Firebase.ai.liveModel(
            modelName = LIVE_MODEL_NAME,
            generationConfig = liveGenerationConfig {
                responseModality = ResponseModality.AUDIO
                speechConfig = SpeechConfig(voice = Voice(VOICE_NAME))
            },
            systemInstruction = content { text(systemInstruction) }
        )
        val newSession = model.connect()
        session = newSession
        try {
            newSession.startAudioConversation()
        } catch (e: Exception) {
            session = null
            runCatching { newSession.close() }
            throw e
        }
    }

    /** Stops the microphone and the voice and closes the connection. Safe to call at any time. */
    suspend fun stop() {
        val current = session ?: return
        session = null
        try {
            current.stopAudioConversation()
            current.close()
        } catch (e: Exception) {
            Log.w(LIVE_TAG, "Error while closing the live session", e)
        }
    }

    /** Stops the audio without waiting, for when the screen or ViewModel is going away. */
    fun stopNow() {
        val current = session ?: return
        try {
            current.stopAudioConversation()
        } catch (e: Exception) {
            Log.w(LIVE_TAG, "Error while stopping live audio", e)
        }
    }
}

package com.example.ai

import android.util.Log
import com.example.data.models.ExamResult
import com.example.data.models.MissedQuestionDetail
import com.example.data.models.Question
import com.example.data.repository.CDLQuestionRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.UUID

private const val TAG = "AiReviewManager"

enum class MessageSender {
    USER,
    AI
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class AiReviewManager {

    // Lazy initialization of Gemini model with optimized generationConfig for fast voice responses
    private val model by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                temperature = 0.4f
                maxOutputTokens = 260
            },
            systemInstruction = content {
                text(
                    """
                    You are Coach Mac, a dedicated CDL instructor speaking directly out loud to a student driver who just completed an exam.
                    
                    Guidelines for your response:
                    1. Direct Spoken Audio: Your output is read aloud immediately via text-to-speech. Speak naturally, warmly, and conversationally. Never use bullet points, asterisks, hashtags, or formatting tables.
                    2. Tone: Friendly, encouraging, safety-focused, and professional.
                    3. Ultra-Fast Spoken Exam Review:
                       - Keep your response under 65 words total (2 to 3 punchy sentences).
                       - If they scored 100%, celebrate their achievement in 2 sentences.
                       - If they missed questions, immediately highlight the single most important safety rule or number they need to remember.
                       - Warmly conclude by inviting them to ask you any question.
                    """.trimIndent()
                )
            }
        )
    }

    /**
     * Builds the initial review prompt based on the exam result.
     */
    fun createInitialReviewPrompt(result: ExamResult): String {
        val missedQuestions = mutableListOf<Triple<Question, Int, Int>>() // Question, chosenIndex, correctIndex

        result.questionIds.forEach { qId ->
            val q = CDLQuestionRepository.getQuestionById(qId)
            val chosen = result.userAnswers[qId]
            if (q != null && chosen != null && chosen != q.correctIndex) {
                if (missedQuestions.none { it.first.questionText == q.questionText }) {
                    missedQuestions.add(Triple(q, chosen, q.correctIndex))
                }
            }
        }

        val letters = listOf("A", "B", "C", "D")
        val statusText = if (result.isPassed) "PASSED" else "FAILED"

        val promptBuilder = StringBuilder()
        promptBuilder.appendLine("Exam Summary:")
        promptBuilder.appendLine("- Category: ${result.category.title}")
        promptBuilder.appendLine("- Score: $statusText (${result.scorePercent}%, ${result.correctCount}/${result.totalQuestions} correct)")
        promptBuilder.appendLine()

        if (missedQuestions.isEmpty()) {
            promptBuilder.appendLine("The student achieved a perfect 100% score on this exam!")
            promptBuilder.appendLine("Deliver a brief 2-sentence congratulatory spoken message under 40 words.")
        } else {
            promptBuilder.appendLine("Here is the top missed question to debrief:")
            val topMissed = missedQuestions.first()
            val (q, chosenIndex, correctIndex) = topMissed
            val chosenLetter = letters.getOrElse(chosenIndex) { "$chosenIndex" }
            val chosenText = q.options.getOrElse(chosenIndex) { "Unknown" }
            val correctLetter = letters.getOrElse(correctIndex) { "$correctIndex" }
            val correctText = q.options.getOrElse(correctIndex) { "Unknown" }

            promptBuilder.appendLine("• [${q.subtopic}] Question: \"${q.questionText}\"")
            promptBuilder.appendLine("• Student Chose: Option $chosenLetter - \"$chosenText\"")
            promptBuilder.appendLine("• Correct Answer: Option $correctLetter - \"$correctText\"")
            promptBuilder.appendLine("• FMCSA Safety Rule: ${q.explanation}")
            promptBuilder.appendLine()
            promptBuilder.appendLine("Deliver an ultra-fast, spoken debrief in 2 to 3 sentences (under 60 words) explaining why this correct rule protects lives on the road.")
        }

        return promptBuilder.toString()
    }

    /**
     * Builds an energetic initial greeting when the user enters Coach Mac AI directly without taking an exam first.
     */
    fun createGeneralTutorPrompt(): String {
        return """
        The student driver is opening an open tutoring and study session with Coach Mac without taking an exam first.
        Deliver a warm, energetic spoken greeting in 2 punchy sentences (under 45 words). Introduce yourself as Coach Mac, encourage them, and ask whether they want to study Air Brakes, Hazardous Materials, Combination Vehicles, or General Knowledge today.
        """.trimIndent()
    }

    /**
     * Generates an immediate local fallback debrief from FMCSA manual citations if cloud API times out or is offline.
     */
    fun generateLocalFallbackDebrief(result: ExamResult): String {
        val missedQuestions = result.questionIds.mapNotNull { qId ->
            val q = CDLQuestionRepository.getQuestionById(qId)
            val chosen = result.userAnswers[qId]
            if (q != null && chosen != null && chosen != q.correctIndex) q else null
        }.distinctBy { it.questionText }

        val statusGreeting = if (result.isPassed) {
            "Great job passing the ${result.category.title} test with a score of ${result.scorePercent} percent!"
        } else {
            "Hey driver, Coach Mac here. You scored ${result.scorePercent} percent on the ${result.category.title} section."
        }

        if (missedQuestions.isEmpty()) {
            return "$statusGreeting You aced this exam with a clean 100 percent score. Keep that sharp focus out on the highway!"
        }

        val top = missedQuestions.first()
        val correctOption = top.options.getOrElse(top.correctIndex) { "the correct choice" }
        return "$statusGreeting Let's review your key takeaway. On ${top.subtopic.lowercase()}, remember that $correctOption. ${top.explanation} You can ask me any question or review the rest of your answers below!"
    }

    /**
     * Generates an immediate local welcome greeting for general tutoring when offline or before cloud response.
     */
    fun generateLocalGeneralGreeting(): String {
        return "Hey driver, Coach Mac here! I'm your CDL instructor. Ask me anything about Air Brakes, HazMat, Combination Vehicles, or General Knowledge to get test-ready. What would you like to master today?"
    }

    /**
     * Builds a prompt for Gemini to debrief a specific missed question step-by-step.
     */
    fun createStepByStepPrompt(
        missedItem: MissedQuestionDetail,
        index: Int,
        total: Int
    ): String {
        val q = missedItem.question
        val letters = listOf("A", "B", "C", "D")
        val chosenLetter = letters.getOrElse(missedItem.chosenIndex) { "${missedItem.chosenIndex}" }
        val chosenText = q.options.getOrElse(missedItem.chosenIndex) { "Unknown" }
        val correctLetter = letters.getOrElse(missedItem.correctIndex) { "${missedItem.correctIndex}" }
        val correctText = q.options.getOrElse(missedItem.correctIndex) { "Unknown" }

        val isLast = (index + 1) >= total
        val nextPrompt = if (isLast) {
            "Conclude warmly by stating that was the last question they missed, and ask if they are ready to retake the exam."
        } else {
            "Conclude warmly by asking: 'Are you ready to go to the next question that you missed?'"
        }

        return """
        The student driver asked to review each question they missed step-by-step.
        Review Question ${index + 1} of $total they missed:
        - Subtopic: ${q.subtopic}
        - Question: "${q.questionText}"
        - Student selected: Option $chosenLetter - "$chosenText"
        - Correct answer: Option $correctLetter - "$correctText"
        - FMCSA Safety Rule: ${q.explanation}

        Instructions:
        1. Keep response spoken, natural, conversational (under 60 words).
        2. Introduce this as Question ${index + 1} of $total.
        3. Explain what they chose and why the correct answer Option $correctLetter ("$correctText") is the vital safety rule.
        4. $nextPrompt
        """.trimIndent()
    }

    /**
     * Generates a local fallback step-by-step debrief for a specific missed question.
     */
    fun generateLocalStepReview(
        missedItem: MissedQuestionDetail,
        index: Int,
        total: Int
    ): String {
        val q = missedItem.question
        val letters = listOf("A", "B", "C", "D")
        val chosenLetter = letters.getOrElse(missedItem.chosenIndex) { "${missedItem.chosenIndex}" }
        val chosenText = q.options.getOrElse(missedItem.chosenIndex) { "Unknown" }
        val correctLetter = letters.getOrElse(missedItem.correctIndex) { "${missedItem.correctIndex}" }
        val correctText = q.options.getOrElse(missedItem.correctIndex) { "Unknown" }

        val isLast = (index + 1) >= total
        val followUp = if (isLast) {
            "That was the last question you missed! Are you ready to retake the test, or do you have any more questions?"
        } else {
            "Are you ready to go to the next question that you missed?"
        }

        return "Coach Mac here with Question ${index + 1} of $total you missed on ${q.subtopic}: \"${q.questionText}\". You chose Option $chosenLetter ($chosenText), but the correct answer is Option $correctLetter ($correctText). ${q.explanation} $followUp"
    }

    /**
     * Generates an immediate local fallback response for user chat messages when cloud AI is unreachable or offline.
     */
    fun generateLocalChatFallback(
        userPrompt: String,
        currentMissed: MissedQuestionDetail? = null,
        missedIndex: Int? = null,
        totalMissed: Int = 0
    ): String {
        if (currentMissed != null && missedIndex != null && totalMissed > 0) {
            val q = currentMissed.question
            val isLast = (missedIndex + 1) >= totalMissed
            val followUp = if (isLast) {
                "That was the last question you missed! Are you ready to retake the test?"
            } else {
                "Are you ready to go to the next question that you missed?"
            }
            return "Coach Mac here: On this question regarding ${q.subtopic}, remember that ${q.explanation} According to ${q.manualCitation}, always prioritize highway safety. $followUp"
        }

        val queryLower = userPrompt.lowercase()
        val words = queryLower.split(Regex("""\W+""")).filter { it.length > 2 }
        
        // Find best matching question in repository
        val match = CDLQuestionRepository.questions.maxByOrNull { q ->
            var score = 0
            val text = (q.questionText + " " + q.subtopic + " " + q.explanation).lowercase()
            for (word in words) {
                if (text.contains(word)) score++
            }
            score
        }

        return if (match != null && words.any { (match.questionText + " " + match.subtopic + " " + match.explanation).lowercase().contains(it) }) {
            "Coach Mac here: On ${match.subtopic}, remember that ${match.explanation} According to ${match.manualCitation}, always prioritize road safety. What other question do you have?"
        } else {
            "Coach Mac here: For the CDL exam, make sure you know your air brake pressure ranges, following distance rules, and pre-trip inspection steps inside and out. Feel free to ask about any specific question or regulation!"
        }
    }

    /**
     * Streams content generation from the Gemini model.
     */
    fun streamResponse(conversationHistory: List<ChatMessage>, newPrompt: String): Flow<String> = flow {
        // Construct conversation contents
        val contents: List<Content> = conversationHistory.takeLast(4).map { msg ->
            val role = if (msg.sender == MessageSender.USER) "user" else "model"
            content(role) { text(msg.text) }
        } + listOf(content("user") { text(newPrompt) })

        withTimeout(12_000L) {
            val responseStream = model.generateContentStream(contents)
            responseStream.collect { chunk ->
                val chunkText = chunk.text
                if (!chunkText.isNullOrEmpty()) {
                    emit(chunkText)
                }
            }
        }
    }
}

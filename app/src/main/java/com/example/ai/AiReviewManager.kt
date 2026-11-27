package com.example.ai

import com.example.data.models.ExamResult
import com.example.data.models.MissedQuestionDetail
import com.example.data.models.Question
import com.example.data.repository.CDLQuestionRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.ResponseStoppedException
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.UUID

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

/**
 * What a Coach Mac request is for. Exam debriefs stay short; open questions, study plans and quiz
 * grading get room for a complete answer.
 */
enum class CoachMode {
    DEBRIEF,
    TUTOR,
    PLAN,
    QUIZ_GRADE
}

class AiReviewManager {

    companion object {
        const val MODEL_NAME = "gemini-3.8-flash"

        /** How many earlier chat messages Gemini sees with each request. */
        const val HISTORY_MESSAGES = 16

        private const val LETTERS = "ABCDEF"

        private val GROUNDING_RULES = """
            Everything you write is read aloud by text-to-speech, so speak naturally in plain sentences. Never use bullet points, asterisks, hashtags, numbered lists, tables or emoji.

            Grounding rules, which matter more than anything else:
            1. Each request can include REFERENCE MATERIAL from the app's CDL study bank and a STUDENT PROFILE. Base every fact, number and rule you state on that material.
            2. Only mention a manual section if it appears on a Source line in the reference material, and copy it exactly. Never invent section numbers, page numbers, regulation numbers or statistics.
            3. If the reference material doesn't cover what the student asked, say plainly that it isn't in your study material, give only general safety advice you are sure of, and tell them to check that topic in their state CDL manual.
            4. Use the student profile to personalize: bring up their weak areas when it helps, but never invent scores or history.
            5. Stay on CDL and commercial driving topics.
        """.trimIndent()

        /** Reads Correct / Not quite from the start of a graded quiz reply. */
        fun parseVerdict(reply: String): Boolean? {
            val start = reply.trim().lowercase().take(24)
            return when {
                start.startsWith("not quite") || start.startsWith("incorrect") ||
                    start.startsWith("not correct") || start.startsWith("wrong") -> false
                start.startsWith("correct") || start.startsWith("that's correct") ||
                    start.startsWith("yes") -> true
                else -> null
            }
        }
    }

    /** Short spoken exam debriefs and one-by-one missed-question reviews. */
    private val debriefModel by lazy {
        Firebase.ai.generativeModel(
            modelName = MODEL_NAME,
            generationConfig = generationConfig {
                temperature = 0.4f
                // Generous on purpose: newer Gemini models count their hidden "thinking" against
                // this limit, and running out makes the SDK throw instead of answering. The
                // prompts keep the spoken answers short.
                maxOutputTokens = 8192
            },
            systemInstruction = content {
                text(
                    "You are Coach Mac, a warm, encouraging, safety-focused CDL instructor speaking out loud to a student driver.\n\n" +
                        GROUNDING_RULES +
                        "\n\nLength: exam debriefs and missed-question reviews stay under 70 words, two to four sentences."
                )
            }
        )
    }

    /** Open questions, study plans and quiz grading: room for a complete answer. */
    private val tutorModel by lazy {
        Firebase.ai.generativeModel(
            modelName = MODEL_NAME,
            generationConfig = generationConfig {
                temperature = 0.3f
                maxOutputTokens = 8192
            },
            systemInstruction = content {
                text(
                    "You are Coach Mac, a warm, encouraging, safety-focused CDL instructor speaking out loud to a student driver.\n\n" +
                        GROUNDING_RULES +
                        "\n\nLength: answer questions completely but compactly, usually 40 to 120 words. " +
                        "A study plan can run up to 180 words, describing each day in one plain sentence. " +
                        "Quiz feedback stays under 60 words."
                )
            }
        )
    }

    private fun letter(index: Int): String = LETTERS.getOrNull(index)?.toString() ?: "${index + 1}"

    private fun missedFrom(result: ExamResult): List<Triple<Question, Int, Int>> {
        val missed = mutableListOf<Triple<Question, Int, Int>>()
        result.questionIds.forEach { qId ->
            val q = CDLQuestionRepository.getQuestionById(qId)
            val chosen = result.userAnswers[qId]
            if (q != null && chosen != null && chosen != q.correctIndex) {
                if (missed.none { it.first.questionText == q.questionText }) {
                    missed.add(Triple(q, chosen, q.correctIndex))
                }
            }
        }
        return missed
    }

    /** Opening debrief after an exam. */
    fun createInitialReviewPrompt(result: ExamResult, profile: String): String {
        val missed = missedFrom(result)
        val statusText = if (result.isPassed) "PASSED" else "FAILED"

        val sb = StringBuilder()
        sb.appendLine(profile)
        sb.appendLine()
        sb.appendLine("EXAM JUST COMPLETED:")
        sb.appendLine("Category: ${result.category.title}")
        sb.appendLine("Score: $statusText (${result.scorePercent} percent, ${result.correctCount} of ${result.totalQuestions} correct)")
        sb.appendLine("Questions missed: ${missed.size}")
        sb.appendLine()

        if (missed.isEmpty()) {
            sb.appendLine("The student scored 100 percent. Congratulate them in two sentences under 40 words, and suggest the next category to practice based on their profile.")
        } else {
            val (q, chosenIndex, correctIndex) = missed.first()
            sb.appendLine(CoachKnowledge.formatReferences(listOf(q)))
            sb.appendLine("The student chose: ${letter(chosenIndex)}, ${q.options.getOrElse(chosenIndex) { "unknown" }}")
            sb.appendLine("Correct answer: ${letter(correctIndex)}, ${q.options.getOrElse(correctIndex) { "unknown" }}")
            val otherTopics = missed.drop(1).map { CoachKnowledge.cleanSubtopic(it.first.subtopic) }.distinct().take(3)
            if (otherTopics.isNotEmpty()) {
                sb.appendLine("Other missed topics: ${otherTopics.joinToString(", ")}")
            }
            sb.appendLine()
            sb.appendLine(
                "Give a spoken debrief in two to four sentences, under 70 words: explain the single most important rule from Ref 1 and why it keeps drivers safe. " +
                    "If the profile shows this topic is a repeat weak area, say so briefly. End by inviting them to review each missed question or ask anything."
            )
        }
        return sb.toString()
    }

    /** Greeting when the student opens Coach Mac without taking an exam first. */
    fun createGeneralTutorPrompt(profile: String): String {
        return """
            $profile

            The student opened a study session with Coach Mac without taking an exam first.
            Greet them warmly in two or three sentences, under 55 words. If the profile shows history, mention their weakest area or lowest readiness and suggest one concrete next step, such as a quick voice quiz on it. If they are new, ask whether they want to study Air Brakes, Hazardous Materials, Combination Vehicles or General Knowledge today.
        """.trimIndent()
    }

    /** Review of one missed question during the one-by-one walkthrough. */
    fun createStepByStepPrompt(missedItem: MissedQuestionDetail, index: Int, total: Int): String {
        val q = missedItem.question
        val isLast = (index + 1) >= total
        val nextPrompt = if (isLast) {
            "End by saying that was the last question they missed, and ask if they are ready to retake the exam."
        } else {
            "End by asking: 'Are you ready to go to the next question that you missed?'"
        }

        return """
            The student is reviewing the questions they missed, one by one. This is question ${index + 1} of $total.
            ${CoachKnowledge.formatReferences(listOf(q))}
            The student chose: ${letter(missedItem.chosenIndex)}, ${q.options.getOrElse(missedItem.chosenIndex) { "unknown" }}
            Correct answer: ${letter(missedItem.correctIndex)}, ${q.options.getOrElse(missedItem.correctIndex) { "unknown" }}

            Instructions:
            1. Spoken and conversational, under 70 words.
            2. Introduce it as question ${index + 1} of $total.
            3. Say what they chose and why the correct answer is the safe rule, using only Ref 1.
            4. $nextPrompt
        """.trimIndent()
    }

    /** An open question from the student, with the closest study-bank material attached. */
    fun createTutorPrompt(
        userText: String,
        profile: String,
        references: String,
        currentMissed: MissedQuestionDetail?,
        missedIndex: Int?,
        totalMissed: Int
    ): String {
        val sb = StringBuilder()
        sb.appendLine(profile)
        sb.appendLine()
        sb.appendLine(references)
        sb.appendLine()
        if (currentMissed != null && missedIndex != null) {
            val q = currentMissed.question
            sb.appendLine("CONTEXT: the student is reviewing missed question ${missedIndex + 1} of $totalMissed (\"${q.questionText}\"). They chose ${letter(currentMissed.chosenIndex)}; the correct answer is ${letter(currentMissed.correctIndex)}.")
            sb.appendLine("After answering, end by asking: \"Are you ready to go to the next question that you missed?\"")
            sb.appendLine()
        }
        sb.appendLine("THE STUDENT SAYS: \"$userText\"")
        sb.appendLine()
        sb.append("Answer the student directly, following the grounding rules.")
        return sb.toString()
    }

    /** A personal study plan built from the student's history. */
    fun createStudyPlanPrompt(profile: String, references: String, userText: String): String {
        return """
            $profile

            $references

            THE STUDENT ASKS: "$userText"

            Make a personal five-day study plan for this student, up to 180 words, spoken in plain sentences with one sentence per day and no lists. Put their weakest subtopics and lowest-readiness categories first. Only suggest things this app offers: practice exams by category, Practice and Study mode, Missed Questions mode, the 3D Pre-Trip Inspection walk-around and test, and voice quizzes with Coach Mac. For any fact you mention, use only the reference material. End by offering to start a quiz on their weakest area right now.
        """.trimIndent()
    }

    /** Grading one spoken quiz answer. [localVerdict] is set when the app already graded it. */
    fun createQuizGradePrompt(q: Question, answer: String, localVerdict: Boolean?): String {
        val sb = StringBuilder()
        sb.appendLine("QUIZ QUESTION: ${q.questionText}")
        q.options.forEachIndexed { i, option -> sb.appendLine("${letter(i)}: $option") }
        sb.appendLine("Correct answer: ${letter(q.correctIndex)}, ${q.options.getOrElse(q.correctIndex) { "" }}")
        sb.appendLine("Explanation: ${q.explanation}")
        sb.appendLine("Source: ${q.manualCitation}")
        sb.appendLine()
        sb.appendLine("THE STUDENT ANSWERED (from speech-to-text, so words may be misheard): \"$answer\"")
        sb.appendLine()
        when (localVerdict) {
            true -> sb.appendLine("This answer has already been graded CORRECT.")
            false -> sb.appendLine("This answer has already been graded INCORRECT.")
            null -> sb.appendLine("Decide whether the answer means the same thing as the correct answer. Judge meaning, not spelling. If the student says they don't know, it is incorrect.")
        }
        sb.append(
            "Begin your reply with exactly \"Correct\" or exactly \"Not quite\". Then, in under 50 words, say the correct answer and why, using only the explanation above. " +
                "End by asking if they are ready for the next question."
        )
        return sb.toString()
    }

    /** System instruction for a live, hands-free voice conversation (Gemini Live API). */
    fun createLiveSystemInstruction(profile: String, references: String): String {
        return "You are Coach Mac, a warm, encouraging, safety-focused CDL instructor having a live spoken conversation with a student driver. " +
            "Keep each turn short and conversational, usually two to four sentences, and let the student talk. " +
            "You can quiz them out loud, explain rules, and help them plan their studying.\n\n" +
            "Grounding rules: base facts, numbers and rules on the reference material and student profile below. " +
            "Never invent manual section numbers, regulation numbers or statistics. " +
            "If you are not sure something is in the CDL manual, say so and tell the student to check their state CDL manual. " +
            "Stay on CDL and commercial driving topics.\n\n" +
            profile + "\n\n" + references
    }

    // ---------------------------------------------------------------------------------------------
    // When the server can't be reached. Coach Mac says so instead of guessing. Where the app has
    // exact data (the exam result, the missed question) it still shares that, labelled as such.
    // ---------------------------------------------------------------------------------------------

    fun offlineNotice(): String {
        return "I can't reach the Coach Mac server right now, so I won't guess at an answer. Check your internet connection and tap Retry. You can still take a voice quiz while you wait."
    }

    fun offlineWithReference(q: Question): String {
        return "I can't reach the Coach Mac server right now, so here's exactly what your study bank says about this question. ${q.explanation} Source: ${q.manualCitation}. Tap Retry when you're back online."
    }

    fun generateLocalFallbackDebrief(result: ExamResult): String {
        val missed = missedFrom(result).map { it.first }
        val score = "You scored ${result.scorePercent} percent on ${result.category.title}${if (result.isPassed) ", a pass" else ""}."
        if (missed.isEmpty()) {
            return "I can't reach the Coach Mac server right now, but your results are saved. $score A clean 100 percent, nice work!"
        }
        val top = missed.first()
        val correctOption = top.options.getOrElse(top.correctIndex) { "the correct choice" }
        return "I can't reach the Coach Mac server right now, so here's the key point straight from your exam. $score " +
            "On ${CoachKnowledge.cleanSubtopic(top.subtopic).lowercase()}, the correct answer was: $correctOption. ${top.explanation} " +
            "Tap Retry when you're back online, or review your missed questions below."
    }

    fun generateLocalGeneralGreeting(): String {
        return "Hey driver, Coach Mac here. I can't reach my server right now, so I can't answer open questions yet. Check your connection and tap Retry, or start a voice quiz, which works offline."
    }

    fun generateLocalStepReview(missedItem: MissedQuestionDetail, index: Int, total: Int): String {
        val q = missedItem.question
        val isLast = (index + 1) >= total
        val followUp = if (isLast) {
            "That was the last question you missed. Ready to retake the test?"
        } else {
            "Are you ready to go to the next question that you missed?"
        }
        return "I can't reach the Coach Mac server, so here's question ${index + 1} of $total straight from your study bank. " +
            "\"${q.questionText}\" You chose ${letter(missedItem.chosenIndex)}, ${q.options.getOrElse(missedItem.chosenIndex) { "" }}, " +
            "but the correct answer is ${letter(missedItem.correctIndex)}, ${q.options.getOrElse(missedItem.correctIndex) { "" }}. ${q.explanation} $followUp"
    }

    fun offlineQuizNotice(q: Question): String {
        return "I can't reach the Coach Mac server to check an answer in your own words. The correct answer is ${CoachKnowledge.correctAnswerText(q)}. ${q.explanation} " +
            "Answering with the letter works offline. Ready for the next question?"
    }

    /**
     * Streams Gemini's reply. [history] is the chat so far, not including [newPrompt]; the last
     * [HISTORY_MESSAGES] of it are sent so Coach Mac remembers the conversation.
     */
    fun streamResponse(
        history: List<ChatMessage>,
        newPrompt: String,
        mode: CoachMode = CoachMode.TUTOR
    ): Flow<String> = flow {
        val recent = history.takeLast(HISTORY_MESSAGES)
        val contents = mutableListOf<Content>()
        if (recent.isNotEmpty() && recent.first().sender == MessageSender.AI) {
            // The conversation has to start with the user, so mark where the session began.
            contents.add(content("user") { text("Start my Coach Mac session.") })
        }
        recent.forEach { msg ->
            val role = if (msg.sender == MessageSender.USER) "user" else "model"
            contents.add(content(role) { text(msg.text) })
        }
        contents.add(content("user") { text(newPrompt) })

        val model = if (mode == CoachMode.DEBRIEF) debriefModel else tutorModel
        val timeoutMs = if (mode == CoachMode.DEBRIEF) 25_000L else 40_000L

        var emittedAny = false
        try {
            withTimeout(timeoutMs) {
                model.generateContentStream(contents).collect { chunk ->
                    val chunkText = chunk.text
                    if (!chunkText.isNullOrEmpty()) {
                        emittedAny = true
                        emit(chunkText)
                    }
                }
            }
        } catch (e: ResponseStoppedException) {
            // Stopped early (for example the length limit). Keep what was already said rather
            // than throwing it away; with nothing said, report the failure as usual.
            if (!emittedAny) throw e
        }
    }
}

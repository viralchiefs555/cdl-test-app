package com.example.ai

import com.example.data.models.CDLCategory
import com.example.data.models.CategoryReadiness
import com.example.data.models.ExamResult
import com.example.data.models.MissedQuestionDetail
import com.example.data.models.Question
import com.example.data.repository.CDLQuestionRepository
import kotlinx.coroutines.TimeoutCancellationException

/** The student's saved progress, as the offline coach needs it. */
data class StudentData(
    val history: List<ExamResult>,
    val readiness: Map<CDLCategory, CategoryReadiness>,
    val overallReadiness: Int,
    val missedIds: Set<String>
)

/**
 * Coach Mac without a server. Answers open questions, follow-ups, progress questions, memory
 * tricks and study plans from the app's own question bank (questions, correct answers,
 * explanations and FMCSA manual citations) and the student's saved history. Used whenever Gemini
 * can't be reached. It never makes anything up: when the bank doesn't cover a question it says so.
 */
class OfflineCoach {

    companion object {
        /** Share of the question the best match must cover to answer it directly. */
        private const val CONFIDENT = 0.6

        /** Below CONFIDENT but at least this: offer the closest thing, clearly labelled. */
        private const val PARTIAL = 0.35

        private val LETTERS = listOf("A", "B", "C", "D", "E", "F")

        /** A short, plain reason for why Gemini couldn't answer, shown in the notice card. */
        fun describeFailure(e: Throwable): String {
            if (e is TimeoutCancellationException) return "the AI took too long to answer"
            val text = buildString {
                var t: Throwable? = e
                var depth = 0
                while (t != null && depth < 6) {
                    append(t.javaClass.simpleName).append(": ").append(t.message ?: "").append(" | ")
                    t = t.cause
                    depth++
                }
            }
            val lower = text.lowercase()
            return when {
                lower.contains("unknownhost") || lower.contains("unable to resolve host") ||
                    lower.contains("connectexception") || lower.contains("no address associated") ->
                    "no internet connection"
                lower.contains("app check") || lower.contains("appcheck") || lower.contains("app_check") ||
                    lower.contains("attestation") || lower.contains("too many attempts") ->
                    "Firebase App Check rejected this install"
                lower.contains("quota") || lower.contains("resource_exhausted") || lower.contains("429") ->
                    "the Gemini usage limit was reached"
                lower.contains("service_disabled") || lower.contains("has not been used") ||
                    lower.contains("api not enabled") || lower.contains("is disabled") ->
                    "the Gemini API isn't turned on for this Firebase project"
                lower.contains("api key") || lower.contains("api_key") || lower.contains("invalid key") ->
                    "the Firebase API key was rejected"
                lower.contains("permission_denied") || lower.contains("403") || lower.contains("unauthorized") || lower.contains("401") ->
                    "Firebase refused the request (permission denied)"
                lower.contains("max_tokens") ->
                    "the AI reply hit its length limit"
                lower.contains("safety") || lower.contains("blocked") ->
                    "the AI blocked the reply for safety"
                lower.contains("not found") || lower.contains("404") ->
                    "the AI model wasn't found"
                lower.contains("timeout") || lower.contains("timed out") ->
                    "the AI took too long to answer"
                else -> {
                    val first = (e.message ?: e.javaClass.simpleName).replace(Regex("\\s+"), " ").take(90)
                    "${e.javaClass.simpleName}: $first"
                }
            }
        }
    }

    private var lastTopic: Question? = null
    private val mentioned = mutableSetOf<String>()
    private var turn = 0

    /** The study-bank question the conversation is currently about, if any. */
    fun currentTopic(): Question? = lastTopic

    /** Remembers what the conversation is about, so follow-ups like "why?" work. */
    fun noteTopic(q: Question?) {
        if (q != null) {
            lastTopic = q
            mentioned.add(q.id)
        }
    }

    fun reset() {
        lastTopic = null
        mentioned.clear()
    }

    private fun <T> pick(options: List<T>): T {
        turn++
        return options[turn % options.size]
    }

    private fun containsAny(text: String, words: List<String>): Boolean = words.any { text.contains(it) }

    private fun clean(text: String): String = text.trim().trimEnd('.', ' ')

    private fun sentence(text: String): String {
        val t = text.trim()
        return if (t.endsWith(".") || t.endsWith("!") || t.endsWith("?")) t else "$t."
    }

    private fun correct(q: Question): String = clean(q.options.getOrElse(q.correctIndex) { "" })

    private fun sub(q: Question): String = CoachKnowledge.cleanSubtopic(q.subtopic).lowercase()

    // ---------------------------------------------------------------------------------------------
    // Open questions
    // ---------------------------------------------------------------------------------------------

    fun answer(
        userText: String,
        data: StudentData,
        currentMissed: MissedQuestionDetail?,
        quizQuestion: Question?
    ): String {
        val lower = userText.lowercase().trim()
        val terms = CoachKnowledge.tokenize(userText)
        val pinned = currentMissed?.question ?: quizQuestion

        if (isGreeting(lower)) return greeting(data)
        if (isThanks(lower)) {
            return pick(
                listOf(
                    "Anytime, driver. What else do you want to go over?",
                    "You got it. Keep the questions coming, or say quiz me to practice.",
                    "Happy to help. Want to keep going with another topic?"
                )
            )
        }
        if (containsAny(lower, listOf("how am i doing", "my progress", "my score", "my readiness", "am i ready", "how ready", "my stats", "how did i do"))) {
            return progress(data)
        }
        if (containsAny(lower, listOf("study plan", "plan for me", "make me a plan", "what should i study", "how should i study", "where should i start"))) {
            return studyPlan(data)
        }
        if (containsAny(lower, listOf("memory trick", "mnemonic", "memorize", "remember", "trick to", "trick for"))) {
            val refersBack = containsAny(" $lower ", listOf(" this ", " that ", " it ", " these ", " the correct answer", " my answer"))
            val ownTopic = CoachKnowledge.retrieveScored(
                lower.replace(Regex("memory trick|mnemonic|memorize|remember|trick|cdl|exam|questions?"), " "),
                limit = 1
            ).firstOrNull()?.takeIf { it.coverage >= CONFIDENT }?.question
            val topic = pinned ?: (if (refersBack) lastTopic else null) ?: ownTopic
            return if (topic != null) memoryTrick(topic) else numberDrill(data)
        }
        if (currentMissed != null && containsAny(lower, listOf("unsafe", "dangerous", "why was my", "wrong answer", "why is my", "what did i do wrong"))) {
            return whyWrong(currentMissed)
        }

        val matches = CoachKnowledge.retrieveScored(userText, limit = 3)
        val best = matches.firstOrNull()
        val isFollowUp = terms.isEmpty() || containsAny(
            lower,
            listOf("why", "explain", "tell me more", "more about", "what do you mean", "again", "elaborate", "how come", "example", "the rule", "fmcsr", "fmcsa rule", "another one", "keep going")
        )
        val confident = best != null && best.coverage >= CONFIDENT

        if (isFollowUp && !confident) {
            if (pinned != null) return explain(pinned, emptyList(), lead = "Here's the rule behind that one.")
            lastTopic?.let { topic ->
                return if (lower.contains("why") || lower.contains("how come")) whyBehind(topic) else moreOn(topic)
            }
        }
        if (best != null && confident) {
            val extras = matches.drop(1)
                .filter { it.coverage >= CONFIDENT && it.question.questionText != best.question.questionText }
                .map { it.question }
                .take(1)
            return explain(best.question, extras, lead = null)
        }
        // Close but not exact: only offer it when every word was one the study guide uses, so a
        // question about something outside it (a state's speed limit, say) isn't answered sideways.
        if (best != null && best.coverage >= PARTIAL && CoachKnowledge.allWordsKnown(userText)) return closest(best.question)
        return notCovered()
    }

    private fun explain(q: Question, extras: List<Question>, lead: String?): String {
        val sb = StringBuilder()
        sb.append(
            lead ?: pick(
                listOf(
                    "Good question. Here's what your study guide says.",
                    "Let's break it down.",
                    "Alright, here's the deal.",
                    "Here's how the CDL manual covers it."
                )
            )
        ).append(' ')
        sb.append(q.questionText.trim()).append(' ')
        sb.append("The answer: ").append(correct(q)).append(". ")
        sb.append(sentence(q.explanation)).append(' ')
        extras.firstOrNull()?.let { extra ->
            sb.append("Also worth knowing: ").append(extra.questionText.trim()).append(' ')
            sb.append(correct(extra)).append(". ")
            mentioned.add(extra.id)
        }
        sb.append("That's from ").append(clean(q.manualCitation)).append(". ")
        sb.append(
            pick(
                listOf(
                    "Want me to quiz you on ${sub(q)}? Just say yes.",
                    "Say tell me more if you want to go deeper.",
                    "Want to practice it? Say quiz me."
                )
            )
        )
        noteTopic(q)
        return sb.toString()
    }

    private fun whyBehind(q: Question): String {
        return "Here's the why. ${sentence(q.explanation)} That's from ${clean(q.manualCitation)}. " +
            "Want another one on ${sub(q)}, or should I quiz you?"
    }

    private fun moreOn(topic: Question): String {
        val related = CDLQuestionRepository.questions
            .filter { it.subtopic == topic.subtopic && it.id != topic.id && it.id !in mentioned }
            .distinctBy { it.questionText }
        val next = related.randomOrNull()
            ?: return explain(topic, emptyList(), lead = "Let me put that another way.")
        noteTopic(next)
        return "Here's more on ${sub(topic)}. ${next.questionText.trim()} The answer: ${correct(next)}. ${sentence(next.explanation)} " +
            "That's from ${clean(next.manualCitation)}. Want another one, or should I quiz you?"
    }

    private fun closest(q: Question): String {
        noteTopic(q)
        return "I don't have an exact answer for that in your study guide, so I won't guess. The closest thing I have is this. " +
            "${q.questionText.trim()} The answer: ${correct(q)}. ${sentence(q.explanation)} " +
            "If that's not what you meant, try asking it a different way."
    }

    private fun notCovered(): String {
        return "That one isn't covered in your study guide, so I won't guess at it. I can help with air brakes, hazmat, " +
            "combination vehicles, general knowledge and the pre-trip inspection. For anything else, like rules in your state, " +
            "check your state's CDL manual. Want a quick quiz instead?"
    }

    private fun whyWrong(m: MissedQuestionDetail): String {
        val q = m.question
        val chosen = clean(q.options.getOrElse(m.chosenIndex) { "that option" })
        return "You picked: $chosen. The safe answer is: ${correct(q)}. Here's why it matters on the road. ${sentence(q.explanation)} " +
            "That's from ${clean(q.manualCitation)}."
    }

    private fun memoryTrick(q: Question): String {
        noteTopic(q)
        val answer = correct(q)
        val numbers = Regex("""\d+(\.\d+)?(\s*(psi|percent|%|feet|foot|seconds|minutes|minute|hours|miles|mph|inches|degrees|pounds|lbs|days))?""")
            .findAll(answer)
            .map { it.value.trim() }
            .distinct()
            .take(2)
            .toList()
        val firstReason = q.explanation.trim().split(Regex("(?<=[.!?])\\s+")).firstOrNull()?.let { sentence(it) } ?: ""
        return if (numbers.isNotEmpty()) {
            "Here's a memory trick for ${sub(q)}: anchor on the number, ${numbers.joinToString(" and ")}. " +
                "Picture the gauge or the test pad while you say the full answer: $answer. " +
                "Say it out loud three times and it sticks. The reason behind it: $firstReason"
        } else {
            "Here's a memory trick for ${sub(q)}: turn it into a picture. Imagine yourself standing at the truck doing it: $answer. " +
                "Then tie it to the reason: $firstReason Say both out loud three times and it'll stick."
        }
    }

    /** Three high-yield numbers to memorize, from the student's weak categories when there are any. */
    private fun numberDrill(data: StudentData): String {
        val weakCats = CoachKnowledge.weakAreas(data.history, data.missedIds).map { it.category }.toSet()
        val withNumbers = CDLQuestionRepository.questions
            .filter { q -> q.options.getOrElse(q.correctIndex) { "" }.any { it.isDigit() } }
            .distinctBy { it.questionText }
        val pool = withNumbers.filter { weakCats.isEmpty() || it.category in weakCats }.ifEmpty { withNumbers }
        val picks = pool.shuffled().take(3)
        if (picks.isEmpty()) return numberDrillFallback()
        noteTopic(picks.first())
        val words = listOf("One", "Two", "Three")
        val sb = StringBuilder("Numbers are where most people lose points, so here are three worth memorizing. ")
        picks.forEachIndexed { i, q ->
            sb.append(words[i]).append(": ").append(q.questionText.trim()).append(' ').append(correct(q)).append(". ")
        }
        sb.append("Say each one out loud three times. Want me to quiz you on them?")
        return sb.toString()
    }

    private fun numberDrillFallback(): String {
        return "My best memory trick: say the answer out loud with its reason, three times, right after you miss it. Want me to quiz you?"
    }

    // ---------------------------------------------------------------------------------------------
    // Progress, greetings and study plans from saved history
    // ---------------------------------------------------------------------------------------------

    private fun isGreeting(lower: String): Boolean {
        val stripped = lower.replace(Regex("[^a-z ]"), "").trim()
        return stripped in setOf("hi", "hey", "hello", "yo", "sup", "good morning", "good afternoon", "good evening", "hey coach", "hi coach", "hello coach", "hey mac", "whats up")
    }

    private fun isThanks(lower: String): Boolean {
        return lower.length <= 40 && containsAny(lower, listOf("thank", "thanks", "appreciate it", "got it", "makes sense", "cool"))
    }

    private fun weakestCategory(data: StudentData): CategoryReadiness? {
        return data.readiness.values.filter { it.testsTaken > 0 }.minByOrNull { it.readinessScore }
    }

    fun greeting(data: StudentData): String {
        if (data.history.isEmpty()) {
            return "Hey driver, Coach Mac here. Ask me anything about air brakes, hazmat, combination vehicles, general knowledge " +
                "or the pre-trip inspection. Or say quiz me and I'll test you out loud."
        }
        val weak = CoachKnowledge.weakAreas(data.history, data.missedIds).firstOrNull()
        val weakText = weak?.let { " Your weakest spot right now is ${CoachKnowledge.cleanSubtopic(it.subtopic).lowercase()} in ${it.category.title}." } ?: ""
        return "Hey driver, good to see you. You're at ${data.overallReadiness} percent overall readiness.$weakText " +
            "Want to start with a quick quiz on it? Just say yes, or ask me anything."
    }

    fun progress(data: StudentData): String {
        if (data.history.isEmpty()) {
            return "You haven't taken any practice exams yet, so I don't have a score to go on. " +
                "Start with a General Knowledge practice test and I'll track your progress from there."
        }
        val taken = data.readiness.values.filter { it.testsTaken > 0 }
        val strongest = taken.maxByOrNull { it.readinessScore }
        val weakest = weakestCategory(data)
        val untouched = data.readiness.values.filter { it.testsTaken == 0 }.map { it.category.title }
        val weakSub = CoachKnowledge.weakAreas(data.history, data.missedIds).firstOrNull()
        val sb = StringBuilder()
        sb.append("You've taken ${data.history.size} practice exam${if (data.history.size == 1) "" else "s"}, and your overall readiness is ${data.overallReadiness} percent. ")
        if (strongest != null) sb.append("Your strongest area is ${strongest.category.title} at ${strongest.readinessScore} percent. ")
        if (weakest != null && weakest != strongest) sb.append("Your weakest is ${weakest.category.title} at ${weakest.readinessScore} percent. ")
        if (untouched.isNotEmpty()) sb.append("You haven't tried ${untouched.joinToString(" or ")} yet. ")
        if (weakSub != null) sb.append("The topic you miss most is ${CoachKnowledge.cleanSubtopic(weakSub.subtopic).lowercase()}. ")
        sb.append("You have ${data.missedIds.size} question${if (data.missedIds.size == 1) "" else "s"} on your missed list. ")
        sb.append(if (data.overallReadiness >= 80) "You're close to test-ready. Keep it sharp." else "Want me to quiz you on your weak areas?")
        return sb.toString()
    }

    fun studyPlan(data: StudentData): String {
        if (data.history.isEmpty()) {
            return "Here's a five-day plan to get you started. Day one, take a General Knowledge practice test in Practice and Study mode and read every explanation. " +
                "Day two, do the same for Air Brakes. Day three, Combination Vehicles. " +
                "Day four, walk through the 3D Pre-Trip Inspection in Find it mode, then try its Test tab. " +
                "Day five, take a full General Knowledge exam in the DMV simulator, then review what you missed with me. Want to start with a quick quiz now?"
        }
        val weak = CoachKnowledge.weakAreas(data.history, data.missedIds, limit = 2)
        val lowest = weakestCategory(data)
        val untouched = data.readiness.values.firstOrNull { it.testsTaken == 0 }?.category
        val day1 = weak.getOrNull(0)?.let {
            "Day one, focus on ${CoachKnowledge.cleanSubtopic(it.subtopic).lowercase()}: do ${it.category.title} in Practice and Study mode and read every explanation, then have me quiz you on your weak areas."
        } ?: "Day one, do a Practice and Study round in ${lowest?.category?.title ?: "General Knowledge"} and read every explanation."
        val day2 = weak.getOrNull(1)?.let {
            "Day two, same idea for ${CoachKnowledge.cleanSubtopic(it.subtopic).lowercase()} in ${it.category.title}."
        } ?: "Day two, take a timed exam in ${lowest?.category?.title ?: "General Knowledge"} in the DMV simulator."
        val day3 = if (data.missedIds.isNotEmpty()) {
            "Day three, clear your missed list: run Missed Questions mode on all ${data.missedIds.size} of them."
        } else {
            "Day three, take a timed exam in the DMV simulator for your lowest category."
        }
        val day4 = if (untouched != null) {
            "Day four, start ${untouched.title}, since you haven't tried it yet."
        } else {
            "Day four, take a full timed exam in ${lowest?.category?.title ?: "your lowest category"} and aim for 80 percent or better."
        }
        val day5 = "Day five, go through the 3D Pre-Trip Inspection, Find it first and then the Test tab, and finish with a voice quiz on pre-trip."
        return "Here's your five-day plan, built from your results. $day1 $day2 $day3 $day4 $day5 Want to start that first quiz right now?"
    }

    // ---------------------------------------------------------------------------------------------
    // Exam debriefs, missed-question reviews and quiz grading
    // ---------------------------------------------------------------------------------------------

    fun debrief(result: ExamResult, data: StudentData): String {
        val missed = result.questionIds.mapNotNull { id ->
            val q = CDLQuestionRepository.getQuestionById(id)
            val chosen = result.userAnswers[id]
            if (q != null && chosen != null && chosen != q.correctIndex) q else null
        }.distinctBy { it.questionText }

        val passText = if (result.isPassed) "That's a pass." else "You need ${result.category.passingScorePercent} percent to pass, so let's close the gap."
        val opener = "You scored ${result.scorePercent} percent on ${result.category.title}, ${result.correctCount} out of ${result.totalQuestions}. $passText"
        if (missed.isEmpty()) {
            return "$opener A perfect score. Outstanding work, driver. Want to try another category or a voice quiz?"
        }
        val top = missed.first()
        noteTopic(top)
        val repeat = CoachKnowledge.weakAreas(data.history.drop(1), data.missedIds)
            .any { it.subtopic == top.subtopic && it.category == top.category }
        val repeatText = if (repeat) " That topic has tripped you up before, so it's worth extra time." else ""
        return "$opener You missed ${missed.size}. The big one to remember is ${sub(top)}: ${correct(top)}. ${sentence(top.explanation)}$repeatText " +
            "Want to review each missed question one by one, or ask me anything?"
    }

    fun stepReview(item: MissedQuestionDetail, index: Int, total: Int): String {
        val q = item.question
        noteTopic(q)
        val chosen = q.options.getOrElse(item.chosenIndex) { "" }
        val followUp = if (index + 1 >= total) {
            "That was the last question you missed. Ready to retake the test?"
        } else {
            "Are you ready to go to the next question that you missed?"
        }
        return "Question ${index + 1} of $total, on ${sub(q)}. ${q.questionText.trim()} " +
            "You picked ${LETTERS.getOrElse(item.chosenIndex) { "" }}, ${clean(chosen)}. " +
            "The correct answer is ${LETTERS.getOrElse(q.correctIndex) { "" }}, ${correct(q)}. ${sentence(q.explanation)} $followUp"
    }

    /** Grades a quiz answer without Gemini. Returns the feedback text and whether it was correct. */
    fun gradeQuiz(q: Question, answer: String): Pair<String, Boolean> {
        noteTopic(q)
        val verdict = CoachKnowledge.gradeLoosely(q, answer)
        return if (verdict != null) {
            CoachKnowledge.localQuizFeedback(q, verdict) to verdict
        } else {
            ("I couldn't match that to one of the choices, so I'll count it as a miss. The correct answer is ${CoachKnowledge.correctAnswerText(q)}. " +
                "${sentence(q.explanation)} Saying the letter works best. Ready for the next question?") to false
        }
    }
}

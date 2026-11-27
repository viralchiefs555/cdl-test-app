package com.example.ai

import com.example.data.models.CDLCategory
import com.example.data.models.CategoryReadiness
import com.example.data.models.ExamResult
import com.example.data.models.Question
import com.example.data.repository.CDLQuestionRepository
import kotlin.math.ln

/** What a Coach Mac voice quiz draws its questions from. */
enum class QuizFocus(val label: String) {
    WEAK_AREAS("your weak areas"),
    PRE_TRIP("pre-trip inspection"),
    AIR_BRAKES("air brakes"),
    HAZMAT("hazardous materials"),
    COMBINATION("combination vehicles"),
    GENERAL("general knowledge")
}

/** A subtopic the student keeps missing, weighted by how often. */
data class WeakArea(
    val category: CDLCategory,
    val subtopic: String,
    val weight: Int
)

/**
 * Keeps Coach Mac grounded in the app's own study bank. Every question in the bank carries an
 * explanation and an FMCSA manual citation, so the closest questions to what the student asked
 * are handed to Gemini as reference material, and Gemini is told to answer only from them.
 * Also builds the student's profile from their saved exam history, and picks and grades quiz
 * questions.
 */
object CoachKnowledge {

    private val LETTERS = listOf("A", "B", "C", "D", "E", "F")

    private val STOP_WORDS = setOf(
        "the", "and", "for", "you", "your", "are", "what", "whats", "when", "how", "why", "does",
        "do", "is", "an", "of", "to", "in", "on", "at", "it", "its", "that", "this", "with",
        "can", "should", "would", "could", "about", "tell", "me", "my", "im", "please", "explain",
        "give", "again", "there", "they", "their", "from", "have", "has", "be", "or", "if", "so",
        "get", "we", "us", "coach", "mac", "cdl", "test", "exam", "question", "questions", "know",
        "need", "which", "who", "will", "much", "many", "any", "some", "more", "like", "just",
        "than", "then", "into", "out", "up", "down", "was", "were", "been", "being", "did", "not",
        "no", "yes", "all", "also", "only", "very", "same", "other", "each", "every", "one"
    )

    private data class Indexed(
        val question: Question,
        val subtopic: Set<String>,
        val text: Set<String>,
        val answer: Set<String>,
        val explanation: Set<String>
    )

    private val index: List<Indexed> by lazy {
        CDLQuestionRepository.questions.distinctBy { it.questionText }.map { q ->
            Indexed(
                question = q,
                subtopic = tokenize(q.subtopic),
                text = tokenize(q.questionText),
                answer = tokenize(q.options.getOrElse(q.correctIndex) { "" }),
                explanation = tokenize(q.explanation)
            )
        }
    }

    private val docFreq: Map<String, Int> by lazy {
        val counts = HashMap<String, Int>()
        index.forEach { d ->
            (d.subtopic + d.text + d.answer + d.explanation).forEach { t ->
                counts[t] = (counts[t] ?: 0) + 1
            }
        }
        counts
    }

    /** Lower-cases, splits on anything that isn't a letter or digit, drops filler words. */
    fun tokenize(text: String): Set<String> {
        return text.lowercase()
            .replace("glad hands", "gladhands")
            .replace("glad-hands", "gladhands")
            .replace("pre-trip", "pretrip")
            .replace("pre trip", "pretrip")
            .replace("haz mat", "hazmat")
            .split(Regex("[^a-z0-9]+"))
            .filter { it.isNotEmpty() && (it.length >= 2 || it.all { c -> c.isDigit() }) }
            .filter { it !in STOP_WORDS }
            .map { stem(it) }
            .toSet()
    }

    private fun stem(token: String): String {
        var t = token
        if (t.length > 4 && t.endsWith("s") && !t.endsWith("ss")) t = t.dropLast(1)
        t = when {
            t.length > 6 && t.endsWith("able") -> t.dropLast(4)
            t.length > 5 && t.endsWith("ing") -> t.dropLast(3)
            t.length > 4 && t.endsWith("ed") -> t.dropLast(2)
            else -> t
        }
        return t
    }

    /** True when every meaningful word in [query] appears somewhere in the study bank. */
    fun allWordsKnown(query: String): Boolean = repairTerms(tokenize(query)).all { docFreq.containsKey(it) || it.all { c -> c.isDigit() } }

    /**
     * Returns the study-bank questions closest to [query], best first. Rare words count for more
     * than common ones, and a match in the subtopic or question counts for more than one in the
     * explanation.
     */
    fun retrieve(query: String, limit: Int = 4, category: CDLCategory? = null): List<Question> {
        val terms = tokenize(query)
        if (terms.isEmpty()) return emptyList()
        val total = index.size.toDouble().coerceAtLeast(1.0)

        val scored = index
            .filter { category == null || it.question.category == category }
            .map { d ->
                var score = 0.0
                for (t in terms) {
                    val df = docFreq[t] ?: continue
                    val idf = ln(1.0 + total / df)
                    if (t in d.subtopic) score += 3.0 * idf
                    if (t in d.text) score += 2.0 * idf
                    if (t in d.answer) score += 2.0 * idf
                    if (t in d.explanation) score += 1.0 * idf
                }
                d.question to score
            }
            .filter { it.second > 0.0 }
            .sortedByDescending { it.second }

        if (scored.isEmpty()) return emptyList()
        val best = scored.first().second
        return scored
            .filter { it.second >= best * 0.35 }
            .take(limit)
            .map { it.first }
    }

    /**
     * A study-bank match with how well it covers the question: [coverage] is the share of the
     * question's meaningful words (weighted by rarity) found in the match. Words the bank never
     * uses, like a state name, count against it, so off-topic questions score low.
     */
    data class Match(val question: Question, val score: Double, val coverage: Double)

    /**
     * Fixes words the study bank doesn't use, when they are typos of words it does use: two words
     * run together ("theplacards" -> "placard") or one letter off ("placrds" -> "placard").
     * Words that still don't match anything are kept, so off-topic questions stay off-topic.
     */
    fun repairTerms(terms: Set<String>): Set<String> {
        val result = LinkedHashSet<String>()
        for (t in terms) {
            if (docFreq.containsKey(t) || t.all { it.isDigit() }) {
                result.add(t)
                continue
            }
            // Two words run together.
            var split: List<String>? = null
            for (i in 2..(t.length - 2)) {
                val left = t.substring(0, i)
                val right = stem(t.substring(i))
                val leftOk = left in STOP_WORDS || docFreq.containsKey(stem(left))
                if (leftOk && docFreq.containsKey(right) && right.length >= 3) {
                    split = listOfNotNull(if (left in STOP_WORDS) null else stem(left), right)
                    break
                }
            }
            if (split != null) {
                result.addAll(split)
                continue
            }
            // One letter off (typo), for longer words only.
            // Short words only get a missing or extra letter ("chek" -> "check"), not a swapped one,
            // so a word like "texas" doesn't turn into "text".
            if (t.length >= 4) {
                val close = docFreq.keys
                    .filter {
                        it.length >= 4 && kotlin.math.abs(it.length - t.length) <= 1 && it[0] == t[0] &&
                            (t.length >= 5 || it.length != t.length) && withinOneEdit(it, t)
                    }
                    .maxByOrNull { docFreq[it] ?: 0 }
                if (close != null) {
                    result.add(close)
                    continue
                }
            }
            result.add(t)
        }
        return result
    }

    private fun withinOneEdit(a: String, b: String): Boolean {
        if (a == b) return true
        if (kotlin.math.abs(a.length - b.length) > 1) return false
        var i = 0
        var j = 0
        var edits = 0
        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                i++; j++
                continue
            }
            edits++
            if (edits > 1) return false
            when {
                a.length > b.length -> i++
                a.length < b.length -> j++
                else -> { i++; j++ }
            }
        }
        edits += (a.length - i) + (b.length - j)
        return edits <= 1
    }

    fun retrieveScored(query: String, limit: Int = 3, category: CDLCategory? = null): List<Match> {
        val terms = repairTerms(tokenize(query))
        if (terms.isEmpty()) return emptyList()
        val total = index.size.toDouble().coerceAtLeast(1.0)
        val unknownIdf = ln(1.0 + total)
        val idf = terms.associateWith { t ->
            val df = docFreq[t]
            if (df == null) unknownIdf else ln(1.0 + total / df)
        }
        val totalWeight = idf.values.sum().coerceAtLeast(0.0001)

        return index
            .filter { category == null || it.question.category == category }
            .map { d ->
                var score = 0.0
                var covered = 0.0
                for (t in terms) {
                    val w = idf[t] ?: continue
                    var hit = false
                    if (t in d.subtopic) { score += 3.0 * w; hit = true }
                    if (t in d.text) { score += 2.0 * w; hit = true }
                    if (t in d.answer) { score += 2.0 * w; hit = true }
                    if (t in d.explanation) { score += 1.0 * w; hit = true }
                    if (hit) covered += w
                }
                Match(d.question, score, covered / totalWeight)
            }
            .filter { it.score > 0.0 }
            .sortedWith(compareByDescending<Match> { it.coverage }.thenByDescending { it.score })
            .take(limit)
    }

    /** Relaxed wording match for spoken quiz answers when the strict one can't decide. */
    fun gradeLoosely(q: Question, answer: String): Boolean? {
        gradeLocally(q, answer)?.let { return it }
        val answerTokens = tokenize(answer)
        if (answerTokens.isEmpty()) return null
        val overlaps = q.options.map { option ->
            val optionTokens = tokenize(option)
            if (optionTokens.isEmpty()) 0.0
            else optionTokens.count { it in answerTokens }.toDouble() / optionTokens.size
        }
        val bestIdx = overlaps.indices.maxByOrNull { overlaps[it] } ?: return null
        val best = overlaps[bestIdx]
        val second = overlaps.filterIndexed { i, _ -> i != bestIdx }.maxOrNull() ?: 0.0
        return if (best >= 0.34 && best - second >= 0.15) bestIdx == q.correctIndex else null
    }

    /** Formats study-bank questions as the reference block every Coach Mac prompt carries. */
    fun formatReferences(questions: List<Question>): String {
        if (questions.isEmpty()) {
            return "REFERENCE MATERIAL: none. Nothing in the app's study bank matches this question."
        }
        val sb = StringBuilder("REFERENCE MATERIAL from the app's CDL study bank:\n")
        questions.forEachIndexed { i, q ->
            sb.append("[Ref ${i + 1}] Topic: ${q.category.title} / ${cleanSubtopic(q.subtopic)}\n")
            sb.append("Question: ${q.questionText}\n")
            sb.append("Correct answer: ${q.options.getOrElse(q.correctIndex) { "" }}\n")
            sb.append("Explanation: ${q.explanation}\n")
            sb.append("Source: ${q.manualCitation}\n")
        }
        return sb.toString().trim()
    }

    /** Drops the "Phase 1:" style prefix some subtopics carry, so it reads naturally aloud. */
    fun cleanSubtopic(subtopic: String): String {
        return subtopic.replace(Regex("""^Phase\s*\d+\s*:\s*"""), "").trim()
    }

    /** The subtopics this student misses most, counting questions still on their missed list double. */
    fun weakAreas(
        history: List<ExamResult>,
        missedIds: Set<String>,
        limit: Int = 3
    ): List<WeakArea> {
        val counts = HashMap<Pair<CDLCategory, String>, Int>()
        missedIds.forEach { id ->
            CDLQuestionRepository.getQuestionById(id)?.let { q ->
                val key = q.category to q.subtopic
                counts[key] = (counts[key] ?: 0) + 2
            }
        }
        history.take(15).forEach { result ->
            result.questionIds.forEach { id ->
                val q = CDLQuestionRepository.getQuestionById(id)
                val answer = result.userAnswers[id]
                if (q != null && answer != null && answer != q.correctIndex) {
                    val key = q.category to q.subtopic
                    counts[key] = (counts[key] ?: 0) + 1
                }
            }
        }
        return counts.entries
            .sortedByDescending { it.value }
            .take(limit)
            .map { WeakArea(it.key.first, it.key.second, it.value) }
    }

    /** One representative study-bank question for each weak area, for study plans and live talk. */
    fun referencesForWeakAreas(weak: List<WeakArea>, missedIds: Set<String>, perArea: Int = 1): List<Question> {
        val result = mutableListOf<Question>()
        weak.forEach { area ->
            val inArea = CDLQuestionRepository.questions.filter {
                it.category == area.category && it.subtopic == area.subtopic
            }
            val missedFirst = inArea.sortedByDescending { if (it.id in missedIds) 1 else 0 }
            result.addAll(missedFirst.take(perArea))
        }
        return result.distinctBy { it.questionText }
    }

    /** A plain summary of the student's saved history, for personalizing every answer. */
    fun buildStudentProfile(
        history: List<ExamResult>,
        readiness: Map<CDLCategory, CategoryReadiness>,
        overallReadiness: Int,
        missedIds: Set<String>
    ): String {
        if (history.isEmpty()) {
            return "STUDENT PROFILE: a new student. No practice exams have been taken in the app yet."
        }
        val sb = StringBuilder("STUDENT PROFILE (from this student's saved practice history in the app):\n")
        sb.append("Exams taken: ${history.size}. Overall readiness: $overallReadiness percent.\n")
        CDLCategory.entries.forEach { cat ->
            val r = readiness[cat]
            val last = history.firstOrNull { it.category == cat }
            if (r != null && r.testsTaken > 0) {
                sb.append(
                    "${cat.title}: ${r.testsTaken} taken, ${r.testsPassed} passed, average ${r.averageScore} percent, " +
                        "most recent ${last?.scorePercent ?: 0} percent, readiness ${r.readinessScore} percent.\n"
                )
            } else {
                sb.append("${cat.title}: not attempted yet.\n")
            }
        }
        val weak = weakAreas(history, missedIds)
        if (weak.isNotEmpty()) {
            sb.append("Weakest subtopics: ")
            sb.append(weak.joinToString("; ") { "${cleanSubtopic(it.subtopic)} (${it.category.title})" })
            sb.append(".\n")
        }
        sb.append("Questions on the student's missed list right now: ${missedIds.size}.")
        return sb.toString()
    }

    private fun isPreTrip(q: Question): Boolean {
        val s = q.subtopic.lowercase()
        return s.contains("inspect") || s.contains("pre-trip") || s.contains("leak test")
    }

    /** Picks the next quiz question for [focus], avoiding ones already asked this quiz. */
    fun pickQuizQuestion(
        focus: QuizFocus,
        history: List<ExamResult>,
        missedIds: Set<String>,
        askedIds: Set<String>
    ): Question? {
        val all = CDLQuestionRepository.questions
        val pool: List<Question> = when (focus) {
            QuizFocus.PRE_TRIP -> all.filter { isPreTrip(it) }
            QuizFocus.AIR_BRAKES -> all.filter { it.category == CDLCategory.AIR_BRAKES }
            QuizFocus.HAZMAT -> all.filter { it.category == CDLCategory.HAZMAT }
            QuizFocus.COMBINATION -> all.filter { it.category == CDLCategory.COMBINATION_VEHICLES }
            QuizFocus.GENERAL -> all.filter { it.category == CDLCategory.GENERAL_KNOWLEDGE }
            QuizFocus.WEAK_AREAS -> {
                val missed = all.filter { it.id in missedIds }
                val weak = weakAreas(history, missedIds, limit = 4)
                val weakPool = all.filter { q -> weak.any { it.category == q.category && it.subtopic == q.subtopic } }
                when {
                    missed.any { it.id !in askedIds } -> missed
                    weakPool.any { it.id !in askedIds } -> weakPool
                    else -> all
                }
            }
        }.ifEmpty { all }

        val fresh = pool.filter { it.id !in askedIds }
        return (fresh.ifEmpty { pool }).randomOrNull()
    }

    fun quizIntro(focus: QuizFocus): String {
        return "Alright driver, quiz time on ${focus.label}. I'll read each question and the choices. " +
            "Answer out loud with the mic button, or type it."
    }

    /** The question as Coach Mac reads it aloud, choices included. */
    fun quizQuestionText(q: Question, number: Int): String {
        val sb = StringBuilder()
        sb.append("Question $number, on ${cleanSubtopic(q.subtopic)}. ${q.questionText}\n")
        q.options.forEachIndexed { i, option ->
            sb.append("${LETTERS.getOrElse(i) { "${i + 1}" }}: $option\n")
        }
        sb.append("Say the letter, or the answer in your own words.")
        return sb.toString()
    }

    fun correctAnswerText(q: Question): String {
        val letter = LETTERS.getOrElse(q.correctIndex) { "${q.correctIndex + 1}" }
        return "$letter, ${q.options.getOrElse(q.correctIndex) { "" }}"
    }

    /** Feedback built straight from the study bank, used when grading happens without Gemini. */
    fun localQuizFeedback(q: Question, correct: Boolean): String {
        val lead = if (correct) {
            "Correct! The answer is ${correctAnswerText(q)}."
        } else {
            "Not quite. The correct answer is ${correctAnswerText(q)}."
        }
        return "$lead ${q.explanation} Ready for the next question?"
    }

    /** The letter the student said, if their whole answer is just a letter ("B", "option c", "I think it's D"). */
    fun parseLetter(answer: String, optionCount: Int): Int? {
        val normalized = answer.lowercase().trim().replace("’", "'")
        val match = Regex(
            """^(?:(?:i think|i say|i'll say|i will say|my answer is|the answer is|answer is|it is|it's|its)\s+)?""" +
                """(?:it's\s+|its\s+)?(?:(?:option|letter|answer|choice)\s+)?([a-z]{1,3})[\s.!?,]*$"""
        ).find(normalized) ?: return null
        val spoken = mapOf(
            "a" to 0, "ay" to 0, "eh" to 0,
            "b" to 1, "be" to 1, "bee" to 1,
            "c" to 2, "see" to 2, "sea" to 2,
            "d" to 3, "dee" to 3,
            "e" to 4, "f" to 5
        )
        val idx = spoken[match.groupValues[1]] ?: return null
        return if (idx < optionCount) idx else null
    }

    /**
     * Grades an answer without Gemini when that can be done with confidence: a letter, or words
     * that clearly match one option. Returns null when it's unclear, so Gemini judges the meaning.
     */
    fun gradeLocally(q: Question, answer: String): Boolean? {
        val letter = parseLetter(answer, q.options.size)
        if (letter != null) return letter == q.correctIndex

        val answerTokens = tokenize(answer)
        if (answerTokens.isEmpty()) return null
        val overlaps = q.options.map { option ->
            val optionTokens = tokenize(option)
            if (optionTokens.isEmpty()) 0.0
            else optionTokens.count { it in answerTokens }.toDouble() / optionTokens.size
        }
        val bestIdx = overlaps.indices.maxByOrNull { overlaps[it] } ?: return null
        val best = overlaps[bestIdx]
        val second = overlaps.filterIndexed { i, _ -> i != bestIdx }.maxOrNull() ?: 0.0
        return if (best >= 0.6 && best - second >= 0.25) bestIdx == q.correctIndex else null
    }
}

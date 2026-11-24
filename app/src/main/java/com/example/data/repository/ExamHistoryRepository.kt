package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.models.CDLCategory
import com.example.data.models.CategoryReadiness
import com.example.data.models.ExamMode
import com.example.data.models.ExamResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class ExamHistoryRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("cdl_prep_history", Context.MODE_PRIVATE)

    private val _historyFlow = MutableStateFlow<List<ExamResult>>(emptyList())
    val historyFlow: StateFlow<List<ExamResult>> = _historyFlow.asStateFlow()

    private val _bookmarksFlow = MutableStateFlow<Set<String>>(emptySet())
    val bookmarksFlow: StateFlow<Set<String>> = _bookmarksFlow.asStateFlow()

    private val _missedQuestionsFlow = MutableStateFlow<Set<String>>(emptySet())
    val missedQuestionsFlow: StateFlow<Set<String>> = _missedQuestionsFlow.asStateFlow()

    init {
        loadHistory()
        loadBookmarks()
        loadMissed()
    }

    private fun loadHistory() {
        val raw = prefs.getString("exam_history", "[]") ?: "[]"
        try {
            val arr = JSONArray(raw)
            val list = mutableListOf<ExamResult>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val catStr = obj.optString("category", CDLCategory.GENERAL_KNOWLEDGE.name)
                val category = runCatching { CDLCategory.valueOf(catStr) }.getOrDefault(CDLCategory.GENERAL_KNOWLEDGE)
                val modeStr = obj.optString("mode", ExamMode.REAL_SIMULATION.name)
                val mode = runCatching { ExamMode.valueOf(modeStr) }.getOrDefault(ExamMode.REAL_SIMULATION)

                val answersObj = obj.optJSONObject("userAnswers")
                val answersMap = mutableMapOf<String, Int>()
                answersObj?.keys()?.forEach { key ->
                    answersMap[key] = answersObj.getInt(key)
                }

                val qIdsArr = obj.optJSONArray("questionIds")
                val qIds = mutableListOf<String>()
                if (qIdsArr != null) {
                    for (j in 0 until qIdsArr.length()) {
                        qIds.add(qIdsArr.getString(j))
                    }
                }

                list.add(
                    ExamResult(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        category = category,
                        mode = mode,
                        totalQuestions = obj.optInt("totalQuestions", 50),
                        correctCount = obj.optInt("correctCount", 0),
                        scorePercent = obj.optInt("scorePercent", 0),
                        isPassed = obj.optBoolean("isPassed", false),
                        timeSpentSeconds = obj.optInt("timeSpentSeconds", 0),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                        userAnswers = answersMap,
                        questionIds = qIds
                    )
                )
            }
            _historyFlow.value = list.sortedByDescending { it.timestamp }
        } catch (_: Exception) {
            _historyFlow.value = emptyList()
        }
    }

    fun saveExamResult(result: ExamResult) {
        val current = _historyFlow.value.toMutableList()
        current.add(0, result)
        _historyFlow.value = current

        // Update missed questions
        val currentMissed = _missedQuestionsFlow.value.toMutableSet()
        result.userAnswers.forEach { (qId, answer) ->
            val question = CDLQuestionRepository.getQuestionById(qId)
            val baseId = qId.substringBefore("_repeat_")
            if (question != null) {
                if (answer != question.correctIndex) {
                    currentMissed.add(baseId)
                } else {
                    // Answered correctly - remove from missed
                    currentMissed.remove(baseId)
                }
            }
        }
        _missedQuestionsFlow.value = currentMissed
        saveMissedToPrefs(currentMissed)

        // Save history to prefs
        val arr = JSONArray()
        for (item in current) {
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("category", item.category.name)
            obj.put("mode", item.mode.name)
            obj.put("totalQuestions", item.totalQuestions)
            obj.put("correctCount", item.correctCount)
            obj.put("scorePercent", item.scorePercent)
            obj.put("isPassed", item.isPassed)
            obj.put("timeSpentSeconds", item.timeSpentSeconds)
            obj.put("timestamp", item.timestamp)

            val ansObj = JSONObject()
            item.userAnswers.forEach { (k, v) -> ansObj.put(k, v) }
            obj.put("userAnswers", ansObj)

            val qArr = JSONArray()
            item.questionIds.forEach { qArr.put(it) }
            obj.put("questionIds", qArr)

            arr.put(obj)
        }
        prefs.edit().putString("exam_history", arr.toString()).apply()
    }

    private fun loadBookmarks() {
        val set = prefs.getStringSet("bookmarked_questions", emptySet()) ?: emptySet()
        _bookmarksFlow.value = set
    }

    fun toggleBookmark(questionId: String) {
        val baseId = questionId.substringBefore("_repeat_")
        val current = _bookmarksFlow.value.toMutableSet()
        if (current.contains(baseId)) {
            current.remove(baseId)
        } else {
            current.add(baseId)
        }
        _bookmarksFlow.value = current
        prefs.edit().putStringSet("bookmarked_questions", current).apply()
    }

    fun isBookmarked(questionId: String): Boolean {
        val baseId = questionId.substringBefore("_repeat_")
        return _bookmarksFlow.value.contains(baseId)
    }

    private fun loadMissed() {
        val set = prefs.getStringSet("missed_questions", emptySet()) ?: emptySet()
        _missedQuestionsFlow.value = set
    }

    private fun saveMissedToPrefs(set: Set<String>) {
        prefs.edit().putStringSet("missed_questions", set).apply()
    }

    fun getReadiness(category: CDLCategory): CategoryReadiness {
        val history = _historyFlow.value.filter { it.category == category }
        val testsTaken = history.size
        val testsPassed = history.count { it.isPassed }
        val avgScore = if (testsTaken > 0) history.map { it.scorePercent }.average().toInt() else 0

        val categoryQuestions = CDLQuestionRepository.getQuestionsByCategory(category)
        val totalQuestions = categoryQuestions.size
        val missedForCat = _missedQuestionsFlow.value.count { qId ->
            CDLQuestionRepository.questions.any { it.id == qId && it.category == category }
        }
        val mastered = (totalQuestions - missedForCat).coerceAtLeast(0)

        // Readiness calculation weighting test pass rate and mastery
        val readiness = if (testsTaken == 0) {
            0
        } else {
            val passRate = (testsPassed.toFloat() / testsTaken * 50).toInt()
            val scoreWeight = (avgScore * 0.3f).toInt()
            val masteryWeight = ((mastered.toFloat() / totalQuestions.coerceAtLeast(1)) * 20).toInt()
            (passRate + scoreWeight + masteryWeight).coerceIn(0, 100)
        }

        return CategoryReadiness(
            category = category,
            testsTaken = testsTaken,
            testsPassed = testsPassed,
            averageScore = avgScore,
            questionsMastered = mastered,
            totalQuestions = totalQuestions,
            readinessScore = readiness
        )
    }

    fun clearAllData() {
        prefs.edit().clear().apply()
        _historyFlow.value = emptyList()
        _bookmarksFlow.value = emptySet()
        _missedQuestionsFlow.value = emptySet()
    }
}

package com.example.data.repository

import com.example.data.models.CDLCategory
import com.example.data.models.Question

object CDLQuestionRepository {

    val questions: List<Question> = generalKnowledgeQuestions + airBrakesQuestions + hazardousMaterialsQuestions + combinationVehiclesQuestions

    fun getQuestionsByCategory(category: CDLCategory): List<Question> {
        return questions.filter { it.category == category }
    }

    fun getQuestionsForExam(category: CDLCategory, count: Int? = null): List<Question> {
        val categoryQuestions = getQuestionsByCategory(category).shuffled()
        val limit = count ?: category.standardQuestionsCount
        // If we want more than available, loop or pad to fulfill standard test counts
        if (categoryQuestions.isEmpty()) return emptyList()
        if (categoryQuestions.size >= limit) {
            return categoryQuestions.take(limit)
        }
        // Expand/repeat with shuffled copies if user requests full 50/25/30 exam simulation
        val result = mutableListOf<Question>()
        var counter = 1
        while (result.size < limit) {
            val shuffled = categoryQuestions.shuffled()
            for (q in shuffled) {
                if (result.size >= limit) break
                result.add(q.copy(id = "${q.id}_repeat_${counter++}"))
            }
        }
        return result
    }

    fun getQuestionById(id: String): Question? {
        val baseId = id.substringBefore("_repeat_")
        return questions.find { it.id == baseId }
    }
}

package com.example

import com.example.data.models.CDLCategory
import com.example.data.repository.CDLQuestionRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CDLTest {

    @Test
    fun testPassingScoreStandardIs80Percent() {
        assertEquals(80, CDLCategory.GENERAL_KNOWLEDGE.passingScorePercent)
        assertEquals(80, CDLCategory.AIR_BRAKES.passingScorePercent)
        assertEquals(80, CDLCategory.HAZMAT.passingScorePercent)

        // General Knowledge: 50 questions, 40 to pass (80%)
        val gkTotal = 50
        val gkPassing = 40
        assertTrue((gkPassing.toFloat() / gkTotal * 100).toInt() >= CDLCategory.GENERAL_KNOWLEDGE.passingScorePercent)
        assertFalse(((gkPassing - 1).toFloat() / gkTotal * 100).toInt() >= CDLCategory.GENERAL_KNOWLEDGE.passingScorePercent)

        // Air Brakes: 25 questions, 20 to pass (80%)
        val abTotal = 25
        val abPassing = 20
        assertTrue((abPassing.toFloat() / abTotal * 100).toInt() >= CDLCategory.AIR_BRAKES.passingScorePercent)
        assertFalse(((abPassing - 1).toFloat() / abTotal * 100).toInt() >= CDLCategory.AIR_BRAKES.passingScorePercent)

        // HazMat: 30 questions, 24 to pass (80%)
        val hmTotal = 30
        val hmPassing = 24
        assertTrue((hmPassing.toFloat() / hmTotal * 100).toInt() >= CDLCategory.HAZMAT.passingScorePercent)
        assertFalse(((hmPassing - 1).toFloat() / hmTotal * 100).toInt() >= CDLCategory.HAZMAT.passingScorePercent)
    }

    @Test
    fun testQuestionsHaveValidAnswersAndCitations() {
        val allQuestions = CDLQuestionRepository.questions
        assertTrue(allQuestions.isNotEmpty())

        for (q in allQuestions) {
            assertTrue("Options must have at least 3 choices", q.options.size >= 3)
            assertTrue("Correct index must be within range", q.correctIndex in q.options.indices)
            assertNotNull("Citation must be present", q.manualCitation)
            assertTrue("Citation must not be empty", q.manualCitation.isNotBlank())
            assertTrue("Explanation must not be empty", q.explanation.isNotBlank())
        }
    }

    @Test
    fun testCategoryQuestionsRetrieval() {
        val gk = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.GENERAL_KNOWLEDGE)
        val ab = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.AIR_BRAKES)
        val hm = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.HAZMAT)

        assertTrue(gk.isNotEmpty())
        assertTrue(ab.isNotEmpty())
        assertTrue(hm.isNotEmpty())

        assertTrue(gk.all { it.category == CDLCategory.GENERAL_KNOWLEDGE })
        assertTrue(ab.all { it.category == CDLCategory.AIR_BRAKES })
        assertTrue(hm.all { it.category == CDLCategory.HAZMAT })
    }

    @Test
    fun testGeneralKnowledgeFiftyQuestionsIntegrity() {
        val gkQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.GENERAL_KNOWLEDGE)
        assertEquals(50, gkQuestions.size)

        gkQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has valid options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
        }

        // Verify key phases are represented
        val subtopics = gkQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.any { it.contains("Vehicle Inspection") })
        assertTrue(subtopics.any { it.contains("Control & Vehicle Handling") })
        assertTrue(subtopics.any { it.contains("Mountain Driving") })
        assertTrue(subtopics.any { it.contains("Emergencies, Skids") })
        assertTrue(subtopics.any { it.contains("Railway Crossings") })
        assertTrue(subtopics.any { it.contains("Cargo Handling") })
        assertTrue(subtopics.any { it.contains("Hazardous Materials") })
    }

    @Test
    fun testAirBrakesOneHundredTwentyQuestionsIntegrity() {
        val abQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.AIR_BRAKES)
        assertEquals(120, abQuestions.size)

        abQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has at least 4 options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
            assertTrue("Question ${index + 1} manualCitation not blank", q.manualCitation.isNotBlank())
        }

        val subtopics = abQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.contains("Components & Mechanics"))
        assertTrue(subtopics.contains("Sub-Braking Systems"))
        assertTrue(subtopics.contains("Inspection & Leak Tests"))
        assertTrue(subtopics.contains("Safe Driving & Emergency Operations"))
    }

    @Test
    fun testHazardousMaterialsOneHundredTwentyQuestionsIntegrity() {
        val hmQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.HAZMAT)
        assertEquals(120, hmQuestions.size)

        hmQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has at least 4 options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
            assertTrue("Question ${index + 1} manualCitation not blank", q.manualCitation.isNotBlank())
        }

        val subtopics = hmQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.contains("Hazard Classes & Identification"))
        assertTrue(subtopics.contains("Placards & Labeling"))
        assertTrue(subtopics.contains("Shipping Papers & Manifests"))
        assertTrue(subtopics.contains("Driving, Parking & Safe Operations"))
        assertTrue(subtopics.contains("Emergencies, Leaks & Security"))
    }

    @Test
    fun testCombinationVehiclesOneHundredTwentyQuestionsIntegrity() {
        val cvQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.COMBINATION_VEHICLES)
        assertEquals(120, cvQuestions.size)

        cvQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has at least 4 options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
            assertTrue("Question ${index + 1} manualCitation not blank", q.manualCitation.isNotBlank())
        }

        val subtopics = cvQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.contains("Combination Vehicle Safety & Driving"))
        assertTrue(subtopics.contains("Combination Air Brake Systems"))
        assertTrue(subtopics.contains("Coupling & Uncoupling"))
        assertTrue(subtopics.contains("Inspecting Combination Vehicles"))
    }

    @Test
    fun testGeneralKnowledgeOptionLengthBalanceAndRandomization() {
        val gkQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.GENERAL_KNOWLEDGE)
        assertEquals(50, gkQuestions.size)

        // Verify correct answer distribution across all 4 positions (0, 1, 2, 3)
        val indexCounts = gkQuestions.groupingBy { it.correctIndex }.eachCount()
        for (i in 0..3) {
            val count = indexCounts[i] ?: 0
            assertTrue("Position $i should have at least 10 questions, found $count", count >= 10)
        }

        // Verify that the correct option is not strictly longer than distractors in more than 30% of questions
        var correctIsStrictlyLongestCount = 0
        for (q in gkQuestions) {
            val correctLen = q.options[q.correctIndex].length
            val otherLens = q.options.filterIndexed { idx, _ -> idx != q.correctIndex }.map { it.length }
            if (correctLen > otherLens.max()) {
                correctIsStrictlyLongestCount++
            }
            // Length consistency: maximum length difference between any two options in a question is tight (under 20 chars)
            val minLen = q.options.minOf { it.length }
            val maxLen = q.options.maxOf { it.length }
            assertTrue(
                "Options in question ${q.id} should have consistent length (diff was ${maxLen - minLen})",
                maxLen - minLen <= 15
            )
        }

        assertTrue(
            "Correct answer is strictly longest in too many questions ($correctIsStrictlyLongestCount / 50)",
            correctIsStrictlyLongestCount <= 16
        )
    }

    @Test
    fun testAirBrakesOptionLengthBalanceAndRandomization() {
        val abQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.AIR_BRAKES)
        assertEquals(120, abQuestions.size)

        // Verify correct answer distribution across all 4 positions (0, 1, 2, 3)
        val indexCounts = abQuestions.groupingBy { it.correctIndex }.eachCount()
        for (i in 0..3) {
            val count = indexCounts[i] ?: 0
            assertTrue("Position $i should have at least 25 questions, found $count", count >= 25)
        }

        var correctIsStrictlyLongestCount = 0
        for (q in abQuestions) {
            val correctLen = q.options[q.correctIndex].length
            val otherLens = q.options.filterIndexed { idx, _ -> idx != q.correctIndex }.map { it.length }
            if (correctLen > otherLens.max()) {
                correctIsStrictlyLongestCount++
            }
            // Length consistency: maximum length difference between any two options in a question is tight (under 15 chars)
            val minLen = q.options.minOf { it.length }
            val maxLen = q.options.maxOf { it.length }
            assertTrue(
                "Options in question ${q.id} should have consistent length (diff was ${maxLen - minLen})",
                maxLen - minLen <= 15
            )
        }

        assertTrue(
            "Correct answer is strictly longest in too many questions ($correctIsStrictlyLongestCount / 120)",
            correctIsStrictlyLongestCount <= 50
        )
    }

    @Test
    fun testCombinationVehiclesOptionLengthBalanceAndRandomization() {
        val cvQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.COMBINATION_VEHICLES)
        assertEquals(120, cvQuestions.size)

        // Verify correct answer distribution across all 4 positions (0, 1, 2, 3)
        val indexCounts = cvQuestions.groupingBy { it.correctIndex }.eachCount()
        for (i in 0..3) {
            val count = indexCounts[i] ?: 0
            assertTrue("Position $i should have at least 25 questions, found $count", count >= 25)
        }

        var correctIsStrictlyLongestCount = 0
        for (q in cvQuestions) {
            val correctLen = q.options[q.correctIndex].length
            val otherLens = q.options.filterIndexed { idx, _ -> idx != q.correctIndex }.map { it.length }
            if (correctLen > otherLens.max()) {
                correctIsStrictlyLongestCount++
            }
            // Length consistency: maximum length difference between any two options in a question is tight (under 15 chars)
            val minLen = q.options.minOf { it.length }
            val maxLen = q.options.maxOf { it.length }
            assertTrue(
                "Options in question ${q.id} should have consistent length (diff was ${maxLen - minLen})",
                maxLen - minLen <= 15
            )
        }

        assertTrue(
            "Correct answer is strictly longest in too many questions ($correctIsStrictlyLongestCount / 120)",
            correctIsStrictlyLongestCount <= 50
        )
    }

    @Test
    fun testQuestionLengthChoiceRetrievalAndPassingMath() {
        val testLengths = listOf(10, 20, 50)
        val categories = listOf(
            CDLCategory.GENERAL_KNOWLEDGE,
            CDLCategory.AIR_BRAKES,
            CDLCategory.HAZMAT,
            CDLCategory.COMBINATION_VEHICLES
        )

        for (cat in categories) {
            for (len in testLengths) {
                val questions = CDLQuestionRepository.getQuestionsForExam(cat, len)
                assertEquals("Category ${cat.name} with requested length $len", len, questions.size)

                // 80% passing threshold math
                val passingScore = (len * 0.8f).toInt()
                val maxAllowedErrors = (len * 0.2f).toInt()

                // When exactly at passing threshold:
                val passPct = ((passingScore.toFloat() / len) * 100).toInt()
                assertTrue(passPct >= 80)

                // When 1 below passing threshold:
                val failScore = passingScore - 1
                val failPct = ((failScore.toFloat() / len) * 100).toInt()
                assertTrue(failPct < 80)

                // Error count check:
                assertEquals(len - passingScore, maxAllowedErrors)
            }
        }
    }
}


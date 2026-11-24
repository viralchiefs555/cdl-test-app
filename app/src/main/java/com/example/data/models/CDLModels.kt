package com.example.data.models

enum class CDLCategory(
    val title: String,
    val shortName: String,
    val description: String,
    val standardQuestionsCount: Int,
    val passingScorePercent: Int = 80,
    val maxErrorsAllowed: Int,
    val standardTimeMinutes: Int
) {
    GENERAL_KNOWLEDGE(
        title = "General Knowledge",
        shortName = "General",
        description = "Vehicle inspections, shifting, space management, night driving, hazard perception & emergencies.",
        standardQuestionsCount = 50,
        passingScorePercent = 80,
        maxErrorsAllowed = 10,
        standardTimeMinutes = 60
    ),
    AIR_BRAKES(
        title = "Air Brakes",
        shortName = "Air Brakes",
        description = "Dual air systems, safety valves, low pressure warning, spring brakes, slack adjusters & psi limits.",
        standardQuestionsCount = 25,
        passingScorePercent = 80,
        maxErrorsAllowed = 5,
        standardTimeMinutes = 35
    ),
    HAZMAT(
        title = "Hazardous Materials",
        shortName = "HazMat",
        description = "9 hazard classes, shipping papers, placarding rules, emergency response, parking & railroad crossings.",
        standardQuestionsCount = 30,
        passingScorePercent = 80,
        maxErrorsAllowed = 6,
        standardTimeMinutes = 40
    ),
    COMBINATION_VEHICLES(
        title = "Combination Vehicles",
        shortName = "Combinations",
        description = "Coupling & uncoupling, rollover risks, trailer air lines, glad hands & trailer brake valves.",
        standardQuestionsCount = 20,
        passingScorePercent = 80,
        maxErrorsAllowed = 4,
        standardTimeMinutes = 30
    )
}

enum class ExamMode(val title: String, val description: String) {
    REAL_SIMULATION(
        title = "DMV Exam Simulator",
        description = "Full timed test under real exam conditions. Flag questions, no instant answers, realistic 80% cutoff."
    ),
    PRACTICE_STUDY(
        title = "Practice & Study Mode",
        description = "Instant answer feedback with in-depth FMCSA manual explanations after every question."
    ),
    MISSED_QUESTIONS(
        title = "Weak Areas / Missed Questions",
        description = "Target and master all questions you previously answered incorrectly."
    ),
    SAVED_BOOKMARKS(
        title = "Saved Bookmarks",
        description = "Review all questions you flagged for personal study."
    )
}

data class Question(
    val id: String,
    val category: CDLCategory,
    val subtopic: String,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val manualCitation: String
)

data class ExamResult(
    val id: String = java.util.UUID.randomUUID().toString(),
    val category: CDLCategory,
    val mode: ExamMode,
    val totalQuestions: Int,
    val correctCount: Int,
    val scorePercent: Int,
    val isPassed: Boolean,
    val timeSpentSeconds: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val userAnswers: Map<String, Int> = emptyMap(), // questionId -> selectedIndex
    val questionIds: List<String> = emptyList()
)

data class CategoryReadiness(
    val category: CDLCategory,
    val testsTaken: Int,
    val testsPassed: Int,
    val averageScore: Int,
    val questionsMastered: Int,
    val totalQuestions: Int,
    val readinessScore: Int // 0-100%
)

data class MissedQuestionDetail(
    val question: Question,
    val chosenIndex: Int,
    val correctIndex: Int
)

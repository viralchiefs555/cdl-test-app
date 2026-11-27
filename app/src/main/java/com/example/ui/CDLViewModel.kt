package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.CDLCategory
import com.example.data.models.CategoryReadiness
import com.example.data.models.ExamMode
import com.example.data.models.ExamResult
import com.example.data.models.MissedQuestionDetail
import com.example.data.models.Question
import com.example.data.repository.CDLQuestionRepository
import com.example.data.repository.ExamHistoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.ai.AiReviewManager
import com.example.ai.ChatMessage
import com.example.ai.CoachKnowledge
import com.example.ai.CoachLiveVoice
import com.example.ai.CoachMode
import com.example.ai.LiveVoiceStatus
import com.example.ai.MessageSender
import com.example.ai.QuizFocus
import com.example.ai.TtsManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException

enum class Screen {
    HOME,
    EXAM,
    RESULT,
    REVIEW,
    HISTORY,
    CHEAT_SHEET,
    AI_REVIEW,
    PRE_TRIP
}

data class AiReviewState(
    val isLoading: Boolean = false,
    val streamingText: String = "",
    val messages: List<ChatMessage> = emptyList(),
    val error: String? = null,
    val isAutoSpeechEnabled: Boolean = true,
    val activeReviewResult: ExamResult? = null,
    val missedQuestions: List<MissedQuestionDetail> = emptyList(),
    val currentMissedIndex: Int? = null,
    val quiz: QuizState? = null,
    val liveStatus: LiveVoiceStatus = LiveVoiceStatus.OFF,
    val liveError: String? = null
)

/** A Coach Mac voice quiz in progress. */
data class QuizState(
    val focus: QuizFocus = QuizFocus.WEAK_AREAS,
    val current: Question? = null,
    val number: Int = 0,
    val answeredCount: Int = 0,
    val correctCount: Int = 0,
    val awaitingAnswer: Boolean = false,
    val askedIds: Set<String> = emptySet(),
    val lastGradedId: String? = null
)

data class ExamSessionState(
    val category: CDLCategory = CDLCategory.GENERAL_KNOWLEDGE,
    val mode: ExamMode = ExamMode.REAL_SIMULATION,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val userAnswers: Map<String, Int> = emptyMap(), // questionId -> chosen index
    val flaggedQuestionIds: Set<String> = emptySet(),
    val timeRemainingSeconds: Int = 0,
    val totalTimeSeconds: Int = 0,
    val isTimerActive: Boolean = false,
    val isInstantFeedbackRevealed: Boolean = false, // for practice mode
    val isFinished: Boolean = false,
    val showSubmitDialog: Boolean = false,
    val showQuestionDrawer: Boolean = false
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val answeredCount: Int
        get() = userAnswers.size

    val progress: Float
        get() = if (questions.isNotEmpty()) (currentIndex + 1).toFloat() / questions.size else 0f
}

data class CDLUiState(
    val currentScreen: Screen = Screen.HOME,
    val screenStack: List<Screen> = listOf(Screen.HOME),
    val examSession: ExamSessionState = ExamSessionState(),
    val lastResult: ExamResult? = null,
    val reviewFilter: ReviewFilter = ReviewFilter.ALL,
    val reviewQuestions: List<Question> = emptyList(),
    val reviewAnswers: Map<String, Int> = emptyMap(),
    val overallReadiness: Int = 0,
    val categoryReadiness: Map<CDLCategory, CategoryReadiness> = emptyMap(),
    val missedQuestionsCount: Int = 0,
    val bookmarkedQuestionsCount: Int = 0,
    val aiReviewState: AiReviewState = AiReviewState()
)

enum class ReviewFilter {
    ALL,
    MISSED_ONLY,
    FLAGGED_ONLY
}

class CDLViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExamHistoryRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(CDLUiState())
    val uiState: StateFlow<CDLUiState> = _uiState.asStateFlow()


    val history = repository.historyFlow
    val bookmarks = repository.bookmarksFlow
    val missedQuestions = repository.missedQuestionsFlow

    val ttsManager = TtsManager(application.applicationContext)
    private val aiReviewManager = AiReviewManager()
    private var aiJob: Job? = null

    private val liveVoice = CoachLiveVoice()
    private var liveJob: Job? = null

    /** Re-runs the last Coach Mac request that couldn't reach the server (the Retry button). */
    private var lastFailedRequest: (() -> Unit)? = null

    /** Set when the student taps Stop, so the rest of a streaming answer stays quiet. */
    private var suppressSpeechThisTurn = false

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            history.collect {
                refreshReadiness()
            }
        }
        viewModelScope.launch {
            bookmarks.collect { set ->
                _uiState.update { it.copy(bookmarkedQuestionsCount = set.size) }
            }
        }
        viewModelScope.launch {
            missedQuestions.collect { set ->
                _uiState.update { it.copy(missedQuestionsCount = set.size) }
                refreshReadiness()
            }
        }
        refreshReadiness()
    }

    private fun refreshReadiness() {
        val readinessMap = CDLCategory.entries.associateWith { cat ->
            repository.getReadiness(cat)
        }
        val overall = if (readinessMap.isNotEmpty()) {
            readinessMap.values.map { it.readinessScore }.average().toInt()
        } else 0

        _uiState.update {
            it.copy(
                categoryReadiness = readinessMap,
                overallReadiness = overall,
                missedQuestionsCount = repository.missedQuestionsFlow.value.size,
                bookmarkedQuestionsCount = repository.bookmarksFlow.value.size
            )
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.update {
            val newStack = it.screenStack + screen
            it.copy(currentScreen = screen, screenStack = newStack)
        }
    }

    fun navigateBack(): Boolean {
        val currentStack = _uiState.value.screenStack
        if (currentStack.size > 1) {
            val newStack = currentStack.dropLast(1)
            val prevScreen = newStack.last()

            // If navigating back away from exam, cancel timer
            if (_uiState.value.currentScreen == Screen.EXAM) {
                stopTimer()
            }
            if (_uiState.value.currentScreen == Screen.AI_REVIEW) {
                ttsManager.stop()
                aiJob?.cancel()
                stopLiveVoice()
            }

            _uiState.update {
                it.copy(currentScreen = prevScreen, screenStack = newStack)
            }
            return true
        }
        return false
    }

    fun startExam(
        category: CDLCategory,
        mode: ExamMode,
        questionCount: Int? = null
    ) {
        val questionList = CDLQuestionRepository.getQuestionsForExam(category, questionCount)
        if (questionList.isEmpty()) return

        val totalTime = if (mode == ExamMode.REAL_SIMULATION) {
            val minutes = when {
                questionList.size <= 10 -> 15
                questionList.size <= 20 -> 25
                else -> category.standardTimeMinutes
            }
            minutes * 60
        } else {
            0
        }

        _uiState.update {
            it.copy(
                currentScreen = Screen.EXAM,
                screenStack = it.screenStack + Screen.EXAM,
                examSession = ExamSessionState(
                    category = category,
                    mode = mode,
                    questions = questionList,
                    currentIndex = 0,
                    userAnswers = emptyMap(),
                    flaggedQuestionIds = emptySet(),
                    timeRemainingSeconds = totalTime,
                    totalTimeSeconds = totalTime,
                    isTimerActive = (mode == ExamMode.REAL_SIMULATION),
                    isInstantFeedbackRevealed = false,
                    isFinished = false
                )
            )
        }

        if (mode == ExamMode.REAL_SIMULATION) {
            startTimer()
        }
    }

    fun startMissedQuestionsExam() {
        val missedIds = repository.missedQuestionsFlow.value
        if (missedIds.isEmpty()) return

        val missedList = missedIds.mapNotNull { id ->
            CDLQuestionRepository.getQuestionById(id)
        }.shuffled()

        if (missedList.isEmpty()) return

        _uiState.update {
            it.copy(
                currentScreen = Screen.EXAM,
                screenStack = it.screenStack + Screen.EXAM,
                examSession = ExamSessionState(
                    category = CDLCategory.GENERAL_KNOWLEDGE,
                    mode = ExamMode.MISSED_QUESTIONS,
                    questions = missedList,
                    currentIndex = 0,
                    userAnswers = emptyMap(),
                    flaggedQuestionIds = emptySet(),
                    timeRemainingSeconds = 0,
                    totalTimeSeconds = 0,
                    isTimerActive = false,
                    isInstantFeedbackRevealed = false,
                    isFinished = false
                )
            )
        }
    }

    fun startBookmarksExam() {
        val bookmarkIds = repository.bookmarksFlow.value
        if (bookmarkIds.isEmpty()) return

        val bookmarkList = bookmarkIds.mapNotNull { id ->
            CDLQuestionRepository.getQuestionById(id)
        }.shuffled()

        if (bookmarkList.isEmpty()) return

        _uiState.update {
            it.copy(
                currentScreen = Screen.EXAM,
                screenStack = it.screenStack + Screen.EXAM,
                examSession = ExamSessionState(
                    category = CDLCategory.GENERAL_KNOWLEDGE,
                    mode = ExamMode.SAVED_BOOKMARKS,
                    questions = bookmarkList,
                    currentIndex = 0,
                    userAnswers = emptyMap(),
                    flaggedQuestionIds = emptySet(),
                    timeRemainingSeconds = 0,
                    totalTimeSeconds = 0,
                    isTimerActive = false,
                    isInstantFeedbackRevealed = false,
                    isFinished = false
                )
            )
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _uiState.value.examSession
                if (!current.isTimerActive || current.isFinished) break

                val remaining = current.timeRemainingSeconds - 1
                if (remaining <= 0) {
                    _uiState.update {
                        it.copy(
                            examSession = it.examSession.copy(
                                timeRemainingSeconds = 0,
                                isTimerActive = false
                            )
                        )
                    }
                    submitExam(isAutoSubmittedOnTimeout = true)
                    break
                } else {
                    _uiState.update {
                        it.copy(
                            examSession = it.examSession.copy(timeRemainingSeconds = remaining)
                        )
                    }
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun selectOption(optionIndex: Int) {
        val session = _uiState.value.examSession
        val currentQ = session.currentQuestion ?: return

        // Once answered in exam mode, keep the recorded choice
        if (session.userAnswers.containsKey(currentQ.id)) {
            return
        }

        val updatedAnswers = session.userAnswers.toMutableMap()
        updatedAnswers[currentQ.id] = optionIndex

        // Always reveal feedback immediately after question is answered in exam mode & practice mode
        _uiState.update {
            it.copy(
                examSession = it.examSession.copy(
                    userAnswers = updatedAnswers,
                    isInstantFeedbackRevealed = true
                )
            )
        }
    }

    fun toggleFlagCurrentQuestion() {
        val session = _uiState.value.examSession
        val currentQ = session.currentQuestion ?: return

        val flagged = session.flaggedQuestionIds.toMutableSet()
        if (flagged.contains(currentQ.id)) {
            flagged.remove(currentQ.id)
        } else {
            flagged.add(currentQ.id)
        }

        _uiState.update {
            it.copy(
                examSession = it.examSession.copy(flaggedQuestionIds = flagged)
            )
        }
    }

    fun toggleBookmark(questionId: String) {
        repository.toggleBookmark(questionId)
    }

    fun isBookmarked(questionId: String): Boolean {
        return repository.isBookmarked(questionId)
    }

    fun nextQuestion() {
        val session = _uiState.value.examSession
        if (session.currentIndex < session.questions.size - 1) {
            _uiState.update {
                it.copy(
                    examSession = it.examSession.copy(
                        currentIndex = session.currentIndex + 1,
                        isInstantFeedbackRevealed = session.userAnswers.containsKey(
                            session.questions[session.currentIndex + 1].id
                        )
                    )
                )
            }
        } else {
            // Reached last question, prompt submit
            _uiState.update {
                it.copy(examSession = it.examSession.copy(showSubmitDialog = true))
            }
        }
    }

    fun previousQuestion() {
        val session = _uiState.value.examSession
        if (session.currentIndex > 0) {
            val prevIndex = session.currentIndex - 1
            _uiState.update {
                it.copy(
                    examSession = it.examSession.copy(
                        currentIndex = prevIndex,
                        isInstantFeedbackRevealed = session.userAnswers.containsKey(
                            session.questions[prevIndex].id
                        )
                    )
                )
            }
        }
    }

    fun jumpToQuestion(index: Int) {
        val session = _uiState.value.examSession
        if (index in session.questions.indices) {
            _uiState.update {
                it.copy(
                    examSession = it.examSession.copy(
                        currentIndex = index,
                        showQuestionDrawer = false,
                        isInstantFeedbackRevealed = session.userAnswers.containsKey(
                            session.questions[index].id
                        )
                    )
                )
            }
        }
    }

    fun toggleQuestionDrawer(show: Boolean) {
        _uiState.update {
            it.copy(examSession = it.examSession.copy(showQuestionDrawer = show))
        }
    }

    fun setShowSubmitDialog(show: Boolean) {
        _uiState.update {
            it.copy(examSession = it.examSession.copy(showSubmitDialog = show))
        }
    }

    fun submitExam(isAutoSubmittedOnTimeout: Boolean = false) {
        stopTimer()
        val session = _uiState.value.examSession
        if (session.questions.isEmpty()) return

        var correctCount = 0
        session.questions.forEach { q ->
            val answer = session.userAnswers[q.id]
            if (answer != null && answer == q.correctIndex) {
                correctCount++
            }
        }

        val total = session.questions.size
        val scorePercent = ((correctCount.toFloat() / total) * 100).toInt()
        val isPassed = scorePercent >= session.category.passingScorePercent

        val timeSpent = session.totalTimeSeconds - session.timeRemainingSeconds

        val result = ExamResult(
            category = session.category,
            mode = session.mode,
            totalQuestions = total,
            correctCount = correctCount,
            scorePercent = scorePercent,
            isPassed = isPassed,
            timeSpentSeconds = if (timeSpent > 0) timeSpent else 60,
            userAnswers = session.userAnswers,
            questionIds = session.questions.map { it.id }
        )

        repository.saveExamResult(result)
        refreshReadiness()

        _uiState.update {
            it.copy(
                currentScreen = Screen.RESULT,
                screenStack = it.screenStack + Screen.RESULT,
                lastResult = result,
                examSession = it.examSession.copy(
                    isFinished = true,
                    showSubmitDialog = false
                )
            )
        }
    }

    fun openReview(result: ExamResult? = null, filter: ReviewFilter = ReviewFilter.ALL) {
        val targetResult = result ?: _uiState.value.lastResult ?: return
        val qList = targetResult.questionIds.mapNotNull { id ->
            CDLQuestionRepository.getQuestionById(id)
        }

        _uiState.update {
            it.copy(
                currentScreen = Screen.REVIEW,
                screenStack = it.screenStack + Screen.REVIEW,
                reviewFilter = filter,
                reviewQuestions = qList,
                reviewAnswers = targetResult.userAnswers
            )
        }
    }

    fun setReviewFilter(filter: ReviewFilter) {
        _uiState.update { it.copy(reviewFilter = filter) }
    }

    fun clearAllHistory() {
        repository.clearAllData()
        refreshReadiness()
    }


    // =============================================================================================
    // Coach Mac
    // =============================================================================================

    private val sentenceEnd = Regex("""[.!?]+["')\]]*\s+""")

    private fun currentProfile(): String {
        val state = _uiState.value
        return CoachKnowledge.buildStudentProfile(
            history = repository.historyFlow.value,
            readiness = state.categoryReadiness,
            overallReadiness = state.overallReadiness,
            missedIds = repository.missedQuestionsFlow.value
        )
    }

    private fun speakIfAuto(text: String) {
        if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
            ttsManager.speak(text, TextToSpeech.QUEUE_FLUSH)
        }
    }

    private fun updateAi(transform: (AiReviewState) -> AiReviewState) {
        _uiState.update { s -> s.copy(aiReviewState = transform(s.aiReviewState)) }
    }

    /**
     * Streams one Coach Mac reply, showing it as it arrives and speaking each finished sentence
     * right away, so the student hears the answer while the rest is still coming in.
     */
    private suspend fun streamAndSpeak(
        history: List<ChatMessage>,
        prompt: String,
        mode: CoachMode
    ): String {
        val full = StringBuilder()
        var spokenCursor = 0
        var firstUtterance = true

        aiReviewManager.streamResponse(history, prompt, mode).collect { chunk ->
            full.append(chunk)
            val current = full.toString()
            updateAi { it.copy(isLoading = false, streamingText = current) }

            if (_uiState.value.aiReviewState.isAutoSpeechEnabled && !suppressSpeechThisTurn) {
                while (true) {
                    val unvoiced = current.substring(spokenCursor)
                    val match = sentenceEnd.find(unvoiced) ?: break
                    val end = match.range.last + 1
                    val sentence = unvoiced.substring(0, end).trim()
                    spokenCursor += end
                    if (sentence.isNotBlank()) {
                        ttsManager.speak(
                            sentence,
                            if (firstUtterance) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
                        )
                        firstUtterance = false
                    }
                }
            }
        }

        val all = full.toString()
        if (_uiState.value.aiReviewState.isAutoSpeechEnabled && !suppressSpeechThisTurn && spokenCursor < all.length) {
            val remaining = all.substring(spokenCursor).trim()
            if (remaining.isNotBlank()) {
                ttsManager.speak(
                    remaining,
                    if (firstUtterance) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
                )
            }
        }
        return all.trim()
    }

    /**
     * Runs one Coach Mac turn. [displayMessages] is the chat as shown, including the student's new
     * message if there is one; [historyForModel] is the earlier conversation Gemini remembers. When
     * the server can't be reached, [fallback] supplies exact app data if there is any, otherwise
     * Coach Mac says it can't answer, and the Retry button re-runs the same turn.
     * [onComplete] gets the final text and whether it came from Gemini.
     */
    private fun runCoachTurn(
        displayMessages: List<ChatMessage>,
        historyForModel: List<ChatMessage>,
        prompt: String,
        mode: CoachMode,
        fallback: (() -> String)? = null,
        onComplete: ((String, Boolean) -> Unit)? = null
    ) {
        ttsManager.stop()
        aiJob?.cancel()
        stopLiveVoice()
        suppressSpeechThisTurn = false

        updateAi {
            it.copy(isLoading = true, streamingText = "", messages = displayMessages, error = null)
        }

        val retry: () -> Unit = {
            runCoachTurn(displayMessages, historyForModel, prompt, mode, fallback, onComplete)
        }

        aiJob = viewModelScope.launch {
            try {
                val reply = streamAndSpeak(historyForModel, prompt, mode)
                val finalText = reply.ifBlank {
                    "Sorry, I lost my train of thought there. Could you say that again?"
                }
                updateAi {
                    it.copy(
                        isLoading = false,
                        streamingText = "",
                        messages = displayMessages + ChatMessage(sender = MessageSender.AI, text = finalText),
                        error = null
                    )
                }
                lastFailedRequest = null
                onComplete?.invoke(finalText, true)
            } catch (e: TimeoutCancellationException) {
                Log.w("CDLViewModel", "Coach Mac request timed out", e)
                handleCoachFailure(displayMessages, fallback, retry, onComplete)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("CDLViewModel", "Coach Mac request failed", e)
                handleCoachFailure(displayMessages, fallback, retry, onComplete)
            }
        }
    }

    private fun handleCoachFailure(
        displayMessages: List<ChatMessage>,
        fallback: (() -> String)?,
        retry: () -> Unit,
        onComplete: ((String, Boolean) -> Unit)?
    ) {
        val text = fallback?.invoke() ?: aiReviewManager.offlineNotice()
        lastFailedRequest = retry
        updateAi {
            it.copy(
                isLoading = false,
                streamingText = "",
                messages = displayMessages + ChatMessage(sender = MessageSender.AI, text = text),
                error = "Couldn't reach the Coach Mac server. Check your connection, then tap Retry."
            )
        }
        speakIfAuto(text)
        onComplete?.invoke(text, false)
    }

    /** The Retry button: re-runs the request that failed, or restarts the session. */
    fun retryLastCoachRequest() {
        val retry = lastFailedRequest
        if (retry != null) {
            lastFailedRequest = null
            retry()
        } else {
            startAiReview(_uiState.value.aiReviewState.activeReviewResult)
        }
    }

    fun startAiReview(result: ExamResult? = null) {
        val targetResult = result ?: _uiState.value.lastResult
        val missed = targetResult?.let { res ->
            res.questionIds.mapNotNull { qId ->
                val q = CDLQuestionRepository.getQuestionById(qId)
                val chosen = res.userAnswers[qId]
                if (q != null && chosen != null && chosen != q.correctIndex) {
                    MissedQuestionDetail(question = q, chosenIndex = chosen, correctIndex = q.correctIndex)
                } else null
            }.distinctBy { it.question.questionText }
        } ?: emptyList()

        ttsManager.stop()
        aiJob?.cancel()
        stopLiveVoice()
        lastFailedRequest = null

        val alreadyOnCoach = _uiState.value.currentScreen == Screen.AI_REVIEW
        _uiState.update {
            it.copy(
                currentScreen = Screen.AI_REVIEW,
                screenStack = if (alreadyOnCoach) it.screenStack else it.screenStack + Screen.AI_REVIEW,
                aiReviewState = AiReviewState(
                    isLoading = true,
                    isAutoSpeechEnabled = it.aiReviewState.isAutoSpeechEnabled,
                    activeReviewResult = targetResult,
                    missedQuestions = missed
                )
            )
        }

        val profile = currentProfile()
        val prompt = if (targetResult != null) {
            aiReviewManager.createInitialReviewPrompt(targetResult, profile)
        } else {
            aiReviewManager.createGeneralTutorPrompt(profile)
        }
        runCoachTurn(
            displayMessages = emptyList(),
            historyForModel = emptyList(),
            prompt = prompt,
            mode = CoachMode.DEBRIEF,
            fallback = {
                if (targetResult != null) aiReviewManager.generateLocalFallbackDebrief(targetResult)
                else aiReviewManager.generateLocalGeneralGreeting()
            }
        )
    }

    fun startStepByStepMissedReview() {
        val missed = _uiState.value.aiReviewState.missedQuestions
        if (missed.isEmpty()) return
        reviewMissedQuestionAtIndex(0)
    }

    fun goToNextMissedQuestion() {
        val state = _uiState.value.aiReviewState
        val nextIndex = (state.currentMissedIndex ?: -1) + 1
        if (nextIndex < state.missedQuestions.size) {
            reviewMissedQuestionAtIndex(nextIndex)
        } else {
            ttsManager.stop()
            aiJob?.cancel()
            val finishText = "Great work driver! You have gone over all ${state.missedQuestions.size} questions you missed on this exam. You're ready to ace your retest, or you can ask me anything else!"
            val userMsg = ChatMessage(sender = MessageSender.USER, text = "I'm ready for the next question.")
            val aiMsg = ChatMessage(sender = MessageSender.AI, text = finishText)
            updateAi {
                it.copy(
                    isLoading = false,
                    streamingText = "",
                    currentMissedIndex = null,
                    error = null,
                    messages = it.messages + userMsg + aiMsg
                )
            }
            speakIfAuto(finishText)
        }
    }

    fun goToPreviousMissedQuestion() {
        val state = _uiState.value.aiReviewState
        val prevIndex = (state.currentMissedIndex ?: 1) - 1
        if (prevIndex >= 0) {
            reviewMissedQuestionAtIndex(prevIndex)
        }
    }

    fun reviewMissedQuestionAtIndex(index: Int) {
        val state = _uiState.value.aiReviewState
        val missedList = state.missedQuestions
        val item = missedList.getOrNull(index) ?: return

        val userActionText = if (index == 0 && state.currentMissedIndex == null) {
            "Let's review each one of the questions that I missed step by step."
        } else {
            "Review missed question ${index + 1} of ${missedList.size}: ${CoachKnowledge.cleanSubtopic(item.question.subtopic)}"
        }
        val userMsg = ChatMessage(sender = MessageSender.USER, text = userActionText)

        updateAi { it.copy(currentMissedIndex = index, quiz = null) }

        runCoachTurn(
            displayMessages = state.messages + userMsg,
            historyForModel = state.messages,
            prompt = aiReviewManager.createStepByStepPrompt(item, index, missedList.size),
            mode = CoachMode.DEBRIEF,
            fallback = { aiReviewManager.generateLocalStepReview(item, index, missedList.size) }
        )
    }

    private fun containsAny(text: String, words: List<String>): Boolean = words.any { text.contains(it) }

    private val readyWords = listOf(
        "ready", "next", "yes", "yep", "yeah", "sure", "continue", "go ahead", "move on", "another", "keep going"
    )

    private fun isReady(lower: String): Boolean {
        return containsAny(lower, readyWords) || lower == "ok" || lower == "okay" || lower == "go"
    }

    fun sendUserChatMessage(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isBlank()) return

        val state = _uiState.value.aiReviewState
        val lower = trimmed.lowercase()

        // A voice quiz in progress gets the first look at what the student said.
        val quiz = state.quiz
        if (quiz != null && handleQuizInput(trimmed, lower, quiz)) return

        // Starting a quiz.
        if (containsAny(lower, listOf("quiz me", "quiz on", "start a quiz", "start quiz", "test me", "voice quiz", "give me a quiz"))) {
            startQuiz(quizFocusFrom(lower), trimmed)
            return
        }

        // A personal study plan.
        if (containsAny(lower, listOf("study plan", "plan for me", "make me a plan", "what should i study", "how should i study"))) {
            requestStudyPlan(trimmed)
            return
        }

        // Starting or stepping through the missed-question review.
        val isRequestingMissedReview = containsAny(
            lower,
            listOf(
                "review each", "questions that i missed", "questions i missed", "wrong questions",
                "missed questions", "step by step", "go over each", "go over missed", "review the questions"
            )
        )
        if (isRequestingMissedReview && state.missedQuestions.isNotEmpty()) {
            reviewMissedQuestionAtIndex(state.currentMissedIndex ?: 0)
            return
        }
        if (state.currentMissedIndex != null && isReady(lower)) {
            goToNextMissedQuestion()
            return
        }

        // An open question, answered from the study bank.
        val currentMissed = state.currentMissedIndex?.let { state.missedQuestions.getOrNull(it) }
        val pinned = listOfNotNull(currentMissed?.question, state.quiz?.current)
        val references = (pinned + CoachKnowledge.retrieve(retrievalQuery(trimmed, state.messages), limit = 4))
            .distinctBy { it.questionText }
            .take(5)

        val prompt = aiReviewManager.createTutorPrompt(
            userText = trimmed,
            profile = currentProfile(),
            references = CoachKnowledge.formatReferences(references),
            currentMissed = currentMissed,
            missedIndex = state.currentMissedIndex,
            totalMissed = state.missedQuestions.size
        )
        val fallback: (() -> String)? = if (currentMissed != null) {
            { aiReviewManager.offlineWithReference(currentMissed.question) }
        } else {
            null
        }

        runCoachTurn(
            displayMessages = state.messages + ChatMessage(sender = MessageSender.USER, text = trimmed),
            historyForModel = state.messages,
            prompt = prompt,
            mode = CoachMode.TUTOR,
            fallback = fallback
        )
    }

    /** Short follow-ups ("why?", "what about the trailer?") borrow words from the last few messages. */
    private fun retrievalQuery(userText: String, history: List<ChatMessage>): String {
        if (CoachKnowledge.tokenize(userText).size >= 3) return userText
        val recent = history.takeLast(2).joinToString(" ") { it.text }
        return "$userText $recent"
    }

    private fun quizFocusFrom(lower: String): QuizFocus = when {
        containsAny(lower, listOf("pre-trip", "pre trip", "pretrip", "inspection")) -> QuizFocus.PRE_TRIP
        containsAny(lower, listOf("air brake", "brakes")) -> QuizFocus.AIR_BRAKES
        containsAny(lower, listOf("hazmat", "haz mat", "hazardous", "placard")) -> QuizFocus.HAZMAT
        containsAny(lower, listOf("combination", "coupling", "doubles", "triples", "trailer")) -> QuizFocus.COMBINATION
        containsAny(lower, listOf("general knowledge", "general")) -> QuizFocus.GENERAL
        else -> QuizFocus.WEAK_AREAS
    }

    fun requestStudyPlan(userText: String = "Make me a study plan") {
        val state = _uiState.value.aiReviewState
        val missedIds = repository.missedQuestionsFlow.value
        val weak = CoachKnowledge.weakAreas(repository.historyFlow.value, missedIds)
        val references = CoachKnowledge.referencesForWeakAreas(weak, missedIds)
        runCoachTurn(
            displayMessages = state.messages + ChatMessage(sender = MessageSender.USER, text = userText),
            historyForModel = state.messages,
            prompt = aiReviewManager.createStudyPlanPrompt(
                profile = currentProfile(),
                references = CoachKnowledge.formatReferences(references),
                userText = userText
            ),
            mode = CoachMode.PLAN
        )
    }

    // ---------------------------------------------------------------------------------------------
    // Voice quiz: Coach Mac reads a study-bank question aloud, the student answers by voice, and
    // the answer is graded against the bank (by letter or clear wording locally, otherwise by Gemini).
    // ---------------------------------------------------------------------------------------------

    fun startQuiz(focus: QuizFocus = QuizFocus.WEAK_AREAS, userText: String? = null) {
        stopLiveVoice()
        updateAi { it.copy(quiz = QuizState(focus = focus), currentMissedIndex = null, error = null) }
        askNextQuizQuestion(userText)
    }

    fun nextQuizQuestion() {
        askNextQuizQuestion("Next question")
    }

    private fun askNextQuizQuestion(userText: String?) {
        val state = _uiState.value.aiReviewState
        val quiz = state.quiz ?: return
        val q = CoachKnowledge.pickQuizQuestion(
            focus = quiz.focus,
            history = repository.historyFlow.value,
            missedIds = repository.missedQuestionsFlow.value,
            askedIds = quiz.askedIds
        )
        if (q == null) {
            endQuiz(userText)
            return
        }

        ttsManager.stop()
        aiJob?.cancel()
        lastFailedRequest = null

        val number = quiz.number + 1
        val intro = if (quiz.number == 0) CoachKnowledge.quizIntro(quiz.focus) + "\n" else ""
        val text = intro + CoachKnowledge.quizQuestionText(q, number)
        val newMessages = state.messages +
            listOfNotNull(userText?.let { ChatMessage(sender = MessageSender.USER, text = it) }) +
            ChatMessage(sender = MessageSender.AI, text = text)

        updateAi {
            it.copy(
                isLoading = false,
                streamingText = "",
                error = null,
                messages = newMessages,
                quiz = quiz.copy(
                    current = q,
                    number = number,
                    awaitingAnswer = true,
                    askedIds = quiz.askedIds + q.id
                )
            )
        }
        speakIfAuto(text)
    }

    /** Returns true when the quiz handled the message. */
    private fun handleQuizInput(text: String, lower: String, quiz: QuizState): Boolean {
        if (containsAny(lower, listOf("stop quiz", "end quiz", "end the quiz", "stop the quiz", "quit", "i'm done", "im done", "that's enough")) ||
            lower == "stop" || lower == "done"
        ) {
            endQuiz(text)
            return true
        }

        if (containsAny(lower, listOf("quiz me", "quiz on", "start a quiz", "start quiz", "new quiz"))) {
            startQuiz(quizFocusFrom(lower), text)
            return true
        }

        val q = quiz.current
        if (quiz.awaitingAnswer && q != null) {
            gradeQuizAnswer(text, lower, q)
            return true
        }

        if (isReady(lower) && text.length <= 40) {
            askNextQuizQuestion(text)
            return true
        }
        // Anything else is a follow-up question; answer it normally and keep the quiz going.
        return false
    }

    private fun gradeQuizAnswer(answer: String, lower: String, q: Question) {
        val state = _uiState.value.aiReviewState
        val userMsg = ChatMessage(sender = MessageSender.USER, text = answer)
        updateAi { s -> s.copy(quiz = s.quiz?.copy(awaitingAnswer = false)) }

        if (containsAny(lower, listOf("skip", "pass on this"))) {
            ttsManager.stop()
            aiJob?.cancel()
            val feedback = CoachKnowledge.localQuizFeedback(q, correct = false)
            updateAi {
                it.copy(
                    isLoading = false,
                    streamingText = "",
                    error = null,
                    messages = state.messages + userMsg + ChatMessage(sender = MessageSender.AI, text = feedback)
                )
            }
            recordQuizAnswer(q, correct = false)
            speakIfAuto(feedback)
            return
        }

        val localVerdict = CoachKnowledge.gradeLocally(q, answer)
        runCoachTurn(
            displayMessages = state.messages + userMsg,
            historyForModel = state.messages,
            prompt = aiReviewManager.createQuizGradePrompt(q, answer, localVerdict),
            mode = CoachMode.QUIZ_GRADE,
            fallback = {
                if (localVerdict != null) CoachKnowledge.localQuizFeedback(q, localVerdict)
                else aiReviewManager.offlineQuizNotice(q)
            },
            onComplete = { reply, fromGemini ->
                val verdict = localVerdict ?: if (fromGemini) AiReviewManager.parseVerdict(reply) else null
                recordQuizAnswer(q, correct = verdict == true)
            }
        )
    }

    private fun recordQuizAnswer(q: Question, correct: Boolean) {
        updateAi { s ->
            val quiz = s.quiz
            if (quiz == null || quiz.lastGradedId == q.id) {
                s
            } else {
                s.copy(
                    quiz = quiz.copy(
                        answeredCount = quiz.answeredCount + 1,
                        correctCount = quiz.correctCount + if (correct) 1 else 0,
                        lastGradedId = q.id
                    )
                )
            }
        }
    }

    fun endQuiz(userText: String? = "Stop the quiz") {
        val state = _uiState.value.aiReviewState
        val quiz = state.quiz ?: return
        ttsManager.stop()
        aiJob?.cancel()

        val summary = if (quiz.answeredCount == 0) {
            "Quiz stopped. Say quiz me whenever you want another round."
        } else {
            val percent = quiz.correctCount * 100 / quiz.answeredCount
            val note = if (percent >= 80) {
                "That's passing territory. Nice work, driver."
            } else {
                "Keep at it. Missed Questions mode on the home screen is a great way to lock these in."
            }
            "Quiz finished. You got ${quiz.correctCount} out of ${quiz.answeredCount} right, $percent percent. $note"
        }
        val newMessages = state.messages +
            listOfNotNull(userText?.let { ChatMessage(sender = MessageSender.USER, text = it) }) +
            ChatMessage(sender = MessageSender.AI, text = summary)
        updateAi {
            it.copy(isLoading = false, streamingText = "", error = null, messages = newMessages, quiz = null)
        }
        speakIfAuto(summary)
    }

    // ---------------------------------------------------------------------------------------------
    // Live voice: a hands-free, natural-voice conversation through the Gemini Live API.
    // ---------------------------------------------------------------------------------------------

    /** Call only once the RECORD_AUDIO permission has been granted. */
    fun startLiveVoice() {
        val status = _uiState.value.aiReviewState.liveStatus
        if (status == LiveVoiceStatus.CONNECTING || status == LiveVoiceStatus.LIVE) return

        ttsManager.stop()
        aiJob?.cancel()
        updateAi { it.copy(liveStatus = LiveVoiceStatus.CONNECTING, liveError = null, isLoading = false, streamingText = "") }

        val missedIds = repository.missedQuestionsFlow.value
        val weak = CoachKnowledge.weakAreas(repository.historyFlow.value, missedIds, limit = 4)
        val missedQuestions = missedIds.mapNotNull { CDLQuestionRepository.getQuestionById(it) }.take(10)
        val examMissed = _uiState.value.aiReviewState.missedQuestions.map { it.question }
        val references = (examMissed + missedQuestions + CoachKnowledge.referencesForWeakAreas(weak, missedIds, perArea = 2))
            .distinctBy { it.questionText }
            .take(16)
        val instruction = aiReviewManager.createLiveSystemInstruction(
            profile = currentProfile(),
            references = CoachKnowledge.formatReferences(references)
        )

        liveJob?.cancel()
        liveJob = viewModelScope.launch {
            try {
                liveVoice.start(instruction)
                updateAi { it.copy(liveStatus = LiveVoiceStatus.LIVE) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("CDLViewModel", "Live voice failed to start", e)
                runCatching { liveVoice.stop() }
                updateAi {
                    it.copy(
                        liveStatus = LiveVoiceStatus.ERROR,
                        liveError = "Live voice isn't available right now. Coach Mac's regular voice and chat still work."
                    )
                }
            }
        }
    }

    fun stopLiveVoice() {
        val status = _uiState.value.aiReviewState.liveStatus
        liveJob?.cancel()
        liveJob = null
        if (liveVoice.isActive) {
            liveVoice.stopNow()
            viewModelScope.launch { runCatching { liveVoice.stop() } }
        }
        if (status != LiveVoiceStatus.OFF) {
            updateAi { it.copy(liveStatus = LiveVoiceStatus.OFF, liveError = null) }
        }
    }

    fun dismissLiveError() {
        updateAi { it.copy(liveStatus = LiveVoiceStatus.OFF, liveError = null) }
    }

    fun speakMessage(text: String) {
        suppressSpeechThisTurn = false
        ttsManager.speak(text)
    }

    fun stopSpeaking() {
        suppressSpeechThisTurn = true
        ttsManager.stop()
    }

    fun toggleMuteTts() {
        ttsManager.toggleMute()
    }

    fun cycleTtsSpeed(): Float {
        return ttsManager.cycleSpeed()
    }

    fun toggleAutoSpeech() {
        _uiState.update { state ->
            val next = !state.aiReviewState.isAutoSpeechEnabled
            if (!next) {
                ttsManager.stop()
            }
            state.copy(
                aiReviewState = state.aiReviewState.copy(isAutoSpeechEnabled = next)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        liveVoice.stopNow()
        ttsManager.shutdown()
    }
}

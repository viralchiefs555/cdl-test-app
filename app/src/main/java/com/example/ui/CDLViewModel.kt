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

import com.example.ai.AiReviewManager
import com.example.ai.ChatMessage
import com.example.ai.MessageSender
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
    val currentMissedIndex: Int? = null
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

        _uiState.update {
            it.copy(
                currentScreen = Screen.AI_REVIEW,
                screenStack = it.screenStack + Screen.AI_REVIEW,
                aiReviewState = AiReviewState(
                    isLoading = true,
                    streamingText = "",
                    messages = emptyList(),
                    error = null,
                    isAutoSpeechEnabled = true,
                    activeReviewResult = targetResult,
                    missedQuestions = missed,
                    currentMissedIndex = null
                )
            )
        }

        aiJob = viewModelScope.launch {
            try {
                val initialPrompt = if (targetResult != null) {
                    aiReviewManager.createInitialReviewPrompt(targetResult)
                } else {
                    aiReviewManager.createGeneralTutorPrompt()
                }
                val fullResponse = StringBuilder()
                var spokenCursor = 0

                aiReviewManager.streamResponse(emptyList(), initialPrompt).collect { chunk ->
                    fullResponse.append(chunk)
                    val currentText = fullResponse.toString()

                    _uiState.update { state ->
                        state.copy(
                            aiReviewState = state.aiReviewState.copy(
                                isLoading = false,
                                streamingText = currentText
                            )
                        )
                    }

                    // Sentence-level streaming TTS: speak as soon as a complete sentence is ready
                    if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                        val unvoiced = currentText.substring(spokenCursor)
                        val sentenceBoundaryRegex = Regex("""[.!?](\s+|\n+|$)""")
                        val match = sentenceBoundaryRegex.find(unvoiced)
                        if (match != null) {
                            val sentenceEnd = match.range.last + 1
                            val sentence = unvoiced.substring(0, sentenceEnd).trim()
                            if (sentence.isNotBlank()) {
                                val queueMode = if (spokenCursor == 0) android.speech.tts.TextToSpeech.QUEUE_FLUSH else android.speech.tts.TextToSpeech.QUEUE_ADD
                                ttsManager.speak(sentence, queueMode)
                                spokenCursor += sentenceEnd
                            }
                        }
                    }
                }

                val finalMessageText = fullResponse.toString().trim()
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled && spokenCursor < finalMessageText.length) {
                    val remaining = finalMessageText.substring(spokenCursor).trim()
                    if (remaining.isNotBlank()) {
                        val queueMode = if (spokenCursor == 0) android.speech.tts.TextToSpeech.QUEUE_FLUSH else android.speech.tts.TextToSpeech.QUEUE_ADD
                        ttsManager.speak(remaining, queueMode)
                    }
                }

                val aiMessage = ChatMessage(
                    sender = MessageSender.AI,
                    text = finalMessageText
                )

                _uiState.update { state ->
                    state.copy(
                        aiReviewState = state.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = listOf(aiMessage),
                            error = null
                        )
                    )
                }
            } catch (e: TimeoutCancellationException) {
                android.util.Log.w("CDLViewModel", "AI Review timed out, using local Coach Mac debrief", e)
                val fallbackText = if (targetResult != null) {
                    aiReviewManager.generateLocalFallbackDebrief(targetResult)
                } else {
                    aiReviewManager.generateLocalGeneralGreeting()
                }
                val aiMessage = ChatMessage(sender = MessageSender.AI, text = fallbackText)
                _uiState.update { state ->
                    state.copy(
                        aiReviewState = state.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = listOf(aiMessage),
                            error = null
                        )
                    )
                }
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                    ttsManager.speak(fallbackText, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                android.util.Log.e("CDLViewModel", "AI Review error, using local Coach Mac debrief", e)
                val fallbackText = if (targetResult != null) {
                    aiReviewManager.generateLocalFallbackDebrief(targetResult)
                } else {
                    aiReviewManager.generateLocalGeneralGreeting()
                }
                val aiMessage = ChatMessage(sender = MessageSender.AI, text = fallbackText)
                _uiState.update { state ->
                    state.copy(
                        aiReviewState = state.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = listOf(aiMessage),
                            error = null
                        )
                    )
                }
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                    ttsManager.speak(fallbackText, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
                }
            }
        }
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
            _uiState.update { s ->
                s.copy(
                    aiReviewState = s.aiReviewState.copy(
                        isLoading = false,
                        streamingText = "",
                        currentMissedIndex = null,
                        messages = s.aiReviewState.messages + userMsg + aiMsg
                    )
                )
            }
            if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                ttsManager.speak(finishText, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
            }
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

        ttsManager.stop()
        aiJob?.cancel()

        val prompt = aiReviewManager.createStepByStepPrompt(item, index, missedList.size)
        val userActionText = if (index == 0 && state.currentMissedIndex == null) {
            "Let's review each one of the questions that I missed step by step."
        } else {
            "Review missed question ${index + 1} of ${missedList.size}: ${item.question.subtopic}"
        }
        val userMsg = ChatMessage(sender = MessageSender.USER, text = userActionText)
        val newMessages = state.messages + userMsg

        _uiState.update { s ->
            s.copy(
                aiReviewState = s.aiReviewState.copy(
                    isLoading = true,
                    streamingText = "",
                    currentMissedIndex = index,
                    messages = newMessages,
                    error = null
                )
            )
        }

        aiJob = viewModelScope.launch {
            try {
                val fullResponse = StringBuilder()
                var spokenCursor = 0

                aiReviewManager.streamResponse(newMessages, prompt).collect { chunk ->
                    fullResponse.append(chunk)
                    val currentText = fullResponse.toString()

                    _uiState.update { s ->
                        s.copy(
                            aiReviewState = s.aiReviewState.copy(
                                isLoading = false,
                                streamingText = currentText
                            )
                        )
                    }

                    if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                        val unvoiced = currentText.substring(spokenCursor)
                        val sentenceBoundaryRegex = Regex("""[.!?](\s+|\n+|$)""")
                        val match = sentenceBoundaryRegex.find(unvoiced)
                        if (match != null) {
                            val sentenceEnd = match.range.last + 1
                            val sentence = unvoiced.substring(0, sentenceEnd).trim()
                            if (sentence.isNotBlank()) {
                                val queueMode = if (spokenCursor == 0) android.speech.tts.TextToSpeech.QUEUE_FLUSH else android.speech.tts.TextToSpeech.QUEUE_ADD
                                ttsManager.speak(sentence, queueMode)
                                spokenCursor += sentenceEnd
                            }
                        }
                    }
                }

                val finalMessageText = fullResponse.toString().trim()
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled && spokenCursor < finalMessageText.length) {
                    val remaining = finalMessageText.substring(spokenCursor).trim()
                    if (remaining.isNotBlank()) {
                        val queueMode = if (spokenCursor == 0) android.speech.tts.TextToSpeech.QUEUE_FLUSH else android.speech.tts.TextToSpeech.QUEUE_ADD
                        ttsManager.speak(remaining, queueMode)
                    }
                }

                val aiMessage = ChatMessage(sender = MessageSender.AI, text = finalMessageText)
                _uiState.update { s ->
                    s.copy(
                        aiReviewState = s.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = newMessages + aiMessage,
                            error = null
                        )
                    )
                }
            } catch (e: TimeoutCancellationException) {
                val fallback = aiReviewManager.generateLocalStepReview(item, index, missedList.size)
                val aiMessage = ChatMessage(sender = MessageSender.AI, text = fallback)
                _uiState.update { s ->
                    s.copy(
                        aiReviewState = s.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = newMessages + aiMessage,
                            error = null
                        )
                    )
                }
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                    ttsManager.speak(fallback, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val fallback = aiReviewManager.generateLocalStepReview(item, index, missedList.size)
                val aiMessage = ChatMessage(sender = MessageSender.AI, text = fallback)
                _uiState.update { s ->
                    s.copy(
                        aiReviewState = s.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = newMessages + aiMessage,
                            error = null
                        )
                    )
                }
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                    ttsManager.speak(fallback, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
                }
            }
        }
    }

    fun sendUserChatMessage(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isBlank()) return

        val state = _uiState.value.aiReviewState
        val lower = trimmed.lowercase()

        // 1. Check if user wants to start or step through missed questions review
        val isRequestingMissedReview = lower.contains("review each") ||
                lower.contains("questions that i missed") ||
                lower.contains("questions i missed") ||
                lower.contains("wrong questions") ||
                lower.contains("missed questions") ||
                lower.contains("step by step") ||
                lower.contains("go over each") ||
                lower.contains("go over missed") ||
                lower.contains("review the questions")

        if (isRequestingMissedReview && state.missedQuestions.isNotEmpty()) {
            val startIndex = state.currentMissedIndex ?: 0
            reviewMissedQuestionAtIndex(startIndex)
            return
        }

        // 2. Check if currently in step-by-step review and user confirms readiness for the next question
        val isReadyForNext = lower.contains("ready") ||
                lower.contains("next") ||
                lower.contains("yes") ||
                lower.contains("yep") ||
                lower.contains("yeah") ||
                lower.contains("sure") ||
                lower.contains("continue") ||
                lower.contains("go ahead") ||
                lower.contains("move on") ||
                lower == "ok" || lower == "okay"

        if (state.currentMissedIndex != null && isReadyForNext) {
            goToNextMissedQuestion()
            return
        }

        ttsManager.stop()
        aiJob?.cancel()

        val currentMissed = state.currentMissedIndex?.let { state.missedQuestions.getOrNull(it) }

        val userMessage = ChatMessage(sender = MessageSender.USER, text = trimmed)
        val currentMessages = _uiState.value.aiReviewState.messages + userMessage

        _uiState.update { s ->
            s.copy(
                aiReviewState = s.aiReviewState.copy(
                    isLoading = true,
                    streamingText = "",
                    messages = currentMessages,
                    error = null
                )
            )
        }

        aiJob = viewModelScope.launch {
            try {
                val fullResponse = StringBuilder()
                var spokenCursor = 0

                val promptToSend = if (currentMissed != null) {
                    """
                    The user is currently reviewing missed question ${(state.currentMissedIndex ?: 0) + 1} of ${state.missedQuestions.size}:
                    - Topic: ${currentMissed.question.subtopic}
                    - Question: "${currentMissed.question.questionText}"
                    - Correct Answer: "${currentMissed.question.options.getOrElse(currentMissed.correctIndex) { "" }}"
                    - Explanation: ${currentMissed.question.explanation}
                    User Question / Comment: "$trimmed"

                    Answer their specific question directly in under 55 words, and end by asking: "Are you ready to go to the next question that you missed?"
                    """.trimIndent()
                } else {
                    trimmed
                }

                aiReviewManager.streamResponse(currentMessages, promptToSend).collect { chunk ->
                    fullResponse.append(chunk)
                    val currentText = fullResponse.toString()

                    _uiState.update { s ->
                        s.copy(
                            aiReviewState = s.aiReviewState.copy(
                                isLoading = false,
                                streamingText = currentText
                            )
                        )
                    }

                    if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                        val unvoiced = currentText.substring(spokenCursor)
                        val sentenceBoundaryRegex = Regex("""[.!?](\s+|\n+|$)""")
                        val match = sentenceBoundaryRegex.find(unvoiced)
                        if (match != null) {
                            val sentenceEnd = match.range.last + 1
                            val sentence = unvoiced.substring(0, sentenceEnd).trim()
                            if (sentence.isNotBlank()) {
                                val queueMode = if (spokenCursor == 0) android.speech.tts.TextToSpeech.QUEUE_FLUSH else android.speech.tts.TextToSpeech.QUEUE_ADD
                                ttsManager.speak(sentence, queueMode)
                                spokenCursor += sentenceEnd
                            }
                        }
                    }
                }

                val finalAiText = fullResponse.toString().trim()
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled && spokenCursor < finalAiText.length) {
                    val remaining = finalAiText.substring(spokenCursor).trim()
                    if (remaining.isNotBlank()) {
                        val queueMode = if (spokenCursor == 0) android.speech.tts.TextToSpeech.QUEUE_FLUSH else android.speech.tts.TextToSpeech.QUEUE_ADD
                        ttsManager.speak(remaining, queueMode)
                    }
                }

                val aiMessage = ChatMessage(sender = MessageSender.AI, text = finalAiText)

                _uiState.update { s ->
                    s.copy(
                        aiReviewState = s.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = currentMessages + aiMessage,
                            error = null
                        )
                    )
                }
            } catch (e: TimeoutCancellationException) {
                val fallbackAnswer = aiReviewManager.generateLocalChatFallback(
                    userPrompt = trimmed,
                    currentMissed = currentMissed,
                    missedIndex = state.currentMissedIndex,
                    totalMissed = state.missedQuestions.size
                )
                val aiMessage = ChatMessage(sender = MessageSender.AI, text = fallbackAnswer)
                _uiState.update { s ->
                    s.copy(
                        aiReviewState = s.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = currentMessages + aiMessage,
                            error = null
                        )
                    )
                }
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                    ttsManager.speak(fallbackAnswer, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                android.util.Log.e("CDLViewModel", "AI chat error, using local Coach Mac fallback", e)
                val fallbackAnswer = aiReviewManager.generateLocalChatFallback(
                    userPrompt = trimmed,
                    currentMissed = currentMissed,
                    missedIndex = state.currentMissedIndex,
                    totalMissed = state.missedQuestions.size
                )
                val aiMessage = ChatMessage(sender = MessageSender.AI, text = fallbackAnswer)
                _uiState.update { s ->
                    s.copy(
                        aiReviewState = s.aiReviewState.copy(
                            isLoading = false,
                            streamingText = "",
                            messages = currentMessages + aiMessage,
                            error = null
                        )
                    )
                }
                if (_uiState.value.aiReviewState.isAutoSpeechEnabled) {
                    ttsManager.speak(fallbackAnswer, android.speech.tts.TextToSpeech.QUEUE_FLUSH)
                }
            }
        }
    }

    fun speakMessage(text: String) {
        ttsManager.speak(text)
    }

    fun stopSpeaking() {
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
        ttsManager.shutdown()
    }
}

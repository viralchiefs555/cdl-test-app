package com.example.ui.screens

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ai.ChatMessage
import com.example.ai.LiveVoiceStatus
import com.example.ai.MessageSender
import com.example.data.models.ExamResult
import com.example.data.models.Question
import com.example.data.repository.CDLQuestionRepository
import com.example.ui.CDLViewModel
import com.example.ui.QuizState
import com.example.ui.theme.FailingRed
import com.example.ui.theme.FailingRedContainer
import com.example.ui.theme.PassingGreen
import com.example.ui.theme.PassingGreenContainer
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SafetyAmber
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiReviewChatScreen(
    viewModel: CDLViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val aiState = uiState.aiReviewState
    val isSpeaking by viewModel.ttsManager.isSpeaking.collectAsState()
    val isMuted by viewModel.ttsManager.isMuted.collectAsState()
    val speechRate by viewModel.ttsManager.speechRate.collectAsState()
    val voiceLabel by viewModel.ttsManager.voiceLabel.collectAsState()

    var inputPrompt by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    // Spoken answers: Android's own speech recognizer screen handles the microphone, so this
    // needs no extra permission. Whatever the student says is sent like a typed message.
    val speechLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spoken = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
            if (!spoken.isNullOrBlank()) {
                viewModel.sendUserChatMessage(spoken)
            }
        }
    }
    val launchVoiceInput: () -> Unit = {
        viewModel.stopSpeaking()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                if (aiState.quiz?.awaitingAnswer == true) "Say your answer" else "Ask Coach Mac"
            )
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Voice input isn't available on this device. You can type instead.", Toast.LENGTH_LONG).show()
        }
    }

    // Live voice needs the microphone permission.
    val micPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.startLiveVoice()
        } else {
            Toast.makeText(context, "Live talk needs microphone access.", Toast.LENGTH_LONG).show()
        }
    }
    val isLiveOn = aiState.liveStatus == LiveVoiceStatus.LIVE || aiState.liveStatus == LiveVoiceStatus.CONNECTING
    val toggleLiveVoice: () -> Unit = {
        if (isLiveOn) {
            viewModel.stopLiveVoice()
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            viewModel.startLiveVoice()
        } else {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // Hands-free quiz: once Coach Mac finishes reading a question aloud, open the mic once.
    val quizQuestionId = aiState.quiz?.current?.id
    val awaitingQuizAnswer = aiState.quiz?.awaitingAnswer == true
    var heardSpeakingFor by remember { mutableStateOf<String?>(null) }
    var autoListenedFor by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(isSpeaking, quizQuestionId, awaitingQuizAnswer) {
        val id = quizQuestionId
        if (id != null && awaitingQuizAnswer) {
            if (isSpeaking) {
                heardSpeakingFor = id
            } else if (heardSpeakingFor == id && autoListenedFor != id) {
                autoListenedFor = id
                delay(400)
                launchVoiceInput()
            }
        }
    }

    BackHandler {
        viewModel.stopSpeaking()
        viewModel.navigateBack()
    }

    // Scroll to bottom as new messages arrive
    LaunchedEffect(aiState.messages.size, aiState.streamingText) {
        val lastIndex = listState.layoutInfo.totalItemsCount - 1
        if (aiState.messages.isNotEmpty() && lastIndex >= 0) {
            listState.animateScrollToItem(lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HeadsetMic,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Coach Mac (AI Voice)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                if (isSpeaking) {
                                    SpeechPulseIndicator()
                                }
                            }
                            Text(
                                text = if (isSpeaking) "Speaking out loud..." else "CDL Exam Voice Reviewer",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSpeaking) PassingGreen else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.stopSpeaking()
                            viewModel.navigateBack()
                        },
                        modifier = Modifier.testTag("ai_review_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Live voice (Gemini Live API) on / off
                    IconButton(
                        onClick = toggleLiveVoice,
                        modifier = Modifier.testTag("live_voice_button")
                    ) {
                        Icon(
                            imageVector = if (isLiveOn) Icons.Default.CallEnd else Icons.Default.RecordVoiceOver,
                            contentDescription = if (isLiveOn) "End Live Talk" else "Start Live Talk",
                            tint = if (isLiveOn) FailingRed else MaterialTheme.colorScheme.primary
                        )
                    }

                    // Speech Speed Selector
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clickable { viewModel.cycleTtsSpeed() }
                            .padding(end = 6.dp)
                            .testTag("speed_toggle_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Speed",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${speechRate}x",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Stop button when speaking
                    if (isSpeaking) {
                        IconButton(
                            onClick = { viewModel.stopSpeaking() },
                            modifier = Modifier.testTag("stop_speech_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stop,
                                contentDescription = "Stop Speech",
                                tint = FailingRed
                            )
                        }
                    }

                    // Mute / Unmute Button
                    IconButton(
                        onClick = { viewModel.toggleMuteTts() },
                        modifier = Modifier.testTag("mute_tts_button")
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute Speech" else "Mute Speech",
                            tint = if (isMuted) SafetyAmber else MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                // Voice picker: tap to hear the next voice
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.cycleVoice() }
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("voice_picker_row"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Voice: $voiceLabel",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Change",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Quick prompt suggestions
                QuickPromptChips(
                    categoryName = aiState.activeReviewResult?.category?.shortName ?: "Exam",
                    hasMissedQuestions = aiState.missedQuestions.isNotEmpty(),
                    isStepReviewing = aiState.currentMissedIndex != null,
                    quiz = aiState.quiz,
                    onSelect = { prompt ->
                        viewModel.sendUserChatMessage(prompt)
                    }
                )

                // Input Bar
                Surface(
                    tonalElevation = 6.dp,
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputPrompt,
                            onValueChange = { inputPrompt = it },
                            placeholder = {
                                Text(
                                    if (aiState.quiz?.awaitingAnswer == true)
                                        "Say or type your answer..."
                                    else if (aiState.currentMissedIndex != null)
                                        "Ask about this question, or say 'next'..."
                                    else if (aiState.activeReviewResult != null)
                                        "Ask Coach Mac anything about this exam..."
                                    else
                                        "Ask Coach Mac any CDL question..."
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("ai_chat_input"),
                            shape = RoundedCornerShape(24.dp),
                            maxLines = 3,
                            singleLine = false
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // Speak instead of typing
                        IconButton(
                            onClick = launchVoiceInput,
                            enabled = !aiState.isLoading,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    if (awaitingQuizAnswer) SafetyAmber.copy(alpha = 0.25f)
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .testTag("ai_voice_input_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Speak Your Message",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(
                            onClick = {
                                if (inputPrompt.isNotBlank()) {
                                    val text = inputPrompt
                                    inputPrompt = ""
                                    viewModel.sendUserChatMessage(text)
                                }
                            },
                            enabled = inputPrompt.isNotBlank() && !aiState.isLoading,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    if (inputPrompt.isNotBlank() && !aiState.isLoading)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.surfaceVariant
                                )
                                .testTag("ai_chat_send_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Message",
                                tint = if (inputPrompt.isNotBlank() && !aiState.isLoading)
                                    Color.White
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Exam Header Card or General Tutoring Card
            item {
                val result = aiState.activeReviewResult
                if (result != null) {
                    ExamSummaryHeaderCard(result = result)
                } else {
                    GeneralTutorHeaderCard()
                }
            }

            // Live voice conversation status
            if (aiState.liveStatus != LiveVoiceStatus.OFF) {
                item {
                    LiveVoiceCard(
                        status = aiState.liveStatus,
                        errorText = aiState.liveError,
                        onEnd = { viewModel.stopLiveVoice() },
                        onDismiss = { viewModel.dismissLiveError() }
                    )
                }
            }

            // Voice quiz progress
            aiState.quiz?.let { quiz ->
                item {
                    QuizStatusCard(
                        quiz = quiz,
                        onStop = { viewModel.endQuiz() }
                    )
                }
            }

            // Step-by-Step Missed Questions Review Section
            if (aiState.missedQuestions.isNotEmpty() && aiState.quiz == null) {
                item {
                    val currentIndex = aiState.currentMissedIndex
                    if (currentIndex != null) {
                        StepReviewActiveCard(
                            currentIndex = currentIndex,
                            total = aiState.missedQuestions.size,
                            currentQuestion = aiState.missedQuestions.getOrNull(currentIndex)?.question,
                            onPrev = { viewModel.goToPreviousMissedQuestion() },
                            onNext = { viewModel.goToNextMissedQuestion() }
                        )
                    } else {
                        StartStepReviewBannerCard(
                            missedCount = aiState.missedQuestions.size,
                            onStart = { viewModel.startStepByStepMissedReview() }
                        )
                    }
                }
            }

            // Completed Chat Messages
            items(aiState.messages) { message ->
                ChatMessageBubble(
                    message = message,
                    isSpeaking = isSpeaking,
                    onSpeak = { viewModel.speakMessage(message.text) },
                    onStop = { viewModel.stopSpeaking() }
                )
            }

            // Study-guide mode notice: Gemini couldn't answer, so Coach Mac used the app's own material
            if (aiState.error != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SafetyAmber.copy(alpha = 0.12f)),
                        border = BorderStroke(1.dp, SafetyAmber.copy(alpha = 0.6f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("study_guide_mode_notice")
                    ) {
                      Column {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = SafetyAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Study-guide mode",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = aiState.error ?: "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedButton(
                                onClick = { viewModel.retryLastCoachRequest() },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("retry_ai_review_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Try AI", fontWeight = FontWeight.Bold)
                            }
                        }
                        aiState.appCheckDebugToken?.let { token ->
                            AppCheckTokenHelp(token = token)
                        }
                      }
                    }
                }
            }

            // Streaming or Loading Indicator
            if (aiState.isLoading || aiState.streamingText.isNotBlank()) {
                item {
                    StreamingAiBubble(
                        streamingText = aiState.streamingText,
                        isLoading = aiState.isLoading
                    )
                }
            }
        }
    }
}

@Composable
fun SpeechPulseIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .size(10.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(PassingGreen)
    )
}

@Composable
fun ExamSummaryHeaderCard(result: ExamResult) {
    val wrongQuestions = result.questionIds.mapNotNull { id ->
        val q = CDLQuestionRepository.getQuestionById(id)
        val ans = result.userAnswers[id]
        if (q != null && ans != null && ans != q.correctIndex) q else null
    }.distinctBy { it.questionText }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${result.category.title} Diagnostic",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (result.isPassed) PassingGreen.copy(alpha = 0.15f) else FailingRed.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = if (result.isPassed) "PASSED (${result.scorePercent}%)" else "FAILED (${result.scorePercent}%)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (result.isPassed) PassingGreen else FailingRed,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (wrongQuestions.isNotEmpty())
                    "Coach Mac is reviewing your ${wrongQuestions.size} missed questions aloud:"
                else
                    "Congratulations on a 100% score! Coach Mac is reviewing key commercial driving takeaways:",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (wrongQuestions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(wrongQuestions) { q ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, FailingRed.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "⚠️ ${q.subtopic}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(
    message: ChatMessage,
    isSpeaking: Boolean,
    onSpeak: () -> Unit,
    onStop: () -> Unit
) {
    val isAi = message.sender == MessageSender.AI

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isAi) Alignment.Start else Alignment.End
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.95f),
            horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            if (isAi) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.HeadsetMic,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isAi) 4.dp else 16.dp,
                    bottomEnd = if (isAi) 16.dp else 4.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isAi)
                        MaterialTheme.colorScheme.surface
                    else
                        MaterialTheme.colorScheme.primary
                ),
                border = if (isAi) BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)) else null,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = if (isAi) MaterialTheme.colorScheme.onSurface else Color.White
                    )

                    if (isAi) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                modifier = Modifier
                                    .clickable { onSpeak() }
                                    .testTag("listen_message_button")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Speak Out Loud",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Read Aloud",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StreamingAiBubble(
    streamingText: String,
    isLoading: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 4.dp, bottomEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (streamingText.isNotBlank()) {
                    Text(
                        text = streamingText,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (streamingText.isBlank()) "Coach Mac is preparing your voice review..." else "Speaking & streaming response...",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun QuickPromptChips(
    categoryName: String,
    hasMissedQuestions: Boolean = false,
    isStepReviewing: Boolean = false,
    quiz: QuizState? = null,
    onSelect: (String) -> Unit
) {
    val prompts = when {
        quiz != null && quiz.awaitingAnswer -> {
            val letters = listOf("A", "B", "C", "D", "E", "F")
            letters.take(quiz.current?.options?.size ?: 4) + listOf("Skip", "Stop quiz")
        }
        quiz != null -> listOf(
            "Next question",
            "Explain that one more",
            "Give me a memory trick for that",
            "Stop quiz"
        )
        isStepReviewing -> listOf(
            "Yes, I'm ready for the next question!",
            "Give me a memory trick for this question",
            "Why was my answer dangerous on the road?",
            "Explain the FMCSA rule again"
        )
        hasMissedQuestions -> listOf(
            "Let's review each one of the questions that I missed",
            "Quiz me on my weak areas",
            "Give me a memory trick to remember the correct answers!",
            "Why was my wrong answer unsafe on the road?",
            "Explain the exact FMCSR rule for this topic."
        )
        categoryName == "General" || categoryName == "Exam" -> listOf(
            "Quiz me on my weak areas",
            "Quiz me on pre-trip inspection",
            "Make me a study plan",
            "How do I test air brake leakage?",
            "What are the 9 hazardous materials classes?",
            "How do I stop a trailer jackknife?",
            "What are the emergency brake air limits?",
            "Give me a memory trick for CDL exam questions!",
            "Quiz me on a high-yield CDL question!"
        )
        else -> listOf(
            "Give me a memory trick to remember the correct answers!",
            "Why was my wrong answer unsafe on the road?",
            "Explain the exact FMCSR rule for this topic.",
            "Quiz me with a follow-up question!"
        )
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(prompts) { prompt ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSelect(prompt) }
            ) {
                Text(
                    text = prompt,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun GeneralTutorHeaderCard() {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HeadsetMic,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Coach Mac AI Voice Tutor",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "OPEN STUDY SESSION",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Ask any questions about Air Brakes, Hazardous Materials, Combination Vehicles, or General Knowledge. Coach Mac will speak and explain FMCSA safety rules in real time.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(listOf("Air Brakes", "Hazardous Materials", "Combination Vehicles", "General Knowledge")) { topic ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = topic,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StartStepReviewBannerCard(
    missedCount: Int,
    onStart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SafetyAmber.copy(alpha = 0.12f)),
        border = BorderStroke(1.5.dp, SafetyAmber.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = SafetyAmber,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$missedCount Questions Missed on This Exam",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Coach Mac will walk you through each question you missed one by one with spoken safety explanations, asking if you are ready before proceeding to the next.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onStart,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("start_step_by_step_button")
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Review Missed Questions 1-by-1",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StepReviewActiveCard(
    currentIndex: Int,
    total: Int,
    currentQuestion: Question?,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${currentIndex + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Missed Question ${currentIndex + 1} of $total",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = currentQuestion?.subtopic ?: "Safety Rule",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar through missed questions
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / total.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentIndex > 0) {
                    OutlinedButton(
                        onClick = onPrev,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("prev_missed_question_button")
                    ) {
                        Text("⬅ Prev Missed")
                    }
                } else {
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("next_missed_question_button")
                ) {
                    Text(
                        text = if (currentIndex + 1 < total) "Next Missed Question ➔" else "Finish Missed Review ✓",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LiveVoiceCard(
    status: LiveVoiceStatus,
    errorText: String?,
    onEnd: () -> Unit,
    onDismiss: () -> Unit
) {
    val isError = status == LiveVoiceStatus.ERROR
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isError) FailingRedContainer.copy(alpha = 0.5f) else PassingGreenContainer.copy(alpha = 0.5f)
        ),
        border = BorderStroke(1.5.dp, if (isError) FailingRed else PassingGreen),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("live_voice_card")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (status) {
                LiveVoiceStatus.CONNECTING -> CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = PassingGreen
                )
                LiveVoiceStatus.LIVE -> SpeechPulseIndicator()
                else -> Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = FailingRed
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when (status) {
                        LiveVoiceStatus.CONNECTING -> "Connecting live talk..."
                        LiveVoiceStatus.LIVE -> "Live talk is on"
                        else -> "Live talk unavailable"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = when (status) {
                        LiveVoiceStatus.LIVE -> "Just talk to Coach Mac out loud, hands-free. Wait for him to finish before you answer."
                        LiveVoiceStatus.CONNECTING -> "Getting Coach Mac's live voice ready."
                        else -> errorText ?: "Live voice isn't available right now."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (isError) {
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("dismiss_live_error_button")) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Dismiss")
                }
            } else {
                Button(
                    onClick = onEnd,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FailingRed),
                    modifier = Modifier.testTag("end_live_voice_button")
                ) {
                    Text("End", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun QuizStatusCard(
    quiz: QuizState,
    onStop: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SafetyAmber.copy(alpha = 0.12f)),
        border = BorderStroke(1.5.dp, SafetyAmber.copy(alpha = 0.6f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("quiz_status_card")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Quiz,
                contentDescription = null,
                tint = SafetyAmber,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Voice quiz: ${quiz.focus.label}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Question ${quiz.number} · Score ${quiz.correctCount} of ${quiz.answeredCount}" +
                        if (quiz.awaitingAnswer) " · Your turn: tap the mic or a letter" else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            OutlinedButton(
                onClick = onStop,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("stop_quiz_button")
            ) {
                Text("Stop")
            }
        }
    }
}

/**
 * Test builds only: shows the App Check debug token so it can be added in the Firebase console,
 * which lets this install use the AI.
 */
@Composable
fun AppCheckTokenHelp(token: String) {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 10.dp)
            .testTag("app_check_token_help")
    ) {
        Text(
            text = "To turn the AI on for this test install, add this debug token in the Firebase console: " +
                "App Check > Apps > your Android app > Manage debug tokens > Add debug token.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            SelectionContainer(modifier = Modifier.weight(1f)) {
                Text(
                    text = token,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            TextButton(
                onClick = {
                    clipboard.setText(AnnotatedString(token))
                    Toast.makeText(context, "Debug token copied", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.testTag("copy_app_check_token_button")
            ) {
                Text("Copy", fontWeight = FontWeight.Bold)
            }
        }
    }
}

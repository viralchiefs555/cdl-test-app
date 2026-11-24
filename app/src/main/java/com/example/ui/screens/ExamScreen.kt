package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OutlinedFlag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import com.example.data.models.ExamMode
import com.example.ui.CDLViewModel
import com.example.ui.components.CDLProgressBar
import com.example.ui.components.CDLTopAppBar
import com.example.ui.components.OptionItem
import com.example.ui.theme.FailingRed
import com.example.ui.theme.FailingRedContainer
import com.example.ui.theme.PassingGreen
import com.example.ui.theme.PassingGreenContainer
import com.example.ui.theme.SafetyAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: CDLViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val session = uiState.examSession
    val currentQuestion = session.currentQuestion

    var showExitDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // Handle Android system back gesture safely
    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit Exam?") },
            text = {
                Text("Your progress in this ${session.mode.title} will not be saved if you leave now.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        viewModel.navigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Exit Exam")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Continue")
                }
            }
        )
    }

    // Submit confirmation dialog
    if (session.showSubmitDialog) {
        val unanswered = session.questions.size - session.answeredCount
        AlertDialog(
            onDismissRequest = { viewModel.setShowSubmitDialog(false) },
            title = { Text("Submit Exam for Grading?") },
            text = {
                Column {
                    Text("You have answered ${session.answeredCount} of ${session.questions.size} questions.")
                    if (unanswered > 0) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "⚠️ Warning: $unanswered unanswered questions will be marked incorrect.",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    if (session.flaggedQuestionIds.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "📌 You have ${session.flaggedQuestionIds.size} questions flagged for review.",
                            color = SafetyAmber
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Official DMV Passing Standard: ${session.category.passingScorePercent}% required.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.submitExam() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("confirm_submit_exam_button")
                ) {
                    Text("Submit Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setShowSubmitDialog(false) }) {
                    Text("Keep Testing")
                }
            }
        )
    }

    // Question Navigator Bottom Sheet
    if (session.showQuestionDrawer) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleQuestionDrawer(false) },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question Overview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${session.answeredCount} / ${session.questions.size} answered",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LegendItem(color = MaterialTheme.colorScheme.primary, label = "Answered")
                    LegendItem(color = MaterialTheme.colorScheme.surfaceVariant, label = "Unanswered")
                    LegendItem(color = SafetyAmber, label = "Flagged")
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    itemsIndexed(session.questions) { index, q ->
                        val isAnswered = session.userAnswers.containsKey(q.id)
                        val isFlagged = session.flaggedQuestionIds.contains(q.id)
                        val isCurrent = index == session.currentIndex

                        val boxBg = when {
                            isCurrent -> MaterialTheme.colorScheme.primaryContainer
                            isFlagged -> SafetyAmber.copy(alpha = 0.25f)
                            isAnswered -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                        val border = when {
                            isCurrent -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            isFlagged -> BorderStroke(2.dp, SafetyAmber)
                            else -> null
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = boxBg,
                            border = border,
                            modifier = Modifier
                                .size(46.dp)
                                .clickable { viewModel.jumpToQuestion(index) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${index + 1}",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = if (isCurrent || isAnswered) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isAnswered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.toggleQuestionDrawer(false)
                        viewModel.setShowSubmitDialog(true)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finish & Submit Exam")
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    Scaffold(
        topBar = {
            val isCurrentFlagged = currentQuestion?.let { session.flaggedQuestionIds.contains(it.id) } ?: false
            val isCurrentBookmarked = currentQuestion?.let { viewModel.isBookmarked(it.id) } ?: false

            CDLTopAppBar(
                title = session.category.title,
                onBack = { showExitDialog = true },
                timeRemainingSeconds = if (session.isTimerActive) session.timeRemainingSeconds else null,
                isFlagged = isCurrentFlagged,
                onToggleFlag = { viewModel.toggleFlagCurrentQuestion() },
                isBookmarked = isCurrentBookmarked,
                onToggleBookmark = { currentQuestion?.let { viewModel.toggleBookmark(it.id) } },
                onOpenDrawer = { viewModel.toggleQuestionDrawer(true) }
            )
        },
        bottomBar = {
            // Exam Action Bar
            Surface(
                tonalElevation = 6.dp,
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous
                    OutlinedButton(
                        onClick = { viewModel.previousQuestion() },
                        enabled = session.currentIndex > 0,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("exam_previous_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Prev")
                    }

                    // Flag button
                    val isFlagged = currentQuestion?.let { session.flaggedQuestionIds.contains(it.id) } ?: false
                    OutlinedButton(
                        onClick = { viewModel.toggleFlagCurrentQuestion() },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isFlagged) SafetyAmber else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag("exam_flag_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isFlagged) Icons.Default.Flag else Icons.Default.OutlinedFlag,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isFlagged) "Flagged" else "Flag")
                    }

                    // Next or Submit
                    if (session.currentIndex == session.questions.size - 1) {
                        Button(
                            onClick = { viewModel.setShowSubmitDialog(true) },
                            colors = ButtonDefaults.buttonColors(containerColor = PassingGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("exam_submit_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Submit")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.nextQuestion() },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("exam_next_button")
                        ) {
                            Text("Next")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (currentQuestion != null) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    CDLProgressBar(
                        current = session.currentIndex + 1,
                        total = session.questions.size
                    )
                }

                // Live Score Tracker Bar
                item {
                    val liveCorrectCount = session.questions.count { q ->
                        val a = session.userAnswers[q.id]
                        a != null && a == q.correctIndex
                    }
                    val liveErrorsCount = session.questions.count { q ->
                        val a = session.userAnswers[q.id]
                        a != null && a != q.correctIndex
                    }

                    val maxAllowedErrors = (session.questions.size * 0.2f).toInt()

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Score: ",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$liveCorrectCount Correct",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = PassingGreen
                                )
                                Text(
                                    text = " • ",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$liveErrorsCount Error${if (liveErrorsCount == 1) "" else "s"}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (liveErrorsCount > maxAllowedErrors) FailingRed else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = "Max $maxAllowedErrors Allowed",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Subtopic & Flag Badge
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = currentQuestion.subtopic,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        if (session.flaggedQuestionIds.contains(currentQuestion.id)) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SafetyAmber.copy(alpha = 0.18f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Flag,
                                        contentDescription = null,
                                        tint = SafetyAmber,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Flagged for Review",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SafetyAmber,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // Question Text
                item {
                    Text(
                        text = currentQuestion.questionText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                // Options
                val selectedOption = session.userAnswers[currentQuestion.id]
                val isRevealed = session.isInstantFeedbackRevealed
                val letters = listOf("A", "B", "C", "D")

                items(currentQuestion.options.size) { index ->
                    val isOptionSelected = selectedOption == index
                    val isOptionCorrect = index == currentQuestion.correctIndex

                    OptionItem(
                        optionLetter = letters.getOrElse(index) { "${index + 1}" },
                        optionText = currentQuestion.options[index],
                        isSelected = isOptionSelected,
                        isCorrect = if (isRevealed) isOptionCorrect else null,
                        isRevealed = isRevealed,
                        onClick = {
                            viewModel.selectOption(index)
                        }
                    )
                }

                // Immediate Right/Wrong feedback and official explanation block
                item {
                    AnimatedVisibility(visible = isRevealed && selectedOption != null) {
                        val isUserCorrect = selectedOption == currentQuestion.correctIndex
                        val chosenLetter = letters.getOrElse(selectedOption ?: 0) { "${(selectedOption ?: 0) + 1}" }
                        val chosenText = currentQuestion.options.getOrNull(selectedOption ?: 0) ?: ""
                        val correctLetter = letters.getOrElse(currentQuestion.correctIndex) { "${currentQuestion.correctIndex + 1}" }
                        val correctText = currentQuestion.options.getOrNull(currentQuestion.correctIndex) ?: ""

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Immediate Right / Wrong Result Banner
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isUserCorrect) PassingGreenContainer.copy(alpha = 0.5f) else FailingRedContainer.copy(alpha = 0.5f)
                                ),
                                border = BorderStroke(
                                    2.dp,
                                    if (isUserCorrect) PassingGreen else FailingRed
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("instant_feedback_banner")
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(if (isUserCorrect) PassingGreen else FailingRed),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isUserCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = if (isUserCorrect) "CORRECT ANSWER!" else "INCORRECT ANSWER",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Black,
                                            color = if (isUserCorrect) PassingGreen else FailingRed
                                        )
                                    }

                                    if (!isUserCorrect) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = "Your Answer: Option $chosenLetter ($chosenText)",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = FailingRed,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = PassingGreenContainer.copy(alpha = 0.85f),
                                            border = BorderStroke(1.dp, PassingGreen)
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Text(
                                                    text = "Correct Answer is Option $correctLetter:",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = PassingGreen
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    text = correctText,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Detailed FMCSA Manual Explanation
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("explanation_card")
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "FMCSA Explanation & Official Rule",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = currentQuestion.explanation,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surface
                                    ) {
                                        Text(
                                            text = "📖 ${currentQuestion.manualCitation}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.OutlinedFlag
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FailingRed
import com.example.ui.theme.FailingRedContainer
import com.example.ui.theme.OnFailingRedContainer
import com.example.ui.theme.OnPassingGreenContainer
import com.example.ui.theme.PassingGreen
import com.example.ui.theme.PassingGreenContainer
import com.example.ui.theme.SafetyAmber
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CDLTopAppBar(
    title: String,
    onBack: (() -> Unit)? = null,
    timeRemainingSeconds: Int? = null,
    isFlagged: Boolean = false,
    onToggleFlag: (() -> Unit)? = null,
    isBookmarked: Boolean = false,
    onToggleBookmark: (() -> Unit)? = null,
    onOpenDrawer: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (timeRemainingSeconds != null) {
                TimerBadge(seconds = timeRemainingSeconds)
            }
            if (onToggleFlag != null) {
                IconButton(
                    onClick = onToggleFlag,
                    modifier = Modifier.testTag("top_bar_flag_button")
                ) {
                    Icon(
                        imageVector = if (isFlagged) Icons.Default.Flag else Icons.Default.OutlinedFlag,
                        contentDescription = if (isFlagged) "Unmark Question" else "Mark for Review",
                        tint = if (isFlagged) SafetyAmber else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (onToggleBookmark != null) {
                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.testTag("top_bar_bookmark_button")
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark Question",
                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (onOpenDrawer != null) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier.testTag("top_bar_grid_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Question List"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun TimerBadge(seconds: Int) {
    val minutes = seconds / 60
    val secs = seconds % 60
    val isUrgent = seconds < 300 // less than 5 mins

    val bgColor by animateColorAsState(
        targetValue = if (isUrgent) FailingRedContainer else MaterialTheme.colorScheme.surfaceVariant,
        label = "timer_bg"
    )
    val textColor = if (isUrgent) OnFailingRedContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .testTag("timer_badge")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = "Time Remaining",
                modifier = Modifier.size(16.dp),
                tint = textColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = String.format(Locale.US, "%02d:%02d", minutes, secs),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun CDLProgressBar(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (total > 0) current.toFloat() / total else 0f
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question $current of $total",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${(progress * 100).toInt()}% Completed",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun OptionItem(
    optionLetter: String,
    optionText: String,
    isSelected: Boolean,
    isCorrect: Boolean? = null, // null for unrevealed exam mode
    isRevealed: Boolean = false,
    onClick: () -> Unit
) {
    val borderColor = when {
        isRevealed && isCorrect == true -> PassingGreen
        isRevealed && isSelected && isCorrect == false -> FailingRed
        isSelected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
    }

    val backgroundColor = when {
        isRevealed && isCorrect == true -> PassingGreenContainer.copy(alpha = 0.4f)
        isRevealed && isSelected && isCorrect == false -> FailingRedContainer.copy(alpha = 0.4f)
        isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(if (isSelected || isRevealed) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("option_$optionLetter")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Letter Circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isRevealed && isCorrect == true -> PassingGreen
                            isRevealed && isSelected && isCorrect == false -> FailingRed
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isRevealed && isCorrect == true) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Correct",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else if (isRevealed && isSelected && isCorrect == false) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Incorrect",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = optionLetter,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = optionText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            if (isRevealed && isCorrect == true) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = PassingGreen.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, PassingGreen.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "Correct Answer",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PassingGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            } else if (isRevealed && isSelected && isCorrect == false) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = FailingRed.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, FailingRed.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "Your Answer",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = FailingRed,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

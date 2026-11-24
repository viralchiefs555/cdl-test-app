package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CDLViewModel
import com.example.ui.Screen
import com.example.ui.screens.CheatSheetScreen
import com.example.ui.screens.ExamResultScreen
import com.example.ui.screens.ExamScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PreTripInspectionScreen
import com.example.ui.screens.ReviewQuestionsScreen
import com.example.ui.theme.CDLPrepTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.ai.configureAppCheck(this, intent)
        enableEdgeToEdge()
        setContent {
            CDLPrepTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CDLApp()
                }
            }
        }
    }
}

@Composable
fun CDLApp(viewModel: CDLViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState.currentScreen) {
            Screen.HOME -> HomeScreen(viewModel = viewModel)
            Screen.EXAM -> ExamScreen(viewModel = viewModel)
            Screen.RESULT -> ExamResultScreen(viewModel = viewModel)
            Screen.REVIEW -> ReviewQuestionsScreen(viewModel = viewModel)
            Screen.HISTORY -> HistoryScreen(viewModel = viewModel)
            Screen.CHEAT_SHEET -> CheatSheetScreen(viewModel = viewModel)
            Screen.AI_REVIEW -> com.example.ui.screens.AiReviewChatScreen(viewModel = viewModel)
            Screen.PRE_TRIP -> PreTripInspectionScreen(viewModel = viewModel)
        }
    }
}

package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CDLViewModel
import com.example.ui.Screen
import com.example.ui.screens.CheatSheetScreen
import com.example.ui.screens.ExamResultScreen
import com.example.ui.screens.ExamScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PreTrip3DScreen
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
    var show3DTruck by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (show3DTruck) {
            PreTrip3DScreen(onBack = { show3DTruck = false })
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
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
                // Entry point to the 3D truck (Explore / Find it / Test) from the Pre-Trip screen.
                if (uiState.currentScreen == Screen.PRE_TRIP) {
                    ExtendedFloatingActionButton(
                        onClick = { show3DTruck = true },
                        icon = { Icon(Icons.Filled.ViewInAr, contentDescription = null) },
                        text = { Text("3D Truck Test") },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

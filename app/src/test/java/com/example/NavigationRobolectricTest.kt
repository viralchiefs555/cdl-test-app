package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.hasScrollAction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class NavigationRobolectricTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenNavigatesToAllScreensAndBack() {
        composeTestRule.waitForIdle()

        // Home screen should be displayed
        composeTestRule.onNodeWithText("CDL Prep Master").assertIsDisplayed()

        // 1. Navigate to History and back
        composeTestRule.onNodeWithTag("history_nav_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Exam Attempt History").assertIsDisplayed()
        composeTestRule.onNodeWithTag("history_back_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("CDL Prep Master").assertIsDisplayed()

        // 2. Navigate to Cheat Sheet and back
        composeTestRule.onNodeWithText("Cheat Sheet").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("CDL Must-Know Numbers").assertIsDisplayed()
        composeTestRule.onNodeWithTag("cheat_sheet_back_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("CDL Prep Master").assertIsDisplayed()

        // 3. Navigate to Pre-Trip 3D and back
        composeTestRule.onNode(hasScrollAction()).performScrollToNode(hasTestTag("pretrip_home_button"))
        composeTestRule.onNodeWithTag("pretrip_home_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Pre-Trip Inspection").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("CDL Prep Master").assertIsDisplayed()

        // 4. Start General Knowledge Exam, answer Q1, move to Q2
        composeTestRule.onNode(hasScrollAction()).performScrollToNode(hasTestTag("start_sim_general_knowledge"))
        composeTestRule.onNodeWithTag("start_sim_general_knowledge").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("confirm_start_test_button").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Question 1 of 20").assertIsDisplayed()
        composeTestRule.onNodeWithTag("option_A").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("exam_next_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Question 2 of 20").assertIsDisplayed()
    }
}

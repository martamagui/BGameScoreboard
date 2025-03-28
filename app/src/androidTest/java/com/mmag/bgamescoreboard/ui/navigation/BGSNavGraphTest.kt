package com.mmag.bgamescoreboard.ui.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mmag.bgamescoreboard.UITest
import com.mmag.bgamescoreboard.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BGSNavGraphTest: UITest() {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testStartDestination_IsGameListScreen() {
        composeTestRule.onNodeWithTag("BGSNavGraph").assertExists()
        composeTestRule.onNodeWithTag("GameListScreen").assertExists()
    }

}
package com.mmag.bgamescoreboard.ui

import dagger.hilt.android.testing.HiltAndroidTest
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.core.view.WindowInsetsControllerCompat
import androidx.test.core.app.ActivityScenario
import com.mmag.bgamescoreboard.UITest
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest : UITest() {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    override fun setUp() {
        super.setUp()
        ActivityScenario.launch(composeTestRule.activity::class.java)
        Thread.sleep(200)
    }
    @Test
    fun testMainActivityLaunchesSuccessfully() {
        composeTestRule.onNodeWithTag("MainActivityRoot").assertExists()
    }

    @Test
    fun testEdgeToEdgeIsEnabled() {
        val activity = composeTestRule.activity
        assertEquals(
            WindowInsetsControllerCompat(activity.window, activity.window.decorView).isAppearanceLightStatusBars,
            true
        )
    }

}
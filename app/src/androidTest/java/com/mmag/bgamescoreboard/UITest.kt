package com.mmag.bgamescoreboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Provides a base class to use for all UI tests.
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
open class UITest {

    @get:Rule(order = 0)
    val hilt = HiltAndroidRule(this)

    @Before
    open fun setUp() {
        hilt.inject()
    }

}
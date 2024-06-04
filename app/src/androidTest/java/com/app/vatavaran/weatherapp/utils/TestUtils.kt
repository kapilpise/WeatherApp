package com.app.vatavaran.weatherapp.utils

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule

object TestUtils {

    // Wait for 10 seconds
    private val WAIT_UNTIL_TIMEOUT = 1_0000L
    fun ComposeContentTestRule.waitUntilNodeCount(
        matcher: SemanticsMatcher,
        count: Int,
        timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
    ) {
        waitUntil(timeoutMillis) {
            onAllNodes(matcher).fetchSemanticsNodes().size == count
        }
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeContentTestRule.waitUntilExists(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
    ) = waitUntilNodeCount(matcher, 1, timeoutMillis)

    @OptIn(ExperimentalTestApi::class)
    fun ComposeContentTestRule.waitUntilDoesNotExist(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
    ) = waitUntilNodeCount(matcher, 0, timeoutMillis)
}
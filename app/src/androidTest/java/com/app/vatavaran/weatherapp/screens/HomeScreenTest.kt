package com.app.vatavaran.weatherapp.screens

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.app.vatavaran.weatherapp.MainActivity
import com.app.vatavaran.weatherapp.di.AppModule
import com.app.vatavaran.weatherapp.navigation.WeatherAppNav
import com.app.vatavaran.weatherapp.utils.TestTags
import com.app.vatavaran.weatherapp.utils.TestUtils.waitUntilExists
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class HomeScreenTest {
    private val TAG = "HomeScreenTest"

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            WeatherAppNav(navController)
        }
    }

    @Test
    fun verify_HomeScreenIsVisible() = runTest {
        // Given : App is started

        // When : App is at home screen navigated from splash screen

        // Then : Home screen is visible
        composeRule
            .onNodeWithContentDescription(TestTags.HOME_BTN_ARROW)
            .assertIsDisplayed()
    }

    @Test
    fun verify_HomeScreenViewsAreVisible() = runTest {

        //Given : App is at home screen
        Log.i(TAG, "Verifying Home screen is displayed")
        composeRule
            .onNodeWithContentDescription("Arrow Dpdown")
            .assertIsDisplayed()
            .performClick()
        // When : App fetches data from api to home screen

        // Then : Home screen should show the current temperature, hourly temperature & daily temperature view
        Log.i(TAG, "Waiting and verifying Home screen views are displayed")
        composeRule.apply {
            Log.i(TAG, "Waiting for view to be visible")
            waitUntilExists(hasTestTag(TestTags.CURRENT_WEATHER_VIEW))
            Log.i(TAG, "Verifying current temperature view displayed")
            onNodeWithTag(TestTags.CURRENT_WEATHER_VIEW)
                .assertIsDisplayed()
            Log.i(TAG, "Verifying hourly card view displayed")
            onNodeWithTag(TestTags.HOUR_CARD)
                .assertIsDisplayed()
            Log.i(TAG, "Verifying daily card view displayed")
            onNodeWithTag(TestTags.DAILY_TEMP_CARD)
                .assertIsDisplayed()
        }
    }


    @After
    fun tearDown() {
    }
}
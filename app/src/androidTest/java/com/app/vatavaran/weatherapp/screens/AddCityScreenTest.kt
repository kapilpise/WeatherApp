package com.app.vatavaran.weatherapp.screens

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AddCityScreenTest {
    private val TAG = "AddCityScreenTest"

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
    fun verify_AddCityScreenVisible() {
        composeRule.onNodeWithContentDescription(TestTags.HOME_BTN_ARROW).assertIsDisplayed()
        composeRule.apply {
            Log.i(TAG, "Waiting for view to be visible")
            waitUntilExists(hasTestTag(TestTags.CURRENT_WEATHER_VIEW))
        }
        // When: Clicked on FAB on Home screen
        composeRule.apply {
            onNodeWithTag(TestTags.HOME_FAB).assertIsDisplayed()
            onNodeWithTag(TestTags.HOME_FAB).performClick()
        }

        // Then: Application should navigate to Add City screen
        composeRule.apply {
            Log.i(TAG, "Waiting for view to be visible")
            waitUntilExists(hasTestTag(TestTags.ADD_CITY_TOPBAR))
            // And: Topbar with label city visible on Add city screen
            onNodeWithTag(TestTags.ADD_CITY_TOPBAR).assertIsDisplayed()
        }
    }

    @Test
    fun verify_AddCityDialogOpen() {
        // Given: Application should on Add city screen
        verify_AddCityScreenVisible()
        // When: Clicked on FAB
        composeRule.apply {
            onNodeWithTag(TestTags.ADD_CITY_FAB).assertIsDisplayed().performClick()
        }
        // Then: Add city dialog should open
        composeRule.onNodeWithTag(TestTags.ADD_CITY_DIALOG).assertIsDisplayed()
    }

    @Test
    fun verify_AddCityDialog_EnterCityValue() {
        // Given: Application should on Add city screen
        // When: Clicked on FAB
        verify_AddCityDialogOpen()

        // And: Add city dialog should open
        // Then: Enter the city name and dismiss the dialog
        composeRule.onNodeWithTag(TestTags.ADD_CITY_TEXT_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ADD_CITY_TEXT_FIELD).performTextInput("Surat")
        composeRule.onNodeWithTag(TestTags.ADD_CITY_DIALOG_BTN).assertIsDisplayed().performClick()
        composeRule.onNodeWithTag(TestTags.ADD_CITY_DIALOG).assertIsNotDisplayed()
    }
}
package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.Map
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventTitleAndDescriptionScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CreateEventUITest {


    @get:Rule
    val rule = createComposeRule()
    val createEventViewModel = CreateEventViewModel()

    @Test
    fun clickFloatingActionButton_ShowsTitleAndDescriptionScreen() {
        rule.setContent {
            CreateEventTitleAndDescriptionScreen(
                navController = rememberNavController(),
                viewModel = createEventViewModel
            )
        }

      //test title and description screen
        rule.onNode(hasContentDescription("Title")).assertExists()
        rule.onNode(hasContentDescription("Description")).assertExists()
        rule.onNode(hasContentDescription("Next")).assertExists()
        rule.onNode(hasContentDescription("Title")).performClick()
        rule.onNode(hasContentDescription("Description")).performClick()
        rule.onNode(hasContentDescription("Next")).performClick()
        rule.onNode(hasContentDescription("Title")).assertExists()
        rule.onNode(hasContentDescription("Description")).assertExists()
        rule.onNode(hasContentDescription("Next")).assertExists()
    }

}
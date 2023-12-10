package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map.MapViewViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapViewViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val repository = FakeEventRepository()


    @Test
    fun getEvents_returnsListOfEvents() {
        val viewModel =
            MapViewViewModel(repository = repository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<Event>(), viewModel.eventList.value)

        composeTestRule.runOnIdle {
            viewModel.getEvents()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.eventList.value.size == 3)
    }


}
package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations.RecommendationsViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecommendationsViewModelTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val repository = FakeEventRepository()


    @Test
    fun getRecommendations_returnsListOfEvents() {
        val viewModel =
            RecommendationsViewModel(repository = repository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<MinimalEvent>(), viewModel.recommendationsList.value)

        composeTestRule.runOnIdle {
            viewModel.getRecommendations(userId = "abc", numberOfEvents = 5)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.recommendationsList.value.size == 2)
    }

    @Test
    fun getKeywords_returnsListOfStrings() {
        val viewModel =
            RecommendationsViewModel(repository = repository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<String>(), viewModel.predefinedKeywords.value)

        composeTestRule.runOnIdle {
            viewModel.getKeywords()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.predefinedKeywords.value.size == 6)
    }

    @Test
    fun getCategories_returnsListOfStrings() {
        val viewModel =
            RecommendationsViewModel(repository = repository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<String>(), viewModel.predefinedCategories.value)

        composeTestRule.runOnIdle {
            viewModel.getCategories()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.predefinedCategories.value.size == 3)
    }
}
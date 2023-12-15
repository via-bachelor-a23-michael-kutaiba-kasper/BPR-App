package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeMetadataRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeRecommendationsRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeSurveyRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations.RecommendationsViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecommendationsViewModelTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val metaRepository = FakeMetadataRepository()
    private val surveyRepository = FakeSurveyRepository()
    private val recommendationsRepository = FakeRecommendationsRepository()


    @Test
    fun getRecommendations_returnsListOfEvents() {
        val viewModel =
            RecommendationsViewModel(
                metaRepo = metaRepository,
                surveyRepo = surveyRepository,
                recommendationsRepo = recommendationsRepository
            )

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
            RecommendationsViewModel(
                metaRepo = metaRepository,
                surveyRepo = surveyRepository,
                recommendationsRepo = recommendationsRepository
            )
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
            RecommendationsViewModel(
                metaRepo = metaRepository,
                surveyRepo = surveyRepository,
                recommendationsRepo = recommendationsRepository
            )
        // Verify the initial state
        TestCase.assertEquals(emptyList<String>(), viewModel.predefinedCategories.value)

        composeTestRule.runOnIdle {
            viewModel.getCategories()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.predefinedCategories.value.size == 3)
    }

    @Test
    fun getInterestSurvey_returns200() {
        val viewModel =
            RecommendationsViewModel(
                metaRepo = metaRepository,
                surveyRepo = surveyRepository,
                recommendationsRepo = recommendationsRepository
            )
        // Verify the updated state
        TestCase.assertEquals(0, viewModel.isInterestSurveyFilled(userId = "abc"))
    }

    @Test
    fun storeInterestSurvey_correctNumberOfKeywordsAndCategories_returns200() {
        val viewModel =
            RecommendationsViewModel(
                metaRepo = metaRepository,
                surveyRepo = surveyRepository,
                recommendationsRepo = recommendationsRepository
            )
        var statusCode = 0
        // Verify the initial state
        TestCase.assertEquals(
            statusCode,
            viewModel.storeInterestSurvey(
                userId = "abc",
                keywords = emptyList(),
                categories = emptyList()
            )
        )

        composeTestRule.runOnIdle {
            statusCode = viewModel.storeInterestSurvey(
                userId = "abc",
                keywords = listOf("abc", "def", "ghi"),
                categories = listOf("abc", "def", "ghi")
            )
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(200, viewModel.isInterestSurveyFilled(userId = "abc"))
    }
}
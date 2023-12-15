package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeProgressRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeReviewRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile.ProfileViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val eventRepository = FakeEventRepository()
    private val reviewRepository = FakeReviewRepository()
    private val progressRepository = FakeProgressRepository()




    @Test
    fun getReviewIds_returnsListOfInts() {
        val viewModel =
            ProfileViewModel(eventRepository = eventRepository, reviewRepository = reviewRepository, progressRepository = progressRepository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<Int>(), viewModel.reviewIds.value)

        composeTestRule.runOnIdle {
            viewModel.getReviewIds(hostId = "abc")
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.reviewIds.value.size == 5)
    }

    @Test
    fun getFinishedJoinedEvents_returnsListOfEvents() {
        val viewModel =
            ProfileViewModel(eventRepository = eventRepository, reviewRepository = reviewRepository, progressRepository = progressRepository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<Int>(), viewModel.finishedJoinedEvents.value)

        composeTestRule.runOnIdle {
            viewModel.getFinishedJoinedEvents(userId = "abc")
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.finishedJoinedEvents.value.size == 2)
    }

    @Test
    fun createReview_returnsInt() {
        val viewModel =
            ProfileViewModel(eventRepository = eventRepository, reviewRepository = reviewRepository, progressRepository = progressRepository)

        // Verify the initial state
        TestCase.assertEquals(false, viewModel.reviewCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createReview(
                eventId = 1,
                userId = "abc",
                rating = 4.0f,
                reviewDate = "2021-05-05"
            )
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.reviewCreated.value)
    }

    @Test
    fun getExperienceHistory_returnsListOfExperience() {
        val viewModel =
            ProfileViewModel(eventRepository = eventRepository, reviewRepository = reviewRepository, progressRepository = progressRepository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<ExperienceHistory>(), viewModel.experienceHistory.value)

        composeTestRule.runOnIdle {
            viewModel.getExperienceHistory("abc")
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.experienceHistory.value.size == 3)
    }

}
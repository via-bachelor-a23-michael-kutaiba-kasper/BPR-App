package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeProgressRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements.AchievementsViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AchievementsViewModelTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val repository = FakeProgressRepository()


    @Test
    fun getUserAchievements_returnsListOfAchievements() {
        val viewModel = AchievementsViewModel(repository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<Achievement>(), viewModel.userAchievements.value)

        composeTestRule.runOnIdle {
            viewModel.getUserAchievements()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.userAchievements.value.size == 4)
    }

    @Test
    fun getExperience_returnsExperience() {
        val viewModel = AchievementsViewModel(repository)

        // Verify the initial state
        TestCase.assertEquals(0, viewModel.experience.value.totalExp)

        composeTestRule.runOnIdle {
            viewModel.getUserExperience()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(100, viewModel.experience.value)
    }

    @Test
    fun getAchievements_returnsListOfAchievements() {
        val viewModel = AchievementsViewModel(repository)

        // Verify the initial state
        TestCase.assertEquals(emptyList<Achievement>(), viewModel.achievements.value)

        composeTestRule.runOnIdle {
            viewModel.getAchievements()
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(true, viewModel.achievements.value.size == 4)
    }


}


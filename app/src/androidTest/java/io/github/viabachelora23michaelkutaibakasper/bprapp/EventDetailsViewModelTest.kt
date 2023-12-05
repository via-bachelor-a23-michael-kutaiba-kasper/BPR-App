package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails.EventDetailsViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class EventDetailsViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val repository = FakeEventRepository()


    @Test
    fun getEvent_returnsEvent() {
        val viewModel =
            EventDetailsViewModel(repository = repository)


        val event = Event(
            title = "Run Event",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming", "Fitness"),
            selectedStartDateTime = LocalDateTime.of(2021, 5, 1, 12, 0).plusHours(3),
            selectedEndDateTime = LocalDateTime.of(2021, 5, 2, 12, 0).plusHours(5),
            photos = null,
            url = null,
            isPrivate = false,
            adultsOnly = false,
            isPaid = false,
            maxNumberOfAttendees = 10,
            host = User(
                displayName = "Michael",
                userId = "123456",
                photoUrl = null,
                creationDate = LocalDateTime.of(2021, 5, 1, 12, 0),
                lastSeenOnline = LocalDateTime.of(2021, 5, 1, 12, 0)
            ), lastUpdatedDate = LocalDateTime.of(2021, 5, 1, 12, 0).plusHours(3),
            eventId = 4,
            attendees = emptyList()
        )
        composeTestRule.runOnIdle {
            viewModel.getEvent(4)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        TestCase.assertEquals(event, viewModel.event.value)
    }

    @Test
    fun joinEvent_returnsNothing() {
        val viewModel =
            EventDetailsViewModel(repository = repository)
        composeTestRule.runOnIdle {
            viewModel.joinEvent(2, "123456")
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        //test it does not return anything because it is a suspend function
        //


        TestCase.assertEquals(viewModel.joinEvent(2, "123456"  ),Unit)
    }

}
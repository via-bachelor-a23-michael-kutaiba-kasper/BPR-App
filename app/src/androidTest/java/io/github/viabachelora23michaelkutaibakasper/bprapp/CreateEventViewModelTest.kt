package io.github.viabachelora23michaelkutaibakasper.bprapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.mocks.FakeEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime


@RunWith(AndroidJUnit4::class)
class CreateEventViewModelTest {


    @get:Rule
    val composeTestRule = createComposeRule()
    private val repository = FakeEventRepository()

    @Test
    fun createEvent_ExpectedValues_returnsCreated() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
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
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(true, viewModel.eventCreated.value)
    }

    @Test
    fun createEvent_EmptyTitle_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming", "Fitness"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }

    @Test
    fun createEvent_EmptyAddress_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming", "Fitness"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }

    @Test
    fun createEvent_DefaultCategory_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Choose Category",
            selectedKeywords = listOf("Dance", "Gaming", "Fitness"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }

    @Test
    fun createEvent_EmptyCategory_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "",
            selectedKeywords = listOf("Dance", "Gaming", "Fitness"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }

    @Test
    fun createEvent_KeywordsIsEmpty_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = emptyList(),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }

    @Test
    fun createEvent_KeywordsSizeLessThanThree_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }


    @Test
    fun createEvent_KeywordsMoreThanFive_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming", "Networking", "Yoga", "Coding", "Fitness"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(5),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }


    @Test
    fun createEvent_EndDateIsBeforeStartDate_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        //create an event object
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming", "Yoga"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(-2),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }


    @Test
    fun createEvent_EndDateIsEqualToStartDate_DoesNotCreate() {
        val viewModel =
            CreateEventViewModel(eventRepo = repository)
        val event = Event(
            title = "Hey",
            description = "Run for 5km",
            location = Location(
                city = "Horsens",
                completeAddress = "Hospitalsgade 86, 8701 Horsens",
                geoLocation = GeoLocation(55.860916, 9.850000)
            ),
            selectedCategory = "Music",
            selectedKeywords = listOf("Dance", "Gaming", "Yoga"),
            selectedStartDateTime = LocalDateTime.now().plusHours(3),
            selectedEndDateTime = LocalDateTime.now().plusHours(3),
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
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now(),
            ), lastUpdatedDate = LocalDateTime.now().plusHours(3),
            eventId = 0,
            attendees = emptyList()

        )
        viewModel.setValidTitle(event.title!!)
        viewModel.setValidAddress(event.location?.completeAddress!!)
        viewModel.setValidCategory(event.selectedCategory!!)
        viewModel.setValidKeywords(event.selectedKeywords!! as List<String>)
        viewModel.setValidStartAndEndDate(
            event.selectedStartDateTime!!,
            event.selectedEndDateTime!!
        )

        // Verify the initial state
        assertEquals(false, viewModel.eventCreated.value)

        composeTestRule.runOnIdle {
            viewModel.createEvent(event)
        }
        composeTestRule.waitForIdle()

        // Verify the updated state
        assertEquals(false, viewModel.eventCreated.value)
    }

}


package io.github.viabachelora23michaelkutaibakasper.bprapp.mocks

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import java.time.LocalDateTime

class FakeEventRepository : IEventRepository {
    override suspend fun getEvents(): List<Event> {
        return listOf(
            Event(
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
                isAdultsOnly = false,
                isPaid = false,
                maxNumberOfAttendees = 10,
                host = User(
                    displayName = "Michael",
                    userId = "123456",
                    photoUrl = null,
                    creationDate = LocalDateTime.now(),
                    lastSeenOnline = LocalDateTime.now(),
                ), lastUpdatedDate = LocalDateTime.now().plusHours(3), eventId = 1

            ), Event(
                title = "Run Event3",
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
                isAdultsOnly = false,
                isPaid = false,
                maxNumberOfAttendees = 10,
                host = User(
                    displayName = "Michael",
                    userId = "123456",
                    photoUrl = null,
                    creationDate = LocalDateTime.now(),
                    lastSeenOnline = LocalDateTime.now(),
                ), lastUpdatedDate = LocalDateTime.now().plusHours(3), eventId = 2

            ), Event(
                title = "Run Event4",
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
                isAdultsOnly = false,
                isPaid = false,
                maxNumberOfAttendees = 10,
                host = User(
                    displayName = "Michael",
                    userId = "123456",
                    photoUrl = null,
                    creationDate = LocalDateTime.now(),
                    lastSeenOnline = LocalDateTime.now(),
                ), lastUpdatedDate = LocalDateTime.now().plusHours(3), eventId = 3

            )
        )
    }

    override suspend fun getEvent(eventId: Int): Event {
        return Event(
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
            isAdultsOnly = false,
            isPaid = false,
            maxNumberOfAttendees = 10,
            host = User(
                displayName = "Michael",
                userId = "123456",
                photoUrl = null,
                creationDate = LocalDateTime.of(2021, 5, 1, 12, 0),
                lastSeenOnline = LocalDateTime.of(2021, 5, 1, 12, 0)
            ), lastUpdatedDate = LocalDateTime.of(2021, 5, 1, 12, 0).plusHours(3),
            eventId = 4
        )
    }

    override suspend fun createEvent(event: Event): Int {
        return 1
    }

    override suspend fun getKeywords(): List<String> {
        return listOf("Dance", "Gaming", "Fitness", "Coding", "Yoga", "Networking")
    }

    override suspend fun getCategories(): List<String> {
        return listOf("Music", "Education", "Technology")
    }
}
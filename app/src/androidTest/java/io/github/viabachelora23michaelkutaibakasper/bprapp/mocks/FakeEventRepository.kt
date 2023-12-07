package io.github.viabachelora23michaelkutaibakasper.bprapp.mocks

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import java.time.LocalDateTime

class FakeEventRepository : IEventRepository {
    override suspend fun getEvents(
        hostId: String?,
        includePrivate: Boolean?,
        from: String?
    ): List<MinimalEvent> {
        return listOf(
            MinimalEvent(
                title = "Run Event",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",

                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),

                photos = null,
                eventId = 1,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

            ), MinimalEvent(
                title = "Run Event3",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",

                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),

                photos = null, eventId = 2,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

            ), MinimalEvent(
                title = "Run Event4",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",
                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),
                photos = null,
                eventId = 3,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

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
    }

    override suspend fun createEvent(event: Event): Int {
        return 1
    }

    override suspend fun joinEvent(eventId: Int, userId: String) {
        return
    }

    override suspend fun getKeywords(): List<String> {
        return listOf("Dance", "Gaming", "Fitness", "Coding", "Yoga", "Networking")
    }

    override suspend fun getCategories(): List<String> {
        return listOf("Music", "Education", "Technology")
    }

    override suspend fun getFinishedJoinedEvents(userId: String): List<MinimalEvent> {
        return listOf(
            MinimalEvent(
                title = "Run Event",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",
                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),
                photos = null,
                eventId = 1,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
            ),
            MinimalEvent(
                title = "Run Event",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",
                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),
                photos = null,
                eventId = 1,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
            )
        )
    }

    override suspend fun createReview(
        eventId: Int,
        userId: String,
        rating: Float,
        reviewDate: String
    ): Int {
        return 1
    }

    override suspend fun getReviewIds(userId: String): List<Int> {
        return listOf(1, 2, 3, 4, 5)
    }

    override suspend fun getReccommendations(
        userId: String,
        numberOfEvents: Int
    ): List<MinimalEvent> {
        return listOf(
            MinimalEvent(
                title = "Run Event",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",
                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),
                photos = null,
                eventId = 1,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
            ),
            MinimalEvent(
                title = "Run Event",
                description = "Run for 5km",
                location = Location(
                    city = "Horsens",
                    completeAddress = "Hospitalsgade 86, 8701 Horsens",
                    geoLocation = GeoLocation(55.860916, 9.850000)
                ),
                selectedCategory = "Music",
                selectedStartDateTime = LocalDateTime.now(),
                selectedEndDateTime = LocalDateTime.now().plusHours(5),
                photos = null,
                eventId = 1,
                host = User(
                    "Michael Kuta Ibaka",
                    "123456789",
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
            )
        )
    }
}
package io.github.viabachelora23michaelkutaibakasper.bprapp.mocks

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.recommendations.IRecommendationsRepository
import java.time.LocalDateTime

class FakeRecommendationsRepository: IRecommendationsRepository {

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
                ), numberOfAttendees = null
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
                ), numberOfAttendees = null
            )
        )
    }
}
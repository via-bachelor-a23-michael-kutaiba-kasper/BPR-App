package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.recommendations

import android.net.Uri
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetRecommendationsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.parseUtcStringToLocalDateTime

class RecommendationsRepository: IRecommendationsRepository {

    override suspend fun getReccommendations(
        userId: String,
        numberOfEvents: Int
    ): List<MinimalEvent> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(
            GetRecommendationsQuery(
                userId = userId,
                limit = Optional.presentIfNotNull(numberOfEvents)
            )
        ).execute()
        Log.d(
            "ApolloEventClient",
            "getReccommendations: ${response.data?.recommendations}"
        )
        return response.data?.recommendations?.result?.result?.map {
            MinimalEvent(
                title = it?.event?.title!!,
                selectedStartDateTime = parseUtcStringToLocalDateTime(it.event.startDate!!),
                eventId = it.event.id!!,
                selectedCategory = it.event.category!!,
                photos = it.event.images,
                description = it.event.description,
                location = Location(
                    city = it.event.city,
                    completeAddress = it.event.location,
                    geoLocation = GeoLocation(
                        lat = it.event.geoLocation?.lat!!.toDouble(),
                        lng = it.event.geoLocation.lng!!.toDouble()
                    )
                ), selectedEndDateTime = parseUtcStringToLocalDateTime(it.event.endDate!!),
                host = User(
                    displayName = it.event.host?.displayName!!,
                    userId = it.event.host.userId!!,
                    photoUrl = it.event.host.photoUrl?.let { Uri.parse(it) },
                    creationDate = parseUtcStringToLocalDateTime(
                        it.event
                            .host.creationDate!!
                    ),
                    lastSeenOnline = it.event.host.lastSeenOnline?.let { it1 ->
                        parseUtcStringToLocalDateTime(
                            it1
                        )
                    }
                ), numberOfAttendees = null
            )
        } ?: emptyList()
    }

}
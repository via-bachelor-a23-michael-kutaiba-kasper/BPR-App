package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.AllPublicEventsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location

class EventRepository : IEventRepository {
    override suspend fun getEvents(): List<Event> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(AllPublicEventsQuery()).execute()
        Log.d("ApolloEventClient", "getPublicEvents: ${response.data?.allPublicEvents}")
        if (response.hasErrors()) Log.d("ApolloEventClient", "getPublicEvents: ${response.errors}")
        return response.data?.allPublicEvents?.map {
            Event(
                title = it?.title,
                description = it?.description,
                url = it?.url,
                location = Location(
                    city = it?.location?.city,
                    completeAddress = it?.title,
                    geoLocation = GeoLocation(
                        lat = it?.location?.geoLocation?.lat!!.toDouble(),
                        lng = it?.location?.geoLocation?.lng!!.toDouble()
                    )
                )

            )
        } ?: emptyList()
    }

    override suspend fun getEvent(eventId: String): Event {
        TODO("Not yet implemented")
    }

    override suspend fun createEvent(event: Event): Event {
        TODO("Not yet implemented")
    }


}
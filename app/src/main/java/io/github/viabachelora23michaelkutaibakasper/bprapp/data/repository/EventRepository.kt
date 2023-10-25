package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.AllPublicEventsQuery

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location

class EventRepository : IEventRepository {

    private val URL = "https://countries.trevorblades.com/"
    override suspend fun getEvents(): List<Event> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(URL)
            .build()
        val response = apolloClient.query(AllPublicEventsQuery()).execute()
        Log.d("ApolloEventClient", "getPublicEvents: ${response.data?.allPublicEvents}")
        return response.data?.allPublicEvents?.map {
            Event(
              title= it?.title,
               description = it?.description,
                url = it?.url,
                location = Location(
                    city = it?.location?.city,
                    streetName = it?.location?.streetNumber,
                    houseNumber = it?.location?.houseNumber,
                    floor = it?.location?.floor)
            )
        } ?: emptyList()
    }

    override suspend fun getEvent(code: String): Event? {
        TODO("Not yet implemented")
    }


}
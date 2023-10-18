package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.CountryByCodeQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.ExampleQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event

class EventRepository : IEventRepository {

    private val URL = "https://countries.trevorblades.com/"
    override suspend fun getEvents(): List<Event> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(URL)
            .build()
        val response = apolloClient.query(ExampleQuery()).execute()
        Log.d("ApolloEventClient", "getCountries: ${response.data?.countries}")
        return response.data?.countries?.map {
            Event(
                it.name,
                it.capital,
                it.code
            )
        } ?: emptyList()
    }

    override suspend fun getEvent(code: String): Event? {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(URL)
            .build()
        val response = apolloClient.query(CountryByCodeQuery(code)).execute()
        Log.d("ApolloCountryClient", "getCountry: $response")
        return response.data?.country?.let {
            Event(
                it.name,
                it.capital,
                it.code
            )
        } ?: throw Exception("Country not found with country code: $code")
    }

    override suspend fun createEvent(event: Event): Event {
        TODO("Not yet implemented")
    }


}
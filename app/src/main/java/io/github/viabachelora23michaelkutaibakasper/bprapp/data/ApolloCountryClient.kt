package io.github.viabachelora23michaelkutaibakasper.bprapp.data

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.CountryByCodeQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.ExampleQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.domain.CountryClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.domain.SimpleCountry

class ApolloCountryClient() : CountryClient {
    override suspend fun getCountries(url:String): List<SimpleCountry> {
       val apolloClient = ApolloClient.Builder()
            .serverUrl(url)
            .build()
        val response = apolloClient.query(ExampleQuery()).execute()
        Log.d("ApolloCountryClient", "getCountries: ${response.data?.countries}")
        return response.data?.countries?.map {
            SimpleCountry(
                it.name,
                it.capital,
                it.code)
        } ?: emptyList()
    }

    override suspend fun getCountry(url: String, code: String): SimpleCountry {
       val apolloClient = ApolloClient.Builder()
            .serverUrl(url)
            .build()
        val response = apolloClient.query(CountryByCodeQuery(code)).execute()
        Log.d("ApolloCountryClient", "getCountry: $response")
        return response.data?.country?.let {
            SimpleCountry(
                it.name,
                it.capital,
                it.code
            )
        } ?: throw Exception("Country not found with country code: $code")
    }

}
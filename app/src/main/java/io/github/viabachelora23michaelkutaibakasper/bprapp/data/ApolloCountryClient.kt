package io.github.viabachelora23michaelkutaibakasper.bprapp.data

import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.ExampleQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.domain.CountryClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.domain.SimpleCountry

class ApolloCountryClient(private val apolloClient: ApolloClient) : CountryClient {
    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient.query(ExampleQuery())
            .execute().data?.countries?.map { it.toSimpleCountry() } ?: emptyList()
    }

    override suspend fun getCountry(code: String): SimpleCountry {
        TODO("Not yet implemented")
    }

}
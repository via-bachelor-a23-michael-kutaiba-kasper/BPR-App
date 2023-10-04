package io.github.viabachelora23michaelkutaibakasper.bprapp.domain

import io.github.viabachelora23michaelkutaibakasper.bprapp.ExampleQuery

interface CountryClient {

    suspend fun getCountries(): List<SimpleCountry>
    suspend fun getCountry(code: String): SimpleCountry?
}
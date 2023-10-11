package io.github.viabachelora23michaelkutaibakasper.bprapp.domain

import io.github.viabachelora23michaelkutaibakasper.bprapp.ExampleQuery

interface CountryClient {

    suspend fun getCountries(url:String): List<SimpleCountry>
    suspend fun getCountry(url:String, code: String): SimpleCountry?
}
package io.github.viabachelora23michaelkutaibakasper.bprapp.data

import io.github.viabachelora23michaelkutaibakasper.bprapp.ExampleQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.domain.SimpleCountry

fun ExampleQuery.Country.toSimpleCountry(): SimpleCountry {
    return SimpleCountry(
        name = name,
        capital = capital
    )

}
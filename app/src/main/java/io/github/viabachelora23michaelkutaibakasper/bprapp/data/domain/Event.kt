package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

data class Event(

    val title: String?,
    val description: String?,
    val url: String?,
    val location: Location?
)

data class Location(
    val city: String?,
    val streetName: String?,
    val houseNumber: String?,
    val floor: String?,
    val geoLocation: GeoLocation?
)

data class GeoLocation(
    val lat: Double?,
    val lng: Double?
)

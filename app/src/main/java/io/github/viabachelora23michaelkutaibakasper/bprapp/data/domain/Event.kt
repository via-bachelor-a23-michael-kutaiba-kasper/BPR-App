package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

data class Event(

    val title: String?,
    val description: String?,
    val url: String?,
    val location: Location?
)

data class Location(
    var city: String?,
    var streetName: String?,
    var houseNumber: String?,
    var country: String?,
    var floor: String?,
    var postalCode: String?,
    var geoLocation: GeoLocation?
)

data class GeoLocation(
    var lat: Double?,
    var lng: Double?
)


package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

import android.net.Uri
import java.time.LocalDateTime

data class Event(
    val title: String?,
    val description: String?,
    val url: String?,
    val location: Location?,
    val isPrivate: Boolean?,
    val isPaid: Boolean?,
    val isAdultsOnly: Boolean?,
    val selectedStartDateTime: LocalDateTime?,
    val selectedEndDateTime: LocalDateTime?,
    val selectedKeywords: List<String>?,
    val selectedCategory: String?,
    val maxNumberOfAttendees: Int?,
    val host:User?,
    val lastUpdatedDate: LocalDateTime?,
    val photos : List<String>?
)
data class User(
    var displayName:String,
    var userId:String,
    var photoUrl:Uri?
)
data class Location(
    var city: String?,
    var completeAddress: String?,
    var geoLocation: GeoLocation?
)

data class GeoLocation(
    var lat: Double?,
    var lng: Double?
)


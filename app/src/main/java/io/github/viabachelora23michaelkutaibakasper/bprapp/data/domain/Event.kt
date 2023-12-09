package io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain

import android.net.Uri
import android.provider.CalendarContract.Attendees
import java.time.LocalDateTime

data class Event(
    val title: String?,
    val description: String?,
    val url: String?,
    val location: Location?,
    val isPrivate: Boolean?,
    val isPaid: Boolean?,
    val adultsOnly: Boolean?,
    val selectedStartDateTime: LocalDateTime?,
    val selectedEndDateTime: LocalDateTime?,
    val selectedKeywords: List<String?>?,
    val selectedCategory: String?,
    val maxNumberOfAttendees: Int?,
    val attendees: List<User?>?,
    val host: User?,
    val lastUpdatedDate: LocalDateTime?,
    val photos: List<String?>?,
    val eventId: Int
)

data class MinimalEvent(
    val title: String,
    val selectedStartDateTime: LocalDateTime,
    val selectedEndDateTime: LocalDateTime?,
    val eventId: Int,
    val numberOfAttendees: Int?,
    val description: String?,
    val selectedCategory: String,
    val photos: List<String?>?,
    val location: Location,
    val host: User
)

data class Status(
    val message: String,
    val code: Int
)

data class EventRating(
    val eventId: Int,
    val rating: Float
)


data class User(
    var displayName: String,
    var userId: String,
    var photoUrl: Uri?,
    var creationDate: LocalDateTime?,
    var lastSeenOnline: LocalDateTime?
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


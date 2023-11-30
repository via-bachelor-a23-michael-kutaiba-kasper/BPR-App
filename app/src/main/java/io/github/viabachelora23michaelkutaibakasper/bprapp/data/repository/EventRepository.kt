package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository

import android.net.Uri
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.FetchAllEventsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetCategoriesQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetEventQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetKeywordsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.GeoLocationInput
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.UserInput
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class EventRepository : IEventRepository {
    fun parseUtcStringToLocalDateTime(utcString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        return LocalDateTime.parse(utcString, formatter)
    }

    override suspend fun getEvents(): List<Event> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(FetchAllEventsQuery()).execute()
        Log.d("ApolloEventClient", "getEvent UwWU: ${response.data?.events}")
        return response.data?.events?.map {
            Event(
                title = it?.title,
                description = it?.description,
                url = it?.url,
                location = io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location(
                    city = it?.city,
                    completeAddress = it?.location,
                    geoLocation = io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation(
                        lat = it?.geoLocation?.lat!!.toDouble(),
                        lng = it.geoLocation.lng!!.toDouble()
                    )
                ),
                isPrivate = it.isPrivate,
                isPaid = it.isPaid,
                isAdultsOnly = it.adultsOnly,
                selectedStartDateTime = parseUtcStringToLocalDateTime(it.startDate!!),
                selectedEndDateTime = parseUtcStringToLocalDateTime(it.endDate!!),
                selectedKeywords = it.keywords,
                selectedCategory = it.category,
                maxNumberOfAttendees = it.maxNumberOfAttendees,
                host = User(
                    displayName = it.host?.displayName!!,
                    userId = it.host.userId!!,
                    photoUrl = it.host.photoUrl?.let { Uri.parse(it) },
                    creationDate = parseUtcStringToLocalDateTime(it.host.creationDate!!),
                    lastSeenOnline = parseUtcStringToLocalDateTime(it.host.lastSeenOnline!!)
                ),
                lastUpdatedDate = parseUtcStringToLocalDateTime(it.lastUpdateDate!!),
                photos = it.images ?: emptyList()
            )
        } ?: emptyList()
    }

    override suspend fun getEvent(eventId: Int): Event {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetEventQuery(eventId)).execute()
        Log.d("ApolloEventClient", "getEvent UwWU: ${response.data?.event}")
        return Event(
            title = response.data?.event?.title,
            description = response.data?.event?.description,
            url = response.data?.event?.url,
            location = io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location(
                city = response.data?.event?.city,
                completeAddress = response.data?.event?.location,
                geoLocation = io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation(
                    lat = response.data?.event?.geoLocation?.lat!!.toDouble(),
                    lng = response.data?.event?.geoLocation?.lng!!.toDouble()
                )
            ),
            isPrivate = response.data?.event?.isPrivate,
            isPaid = response.data?.event?.isPaid,
            isAdultsOnly = response.data?.event?.adultsOnly,
            selectedStartDateTime = parseUtcStringToLocalDateTime(response.data?.event?.startDate!!),
            selectedEndDateTime = parseUtcStringToLocalDateTime(response.data?.event?.endDate!!),
            selectedKeywords = response.data?.event?.keywords,
            selectedCategory = response.data?.event?.category,
            maxNumberOfAttendees = response.data?.event?.maxNumberOfAttendees,
            host = User(
                displayName = response.data?.event?.host?.displayName!!,
                userId = response.data?.event?.host?.userId!!,
                photoUrl = response.data?.event?.host?.photoUrl?.let { Uri.parse(it) },
                creationDate = parseUtcStringToLocalDateTime(response.data?.event?.host?.creationDate!!),
                lastSeenOnline = parseUtcStringToLocalDateTime(response.data?.event?.host?.lastSeenOnline!!)
            ),
            lastUpdatedDate = parseUtcStringToLocalDateTime(response.data?.event?.lastUpdateDate!!),
            photos = response.data?.event?.images ?: emptyList()
        )
    }

    override suspend fun createEvent(event: Event): Int {

        Log.d("ApolloEventClient", "createEvent: $event")
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.mutation(
            CreateEventMutation(
                title = event.title!!,
                startDate = event.selectedStartDateTime?.atZone(ZoneId.systemDefault())
                    ?.withZoneSameInstant(ZoneOffset.UTC).toString(),
                endDate = event.selectedEndDateTime?.atZone(ZoneId.systemDefault())
                    ?.withZoneSameInstant(ZoneOffset.UTC).toString(),
                createdDate = event.lastUpdatedDate!!.toString(),
                isPrivate = event.isPrivate!!,
                adultsOnly = event.isAdultsOnly!!,
                isPaid = event.isPaid!!,
                host = UserInput(
                    displayName = event.host?.displayName!!,
                    userId = event.host.userId,
                    photoUrl = com.apollographql.apollo3.api.Optional.present(event.host.photoUrl.toString()),
                    CreationDate = event.lastUpdatedDate.toString(),
                    dateOfBirth = event.lastUpdatedDate.toString()
                ),
                maxNumberOfAttendees = event.maxNumberOfAttendees!!,
                location = event.location?.completeAddress!!,
                city = event.location.city!!,
                geoLocation = GeoLocationInput(
                    lat = event.location.geoLocation?.lat!!,
                    lng = event.location.geoLocation?.lng!!
                ),
                category = event.selectedCategory!!,
                keywords = event.selectedKeywords!! as List<String>,
                description = event.description!!,
            )
        ).execute()
        Log.d("ApolloEventClient", "createEvent: ${response.data?.createEvent?.event?.id}")

        return response.data?.createEvent?.event?.id!!
    }

    override suspend fun getKeywords(): List<String> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetKeywordsQuery()).execute()
        return response.data?.keywords?.map { it!! } ?: emptyList()
    }

    override suspend fun getCategories(): List<String> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetCategoriesQuery()).execute()
        return response.data?.categories?.map { it!! } ?: emptyList()
    }


}
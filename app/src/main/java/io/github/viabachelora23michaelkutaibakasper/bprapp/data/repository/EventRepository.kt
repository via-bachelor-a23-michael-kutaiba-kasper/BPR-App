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
import io.github.viabachelora23michaelkutaibakasper.bprapp.JoinEventMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.GeoLocationInput
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.UserInput
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.parseUtcStringToLocalDateTime
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class EventRepository : IEventRepository {


    override suspend fun getEvents(): List<MinimalEvent> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()

            val response = apolloClient.query(FetchAllEventsQuery()).execute()
            Log.d("ApolloEventClient", "getEvents UwWU: ${response.data?.events}")
            return response.data?.events?.map {
                MinimalEvent(
                    title = it?.title!!,
                    selectedStartDateTime = parseUtcStringToLocalDateTime(it.startDate!!),
                    eventId = it.id!!,
                    selectedCategory = it.category!!,
                    photos = it.images,
                    description = it.description,
                    location = Location(
                        city = it.city,
                        completeAddress = it.location,
                        geoLocation = GeoLocation(
                            lat = it.geoLocation?.lat!!.toDouble(),
                            lng = it.geoLocation.lng!!.toDouble()
                        )
                    )
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
            photos = response.data?.event?.images ?: emptyList(),
            eventId = response.data?.event?.id!!
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

    override suspend fun joinEvent(eventId: Int, userId: String) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.mutation(JoinEventMutation(eventId, userId = userId)).execute()
        Log.d("ApolloEventClient", "joinEvent: ${response.data?.joinEvent?.id}")
    }

    override suspend fun getKeywords(): List<String> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetKeywordsQuery()).execute()
        Log.d("ApolloEventClient", "getKeywords: ${response.data?.keywords}")
        return response.data?.keywords?.map { it!! } ?: emptyList()
    }

    override suspend fun getCategories(): List<String> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetCategoriesQuery()).execute()
        Log.d("ApolloEventClient", "getCategories: ${response.data?.categories}")
        return response.data?.categories?.map { it!! } ?: emptyList()
    }


}
package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.events

import android.net.Uri
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.FetchAllEventsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.FetchJoinedEventsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetEventQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.JoinEventMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.GeoLocationInput
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.UserInput
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.parseUtcStringToLocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class EventRepository : IEventRepository {


    override suspend fun getEvents(
        hostId: String?,
        includePrivate: Boolean?,
        from: String?
    ): List<MinimalEvent> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        Log.d("ApolloEventClient", "getEvents: $from")
        val response =
            apolloClient.query(
                FetchAllEventsQuery(
                    hostId = Optional.presentIfNotNull(hostId),
                    includePrivateEvents = Optional.presentIfNotNull(includePrivate),
                    from = Optional.presentIfNotNull(from)
                )
            ).execute()
        Log.d("ApolloEventClient", "getEvents UwWU: ${response.data?.events}")
        return response.data?.events?.result?.map {
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
                ), selectedEndDateTime = parseUtcStringToLocalDateTime(it.endDate!!),
                host = User(
                    displayName = it.host?.displayName!!,
                    userId = it.host.userId!!,
                    photoUrl = it.host.photoUrl?.let { Uri.parse(it) },
                    creationDate = parseUtcStringToLocalDateTime(it.host.creationDate!!),
                    lastSeenOnline = it.host.lastSeenOnline?.let { it1 ->
                        parseUtcStringToLocalDateTime(
                            it1
                        )
                    }
                ), numberOfAttendees = it.attendees?.size
            )
        } ?: emptyList()
    }


    override suspend fun getEvent(eventId: Int): Event {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(GetEventQuery(eventId)).execute()
        Log.d("ApolloEventClient", "getEvent UwWU: ${response.data?.event?.result}")
        return Event(
            title = response.data?.event?.result?.title,
            description = response.data?.event?.result?.description,
            url = response.data?.event?.result?.url,
            location = Location(
                city = response.data?.event?.result?.city,
                completeAddress = response.data?.event?.result?.location,
                geoLocation = GeoLocation(
                    lat = response.data?.event?.result?.geoLocation?.lat!!.toDouble(),
                    lng = response.data?.event?.result?.geoLocation?.lng!!.toDouble()
                )
            ),
            isPrivate = response.data?.event?.result?.isPrivate,
            isPaid = response.data?.event?.result?.isPaid,
            adultsOnly = response.data?.event?.result?.adultsOnly,
            selectedStartDateTime = parseUtcStringToLocalDateTime(response.data?.event?.result?.startDate!!),
            selectedEndDateTime = parseUtcStringToLocalDateTime(response.data?.event?.result?.endDate!!),
            selectedKeywords = response.data?.event?.result?.keywords,
            selectedCategory = response.data?.event?.result?.category,
            maxNumberOfAttendees = response.data?.event?.result?.maxNumberOfAttendees,
            host = User(
                displayName = response.data?.event?.result?.host?.displayName!!,
                userId = response.data?.event?.result?.host?.userId!!,
                photoUrl = response.data?.event?.result?.host?.photoUrl?.let { Uri.parse(it) },
                creationDate = parseUtcStringToLocalDateTime(response.data?.event?.result?.host?.creationDate!!),
                lastSeenOnline = response.data?.event?.result?.host?.lastSeenOnline?.let {
                    parseUtcStringToLocalDateTime(
                        it
                    )
                }
            ),
            lastUpdatedDate = parseUtcStringToLocalDateTime(response.data?.event?.result?.lastUpdateDate!!),
            photos = response.data?.event?.result?.images ?: emptyList(),
            eventId = response.data?.event?.result?.id!!,
            attendees = response.data?.event?.result?.attendees?.map {
                User(
                    displayName = it?.displayName!!,
                    userId = it.userId!!,
                    photoUrl = it.photoUrl?.let { Uri.parse(it) },
                    creationDate = parseUtcStringToLocalDateTime(it.creationDate!!),
                    lastSeenOnline = parseUtcStringToLocalDateTime(it.lastSeenOnline!!)
                )
            } ?: emptyList()
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
                adultsOnly = event.adultsOnly!!,
                isPaid = event.isPaid!!,
                host = UserInput(
                    displayName = event.host?.displayName!!,
                    userId = event.host.userId,
                    photoUrl = Optional.present(event.host.photoUrl.toString()),
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
        Log.d("ApolloEventClient", "createEvent: ${response.data?.createEvent?.result?.id}")

        return response.data?.createEvent?.result?.id!!
    }

    override suspend fun joinEvent(eventId: Int, userId: String) {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.mutation(JoinEventMutation(eventId, userId = userId)).execute()
        Log.d("ApolloEventClient", "joinEvent: ${response.data?.joinEvent?.result?.id}")
    }

    override suspend fun getJoinedEvents(userId: String,eventState:String): List<MinimalEvent> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        val response = apolloClient.query(FetchJoinedEventsQuery(userId = userId,eventState=eventState)).execute()
        Log.d(
            "ApolloEventClient",
            "getFinishedJoinedEvents: ${response.data?.joinedEvents}"
        )
        return response.data?.joinedEvents?.result?.map {
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
                ), selectedEndDateTime = parseUtcStringToLocalDateTime(it.endDate!!),
                host = User(
                    displayName = it.host?.displayName!!,
                    userId = it.host.userId!!,
                    photoUrl = it.host.photoUrl?.let { Uri.parse(it) },
                    creationDate = parseUtcStringToLocalDateTime(it.host.creationDate!!),
                    lastSeenOnline = it.host.lastSeenOnline?.let { it1 ->
                        parseUtcStringToLocalDateTime(
                            it1
                        )
                    }
                ),
                numberOfAttendees = null
            )
        } ?: emptyList()
    }
}
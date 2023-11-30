package io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.BuildConfig
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventMutation
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetCategoriesQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.GetKeywordsQuery
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.GeoLocationInput
import io.github.viabachelora23michaelkutaibakasper.bprapp.type.UserInput
import java.time.ZoneId
import java.time.ZoneOffset

class EventRepository : IEventRepository {
    override suspend fun getEvents(): List<Event> {
        /* val apolloClient = ApolloClient.Builder()
             .serverUrl(BuildConfig.API_URL)
             .build()
         val response = apolloClient.query(AllPublicEventsQuery()).execute()
         Log.d("ApolloEventClient", "getPublicEvents: ${response.data?.allPublicEvents}")
         if (response.hasErrors()) Log.d("ApolloEventClient", "getPublicEvents: ${response.errors}")
         return response.data?.allPublicEvents?.map {
             Event(
                 title = it?.title,
                 description = it?.description,
                 url = it?.url,
                 location = Location(
                     city = it?.location?.city,
                     completeAddress = it?.title,
                     geoLocation = GeoLocation(
                         lat = it?.location?.geoLocation?.lat!!.toDouble(),
                         lng = it?.location?.geoLocation?.lng!!.toDouble()
                     )
                 ),
                 type = null,
                 isPrivate = null,
                 isPaid = null,
                 isAdultsOnly = null,
                 selectedStartDateTime = null,
                 selectedEndDateTime = null,
                 selectedKeywords = null,
                 selectedCategory = null,
                 maxNumberOfAttendees = null,
                 host = User(
                     displayName = "Michael Kuta Ibaka",
                     userId = "123456789",
                     photoUrl = null
                 ),
                 lastUpdatedDate = null,
                 emptyList()
             )
         } ?: emptyList()*/
        return emptyList()
    }

    override suspend fun getEvent(eventId: String): Event {
        TODO("Not yet implemented")
    }

    override suspend fun createEvent(event: Event): String {

        Log.d("ApolloEventClient", "createEvent: $event")
        //call the apollo client to create the event
        val apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.API_URL)
            .build()
        //create response variable to hold the response from the server
        val response = apolloClient.mutation(
            CreateEventMutation(
                title = event.title!!,
                startDate = event.selectedStartDateTime?.atZone(ZoneId.systemDefault())?.withZoneSameInstant(ZoneOffset.UTC).toString(),
                endDate = event.selectedEndDateTime?.atZone(ZoneId.systemDefault())?.withZoneSameInstant(ZoneOffset.UTC).toString(),
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
                keywords = event.selectedKeywords!!,
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
package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isInvalidJoinEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EventDetailsViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {

    private val eventRepository: IEventRepository = repository
    val errorFetchingEvent = mutableStateOf(false)
    val invalidJoin = mutableStateOf(false)
    private val _event = MutableStateFlow<Event>(
        Event(
            "title",
            "description",
            "",
            Location("", "hospitalsgade 4", GeoLocation(0.0, 0.0)),
            false,
            false,
            false,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1),
            emptyList<String>(),
            "Music",
            50,
            host = User(
                "Michael Kuta Ibaka",
                "123456789",
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            photos = emptyList(),
            attendees = emptyList(),
            lastUpdatedDate = LocalDateTime.now(),
            eventId = 1
        )
    )
    val event = _event.asStateFlow() //expose the stateflow as a public property

    val isLoading = mutableStateOf(false)


    fun getEvent(eventId: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                errorFetchingEvent.value = false
                val event = eventRepository.getEvent(eventId)
                _event.value = event
                Log.d("EventDetailsViewmodel", "getevent: $event")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("EventDetailsViewmodel", "failed to fetch event: ${e.printStackTrace()}")
                isLoading.value = false
                errorFetchingEvent.value = true
            }
        }
    }

    fun joinEvent(eventId: Int, userId: String) {
        if (isInvalidJoinEvent(event.value.attendees!!.size, event.value.maxNumberOfAttendees!!)) {
            invalidJoin.value = true
            return
        }
        viewModelScope.launch {
            try {
                invalidJoin.value = false
                eventRepository.joinEvent(eventId, userId)
                Log.d("EventDetailsViewmodel", "joined event: $eventId")
            } catch (e: Exception) {
                Log.d("EventDetailsViewmodel", "failed to join event: ${e.message}")
            }
        }
    }
}
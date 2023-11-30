package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EventDetailsViewModel : ViewModel() {

    private val eventRepository: IEventRepository = EventRepository()
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
            User(
                "Michael Kuta Ibaka",
                "123456789",
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            LocalDateTime.now(),
            listOf(
                R.drawable.cat1.toString(),
                R.drawable.cat2.toString(),
                R.drawable.cat3.toString()
            )
        )
    )
    val event = _event.asStateFlow() //expose the stateflow as a public property

    val isLoading = mutableStateOf(true)


    fun fetchEventData(eventId: Int) {

        viewModelScope.launch {
            try {
                val event = eventRepository.getEvent(eventId)
                _event.value = event
                Log.d("EventDetailsViewmodel", "getevent: $event")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("EventDetailsViewmodel", "failed to fetch event: ${e.message}")
            }
        }
    }
}
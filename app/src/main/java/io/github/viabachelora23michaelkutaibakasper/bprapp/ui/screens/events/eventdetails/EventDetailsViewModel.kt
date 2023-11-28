package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel : ViewModel() {

    private val eventRepository: IEventRepository = EventRepository()
    private val _event = MutableStateFlow<Event>(
        Event(
            "",
            "",
            "",
            Location("", "",  GeoLocation(0.0, 0.0))
        )
    )
    val event = _event.asStateFlow() //expose the stateflow as a public property

    val isLoading = mutableStateOf(false)


    fun fetchEventData(eventId:String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val event = eventRepository.getEvent(eventId)
                _event.value = event
                Log.d("EventDetailsViewmodel", "getevents: $event")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("EventDetailsViewmodel", "fetchEventData: ${e.message}")
            }
        }
    }
}
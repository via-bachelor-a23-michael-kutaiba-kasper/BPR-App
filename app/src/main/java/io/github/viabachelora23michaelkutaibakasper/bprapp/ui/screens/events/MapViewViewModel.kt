package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewViewModel : ViewModel() {

    private val eventRepository: IEventRepository = EventRepository()
    private val _eventList = MutableStateFlow<List<Event>>(emptyList())
    val eventList = _eventList.asStateFlow() //expose the stateflow as a public property

    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow() //expose the stateflow as a public property

    init {
        fetchEventData()
    }

    private fun fetchEventData() {
        viewModelScope.launch {
            val events = eventRepository.getEvents()
            _eventList.value = events
            Log.d("mapviewmodel", "getevents: $events")
        }

    }
    //make a function that creates a new event in the repository
    fun createEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.createEvent(event)
        }
    }

    fun loadEvent(code: String) {

        viewModelScope.launch {
            _event.value = eventRepository.getEvent(code)
        }

    }

}
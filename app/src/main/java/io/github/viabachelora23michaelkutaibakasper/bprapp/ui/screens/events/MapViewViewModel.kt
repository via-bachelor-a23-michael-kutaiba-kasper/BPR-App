package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewViewModel : ViewModel() {
    //make a viewmodel that holds the list of events. the viewmodel should use kotlin stateflow to hold the list of events
    //the viewmodel should have a function that loads the list of events from the repository

    private val eventRepository: IEventRepository = EventRepository()
    private val _eventList = MutableStateFlow<List<Event>>(emptyList())
    val eventList = _eventList.asStateFlow() //expose the stateflow as a public property

    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow() //expose the stateflow as a public property


     fun fetchEventData() {
        viewModelScope.launch {
            val events = eventRepository.getEvents()
            _eventList.value = events
        }
    }

    fun loadEvent(code: String) {

        viewModelScope.launch {
            _event.value = eventRepository.getEvent(code)
        }

    }

}
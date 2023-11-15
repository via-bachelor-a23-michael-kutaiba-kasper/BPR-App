package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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

    val isLoading = mutableStateOf(false)

    init {
       // fetchEventData()
    }
//q: how do i await the response from the repository? i need to update isLoading to false after the response is received
    //a: use a try catch finally block
    //a: but what if the repository call is also async?
//a: use a coroutine
    //a: but what if the repository call is also async?
    //show me the code

    fun fetchEventData() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val events = eventRepository.getEvents()
                _eventList.value = events
                Log.d("mapviewmodel", "getevents: $events")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("MapViewViewModel", "fetchEventData: ${e.message}")
            }
        }
    }
}
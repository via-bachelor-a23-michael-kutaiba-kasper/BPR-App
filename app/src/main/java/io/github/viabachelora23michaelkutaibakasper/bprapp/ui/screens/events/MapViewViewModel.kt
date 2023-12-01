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

class MapViewViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {
    private val eventRepository: IEventRepository = repository
    private val _eventList = MutableStateFlow<List<Event>>(emptyList())
    val eventList = _eventList.asStateFlow()

    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()

    val isLoading = mutableStateOf(false)

    init {
        getEvents()
    }

    fun getEvents() {
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
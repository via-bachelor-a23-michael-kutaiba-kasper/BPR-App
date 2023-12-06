package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MapViewViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {
    private val eventRepository: IEventRepository = repository
    private val _eventList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    val eventList = _eventList.asStateFlow()
    private val _clusterEvents = MutableStateFlow<List<EventClusterItem>>(emptyList())
    val clusterEvents = _clusterEvents.asStateFlow()
    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()
    val isLoading = mutableStateOf(false)
    val clusterClicked = mutableStateOf(false)
    val currentClusterItems = mutableStateOf<List<EventClusterItem>>(emptyList())
    val errorFetchingEvent = mutableStateOf(false)

    init {
        getEvents(
            from = localDateTimeToUTCLocalDateTime(
                LocalDateTime.now()
            ).toString()
        )
    }


    fun getEvents(from: String? = null) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val events = eventRepository.getEvents(includePrivate = false, from = from)
                _eventList.value = events
                val clusterEvents = mutableListOf<EventClusterItem>()
                _eventList.value.forEach { event ->
                    clusterEvents.add(
                        EventClusterItem(
                            lat = event.location.geoLocation?.lat ?: 0.0,
                            lng = event.location.geoLocation?.lng ?: 0.0,
                            title = event.title,
                            description = event.description ?: "No description",
                            eventId = event.eventId,
                            selectedStartDateTime = event.selectedStartDateTime,
                            selectedCategory = event.selectedCategory,
                            photos = event.photos,
                            host = event.host.displayName
                        )
                    )
                }
                _clusterEvents.value = clusterEvents

                Log.d("mapviewmodel", "getevents: $events")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("MapViewViewModel", "error message: ${e.message}")
                isLoading.value = false
                errorFetchingEvent.value = true
            }
        }
    }

    public class EventClusterItem(
        lat: Double,
        lng: Double,
        title: String,
        eventId: Int,
        selectedStartDateTime: LocalDateTime,
        description: String?,
        selectedCategory: String,
        host: String?,
        photos: List<String?>?,
    ) : ClusterItem {

        val title1: String
        var selectedStartDateTime: LocalDateTime = LocalDateTime.now()
        var eventId: Int = 0
        private val position: LatLng
        var description: String? = null
        var selectedCategory: String = ""
        var photos: List<String?>? = null
        val location: Location = Location(
            city = "",
            completeAddress = "",
            geoLocation = null
        )
        var host: String? = null

        override fun getPosition(): LatLng {
            return position
        }

        override fun getTitle(): String? {
            return title1
        }


        override fun getSnippet(): String {
            return description.toString()
        }

        override fun getZIndex(): Float? {
            return 0f
        }

        init {
            position = LatLng(lat, lng)
            this.title1 = title
            this.description = description
            this.selectedStartDateTime = selectedStartDateTime
            this.eventId = eventId
            this.selectedCategory = selectedCategory
            this.photos = photos
            this.host = host


        }
    }
}
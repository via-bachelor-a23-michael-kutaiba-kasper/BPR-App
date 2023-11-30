package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateEventViewModel() : ViewModel() {
    val eventCreated = mutableStateOf(false)
    val createdEventId = mutableStateOf(0)
    private val eventRepository: IEventRepository = EventRepository()
    var predefinedKeywords = mutableStateOf(emptyList<String>())
    var predefinedCategories = mutableStateOf(emptyList<String>())
    private var _title = mutableStateOf("")
    private var _description = mutableStateOf("")
    private var _isPrivate = mutableStateOf(false)
    private var _isPaid = mutableStateOf(false)
    private var _isAdultsOnly = mutableStateOf(false)
    private var _selectedStartDateTime = mutableStateOf(LocalDateTime.now())
    private var _selectedEndDateTime = mutableStateOf(LocalDateTime.now().plusHours(3))
    private var _keywords = mutableStateOf(emptyList<String>())
    private var _selectedCategory = mutableStateOf("Choose Category")
    private var _maxNumberOfAttendees = mutableStateOf(0)
    private val _location =
        mutableStateOf(Location("Horsens", "Hospitalsgade 4, 8700 Horsens", GeoLocation(0.0, 0.0)))
    private val _event = mutableStateOf<Event>(
        Event(
            title = _title.value,
            description = _description.value,
            url = null,
            location = Location(
                city = _location.value.city,
                completeAddress = _location.value.completeAddress,
                geoLocation = GeoLocation(
                    lat = _location.value.geoLocation!!.lat,
                    lng = _location.value.geoLocation!!.lng
                )
            ),
            isPrivate = _isPrivate.value,
            isPaid = _isPaid.value,
            isAdultsOnly = _isAdultsOnly.value,
            selectedStartDateTime = _selectedStartDateTime.value,
            selectedEndDateTime = _selectedEndDateTime.value,
            selectedKeywords = _keywords.value,
            selectedCategory = _selectedCategory.value,
            maxNumberOfAttendees = _maxNumberOfAttendees.value,
            host = User(
                displayName = "Michael Kuta Ibaka",
                userId = "123456789",
                photoUrl = null,
                creationDate = LocalDateTime.now(),
                lastSeenOnline = LocalDateTime.now()
            ),
            lastUpdatedDate = LocalDateTime.now(),
            photos = emptyList()
        )
    )
    val event: State<Event> get() = _event
    val _host = mutableStateOf(
        User(
            "",
            "",
            null,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
    )
    val title: State<String> get() = _title
    val description: State<String> get() = _description
    val isPrivate: State<Boolean> get() = _isPrivate
    val isAdultsOnly: State<Boolean> get() = _isAdultsOnly
    val selectedStartDateTime: State<LocalDateTime> get() = _selectedStartDateTime
    val selectedEndDateTime: State<LocalDateTime> get() = _selectedEndDateTime
    val selectedKeywords: State<List<String>> get() = _keywords
    val selectedCategory: State<String> get() = _selectedCategory
    val isPaid: State<Boolean> get() = _isPaid
    val maxNumberOfAttendees: State<Int> get() = _maxNumberOfAttendees
    val location: State<Location> get() = _location
    val host: State<User> get() = _host

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun setDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun setIsPrivate(newIsPrivate: Boolean): Boolean {
        _isPrivate.value = newIsPrivate
        return _isPrivate.value
    }

    fun setIsAdultsOnly(newIsAdultsOnly: Boolean): Boolean {
        _isAdultsOnly.value = newIsAdultsOnly
        return _isAdultsOnly.value
    }

    fun setSelectedDateTime(newSelectedDateTime: LocalDateTime) {
        _selectedStartDateTime.value = newSelectedDateTime
    }

    fun setSelectedEndDateTime(newSelectedEndDateTime: LocalDateTime) {
        _selectedEndDateTime.value = newSelectedEndDateTime
    }

    fun setKeywords(newKeywords: List<String>): List<String> {
        _keywords.value = newKeywords
        return _keywords.value
    }

    fun setCategory(newCategory: String): String {
        _selectedCategory.value = newCategory
        return _selectedCategory.value
    }

    fun setIsPaid(newIsPaid: Boolean): Boolean {
        _isPaid.value = newIsPaid
        return _isPaid.value
    }

    fun setMaxNumberOfAttendees(newMaxNumberOfAttendees: Int): Int {
        _maxNumberOfAttendees.value = newMaxNumberOfAttendees
        return _maxNumberOfAttendees.value
    }

    fun setLocation(newLocation: Location): Location {
        _location.value = newLocation
        return _location.value
    }

    fun setHost(newHost: User): User {
        _host.value = newHost
        return _host.value
    }

    fun setEvent(): Event {
        _event.value = Event(
            title = _title.value,
            description = _description.value,
            url = null,
            location = Location(
                city = _location.value.city,
                completeAddress = _location.value.completeAddress,
                geoLocation = GeoLocation(
                    lat = _location.value.geoLocation!!.lat,
                    lng = _location.value.geoLocation!!.lng
                )
            ),
            isPrivate = _isPrivate.value,
            isPaid = _isPaid.value,
            isAdultsOnly = _isAdultsOnly.value,
            selectedStartDateTime = _selectedStartDateTime.value,
            selectedEndDateTime = _selectedEndDateTime.value,
            selectedKeywords = _keywords.value,
            selectedCategory = _selectedCategory.value,
            maxNumberOfAttendees = _maxNumberOfAttendees.value,
            host = _host.value,
            lastUpdatedDate = LocalDateTime.now(),
            photos = emptyList()
        )
        return _event.value
    }


    fun createEvent() {
        viewModelScope.launch {
            try {
                val eventId = eventRepository.createEvent(event = _event.value)
                eventCreated.value = true
                createdEventId.value = eventId
                Log.d("CreateEventViewModel", "eventcreated: ${eventCreated.value}")
                Log.d("CreateEventViewModel", "createEvent: $eventId")
            } catch (e: Exception) {
                Log.d("CreateEventViewModel", "event creation failed: ${e.message}")
            }
        }
    }

    private fun getKeywords(): List<String> {
        viewModelScope.launch {
            try {
                val keywords = eventRepository.getKeywords()
                predefinedKeywords.value = keywords
                Log.d("CreateEventViewModel", "getKeywords: $keywords")
            } catch (e: Exception) {
                Log.d("CreateEventViewModel", "getKeywords: ${e.message}")
            }
        }
        return emptyList()
    }

    private fun getCategories(): List<String> {
        viewModelScope.launch {
            try {
                val categories = eventRepository.getCategories()
                predefinedCategories.value = categories
                Log.d("CreateEventViewModel", "getCategories: $categories")
            } catch (e: Exception) {
                Log.d("CreateEventViewModel", "getCategories: ${e.message}")
            }
        }
        return emptyList()
    }

    init {
        eventCreated.value = false
        getKeywords()
        getCategories()
    }
}
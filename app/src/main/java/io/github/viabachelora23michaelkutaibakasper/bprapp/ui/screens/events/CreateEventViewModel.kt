package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import java.time.LocalDateTime

class CreateEventViewModel : ViewModel() {

    var _title = mutableStateOf("")
    var _address = mutableStateOf("")
    var _type = mutableStateOf("Choose Type")
    var _description = mutableStateOf("")
    var _isPrivate = mutableStateOf(false)
    var _isPaid = mutableStateOf(false)
    var _isAdultsOnly = mutableStateOf(false)
    var _selectedStartDateTime = mutableStateOf(LocalDateTime.now())
    var _selectedEndDateTime = mutableStateOf(LocalDateTime.now().plusHours(3))
    var _keywords = mutableStateOf(emptyList<String>())
    var _selectedCategory = mutableStateOf("Choose Category")
    var _maxNumberOfAttendees = mutableStateOf(0)
    val _location = mutableStateOf(Location("", "", "", "", "", "", GeoLocation(0.0, 0.0)))

    val title: State<String> get() = _title
    val address: State<String> get() = _address
    val type: State<String> get() = _type
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

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun setAddress(newAdress: String) {
        _address.value = newAdress
    }

    fun setType(newType: String) {
        _type.value = newType
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


}
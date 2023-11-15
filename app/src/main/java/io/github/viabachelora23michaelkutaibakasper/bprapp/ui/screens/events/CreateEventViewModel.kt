package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class CreateEventViewModel : ViewModel() {

    var _title = mutableStateOf("")
    var _address = mutableStateOf("")
    var _type = mutableStateOf("Choose Type")
    var _description = mutableStateOf("")
    var _isPrivate = mutableStateOf(false)
    var _isAdultsOnly = mutableStateOf(false)
    var _selectedStartDateTime = mutableStateOf(LocalDateTime.now())
    var _selectedEndDateTime = mutableStateOf(LocalDateTime.now().plusHours(3))


    val title: State<String> get() = _title
    val address: State<String> get() = _address
    val type: State<String> get() = _type
    val description: State<String> get() = _description
    val isPrivate: State<Boolean> get() = _isPrivate
    val isAdultsOnly: State<Boolean> get() = _isAdultsOnly
    val selectedStartDateTime: State<LocalDateTime> get() = _selectedStartDateTime
    val selectedEndDateTime: State<LocalDateTime> get() = _selectedEndDateTime

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

    fun setIsAdultsOnly(newIsAdultsOnly: Boolean) {
        _isAdultsOnly.value = newIsAdultsOnly
    }

    fun setSelectedDateTime(newSelectedDateTime: LocalDateTime) {
        _selectedStartDateTime.value = newSelectedDateTime
    }

    fun setSelectedEndDateTime(newSelectedEndDateTime: LocalDateTime) {
        _selectedEndDateTime.value = newSelectedEndDateTime
    }

}
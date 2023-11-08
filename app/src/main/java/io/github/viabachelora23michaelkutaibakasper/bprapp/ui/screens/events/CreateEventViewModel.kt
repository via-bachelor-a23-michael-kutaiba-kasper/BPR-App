package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateEventViewModel : ViewModel() {

    var title = mutableStateOf("")
    var address = mutableStateOf("")
    var type = mutableStateOf("Choose Type")
    var description = mutableStateOf("")
    var isPrivate = mutableStateOf(false)
    var isAdultsOnly = mutableStateOf(false)
    var selectedDateTime = mutableStateOf("")
}
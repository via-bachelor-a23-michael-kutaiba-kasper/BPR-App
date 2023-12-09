package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AchievementsViewModel : ViewModel() {

    val isLoading = mutableStateOf(false)
    fun getAchievements() {
        isLoading.value = true
        isLoading.value = false
    }
}
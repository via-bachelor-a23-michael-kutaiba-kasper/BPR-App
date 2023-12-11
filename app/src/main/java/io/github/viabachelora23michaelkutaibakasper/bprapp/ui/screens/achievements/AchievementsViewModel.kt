package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AchievementsViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {

    val isLoading = mutableStateOf(false)
    private var _user = MutableStateFlow(Firebase.auth.currentUser)
    val user = _user.asStateFlow()

    private var _achievements = MutableStateFlow<List<Achievement>>(
        listOf(
            Achievement(
                title = "Music Maestro",
                description = "Host 5 music events",
                points = 10,
                id = 1,
                isAchieved = true
            ),
            Achievement(
                title = "Athlete",
                description = "Host 5 sport related events",
                points = 20,
                id = 1,
                isAchieved = false
            ), Achievement(
                title = "Music Maestro",
                description = "Host 5 music events",
                points = 10,
                id = 1,
                isAchieved = false
            ),
            Achievement(
                title = "Athlete",
                description = "Host 5 sport related events",
                points = 20,
                id = 1,
                isAchieved = true
            )
        )
    )
    val achievements = _achievements.asStateFlow()

    private val _selectedAchievement =
        MutableStateFlow<Achievement>(
            Achievement(
                title = "Music Maestro",
                description = "Host 5 music events",
                points = 10,
                id = 1,
                isAchieved = false
            )
        )
    val selectedAchievement = _selectedAchievement.asStateFlow()
    fun setSelectedAchievement(achievement: Achievement) {
        _selectedAchievement.value = achievement

    }

    fun getAchievements() {
        _user.value = Firebase.auth.currentUser
        isLoading.value = true
        isLoading.value = false
        val sortedlist = _achievements.value.sortedByDescending { !it.isAchieved }
        _achievements.value = sortedlist
    }
}
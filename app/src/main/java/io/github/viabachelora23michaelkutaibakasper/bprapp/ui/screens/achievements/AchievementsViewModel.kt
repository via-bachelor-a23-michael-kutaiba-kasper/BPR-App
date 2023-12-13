package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Experience
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AchievementsViewModel(val repository: IEventRepository = EventRepository()) : ViewModel() {
    val isLoading = mutableStateOf(false)
    private var _user = MutableStateFlow(Firebase.auth.currentUser)
    val user = _user.asStateFlow()
    private var _experience = MutableStateFlow(Experience(200, 2, 100, 300, 1, "Beginner"))
    val experience = _experience.asStateFlow()
    private var _allAchievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements = _allAchievements.asStateFlow()
    private var _userAchievements = MutableStateFlow<List<Achievement>>(emptyList())
    val userAchievements = _userAchievements.asStateFlow()
    private val _selectedAchievement =
        MutableStateFlow(
            Achievement(
                name = "Music Maestro",
                description = "Host 5 music events",
                expReward = 10,
                icon = "",
                requirement = 5,
                unlockDate = null,
                isAchieved = false, progress = 0

            )
        )
    val selectedAchievement = _selectedAchievement.asStateFlow()
    fun setSelectedAchievement(achievement: Achievement) {
        _selectedAchievement.value = achievement
    }

    fun all() {
        getUserExperience()
        getUserAchievements()
        getAchievements()

    }

    private fun synchronizeAchievements() {
        val hashmap = HashMap<String, Achievement>()
        for (userachivement in _userAchievements.value) {
            hashmap[userachivement.name] = userachivement
        }
        for (achivement in _allAchievements.value) {
            if (hashmap.containsKey(achivement.name)) {
                hashmap[achivement.name]!!.isAchieved =
                    (achivement.unlockDate != null && achivement.unlockDate!!.year > 1) ||
                            hashmap[achivement.name]?.progress!! >= achivement.requirement
                _allAchievements.value = _allAchievements.value.filter {
                    it.name != achivement.name
                }
            }
        }
        _allAchievements.value =
            (hashmap.values.toList() + _allAchievements.value).sortedByDescending { it.isAchieved }
    }

    init {
        all()
    }

    fun getAchievements() {
        _user.value = Firebase.auth.currentUser
        viewModelScope.launch {
            try {
                isLoading.value = true
                val achievements = repository.getAllAchievements()
                _allAchievements.value = achievements.drop(1)
                synchronizeAchievements()
                Log.d("AchievementsViewModel", " allacievements: $achievements")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d(
                    "AchievementsViewModel",
                    "failed to fetch achievements: ${e.printStackTrace()}"
                )
                isLoading.value = false
            }
        }
    }

    fun getUserAchievements() {
        _user.value = Firebase.auth.currentUser
        viewModelScope.launch {
            try {
                val achievements = repository.getUserAchievements(user.value!!.uid)
                _userAchievements.value = achievements
                synchronizeAchievements()
                Log.d("AchievementsViewModel", " user achievements: $achievements")
            } catch (e: Exception) {
                Log.d(
                    "AchievementsViewModel",
                    "failed to fetch user achievements: ${e.printStackTrace()}"
                )
            }
        }
    }

    fun getUserExperience() {
        viewModelScope.launch {
            try {
                val fetchedExp = repository.getExperience(user.value!!.uid)
                _experience.value = fetchedExp
                Log.d("AchievementsViewModel", "getUserExperience: $fetchedExp")
            } catch (e: Exception) {
                Log.d("AchievementsViewModel", "failed to getExperience: ${e.message}")
            }
        }
    }
}
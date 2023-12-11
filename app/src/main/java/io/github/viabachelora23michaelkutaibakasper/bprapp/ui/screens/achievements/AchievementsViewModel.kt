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
    private var _experience = MutableStateFlow(Experience(200, 2, 100, 300))
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
        getAchievements()
        getUserAchievements()
        combineAllchievementsWithUserAchievements()
    }

    fun setIsAchieved() {
        //if an achivement from all achivements is in user achivements, set isachieved to true
        for (achivement in _allAchievements.value) {
            for (userachivement in _userAchievements.value) {
                if (achivement.name == userachivement.name) {
                    achivement.isAchieved = true
                }
            }
        }
    }

    fun combineAllchievementsWithUserAchievements() {
        val hashmap = HashMap<String, Achievement>()
        for (userachivement in _userAchievements.value) {
            hashmap[userachivement.name] = userachivement
        }
        for (achivement in _allAchievements.value) {
            if (hashmap.containsKey(achivement.name)) {
                achivement.progress = hashmap[achivement.name]!!.progress
                achivement.isAchieved =
                    hashmap[achivement.name]!!.progress == achivement.requirement
                achivement.unlockDate = hashmap[achivement.name]!!.unlockDate
            }
        }
        _allAchievements.value = hashmap.values.toList().sortedByDescending { it.isAchieved }
    }

    init {
        all()
    }

    fun getAchievements() {
        _user.value = Firebase.auth.currentUser
        // val sortedlist = _achievements.value.sortedByDescending { !it.isAchieved }

        viewModelScope.launch {
            try {
                isLoading.value = true
                val achievements = repository.getAllAchievements()
                _allAchievements.value = achievements.drop(1)
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
        // val sortedlist = _achievements.value.sortedByDescending { !it.isAchieved }

        viewModelScope.launch {
            try {
                val achievements = repository.getUserAchievements(user.value!!.uid)
                _userAchievements.value = achievements
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
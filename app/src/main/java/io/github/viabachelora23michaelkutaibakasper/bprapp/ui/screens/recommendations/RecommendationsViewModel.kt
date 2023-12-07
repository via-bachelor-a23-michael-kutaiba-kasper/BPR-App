package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.FireStoreClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendationsViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {
    private val eventRepository = repository
    val isSurveyFilled = mutableStateOf(false)
    var user = mutableStateOf(Firebase.auth.currentUser)
    var predefinedKeywords = mutableStateOf(emptyList<String>())
    var predefinedCategories = mutableStateOf(emptyList<String>())
    private var _keywords = mutableStateOf(emptyList<String>())
    private var _categories = mutableStateOf(emptyList<String>())
    private val firestoreClient = FireStoreClient()
    val isLoading = mutableStateOf(false)
    val errorFetchingEvents = mutableStateOf(false)
    private val _recommendationsList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    val recommendationsList = _recommendationsList.asStateFlow()

    fun setSurveyFilled(hostId: String) {
        isSurveyFilled.value = firestoreClient.SetSurveyFilled(hostId)
    }

    val selectedKeywords: State<List<String>> get() = _keywords
    val selectedCategory: State<List<String>> get() = _categories

    fun setKeywords(newKeywords: List<String>): List<String> {
        _keywords.value = newKeywords
        return _keywords.value
    }

    fun setCategories(newCategories: List<String>): List<String> {
        _categories.value = newCategories
        return _categories.value
    }

    init {
        isSurveyFilledForUser(user.value?.uid ?: "")
        getKeywords()
        getCategories()
    }

    private fun isSurveyFilledForUser(userId: String): Boolean {
        viewModelScope.launch {
            isSurveyFilled.value = firestoreClient.isSurveyFilledForUser(userId)
        }
        return isSurveyFilled.value
    }

    private fun getKeywords(): List<String> {
        viewModelScope.launch {
            try {
                val keywords = eventRepository.getKeywords()
                predefinedKeywords.value = keywords
                Log.d("RecommendationsViewModel", "getKeywords: $keywords")
            } catch (e: Exception) {
                Log.d("RecommendationsViewModel", "failed to getKeywords: ${e.message}")
            }
        }
        return emptyList()
    }

    private fun getCategories(): List<String> {
        viewModelScope.launch {
            try {
                val categories = eventRepository.getCategories()
                predefinedCategories.value = categories
                Log.d("RecommendationsViewModel", "failed to getCategories: $categories")
            } catch (e: Exception) {
                Log.d("RecommendationsViewModel", "failed to getCategories: ${e.message}")
            }
        }
        return emptyList()
    }

    fun getRecommendations(userId: String, numberOfEvents: Int) {
        viewModelScope.launch {
            errorFetchingEvents.value = false
            try {
                isLoading.value = true
                val events =
                    eventRepository.getReccommendations(
                        userId = userId,
                        numberOfEvents = numberOfEvents
                    )
                _recommendationsList.value = events
                Log.d("recommendationsViewmodel", "getevents: $events")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("recommendationsViewViewModel", "error message: ${e.printStackTrace()}")
                isLoading.value = false
                errorFetchingEvents.value = true
            }
        }
    }

}
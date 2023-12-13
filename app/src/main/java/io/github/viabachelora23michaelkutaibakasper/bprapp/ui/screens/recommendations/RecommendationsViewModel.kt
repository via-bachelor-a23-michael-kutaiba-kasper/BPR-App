package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendationsViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {
    private val eventRepository = repository
    private val _isSurveyFilled = MutableStateFlow(false)
    val isSurveyFilled = _isSurveyFilled.asStateFlow()
    private var _user = MutableStateFlow(Firebase.auth.currentUser)
    val user = _user.asStateFlow()
    private var _predefinedKeywords = MutableStateFlow(emptyList<String>())
    val predefinedKeywords = _predefinedKeywords.asStateFlow()
    private var _predefinedCategories = MutableStateFlow(emptyList<String>())
    val predefinedCategories = _predefinedCategories.asStateFlow()
    val isLoading = mutableStateOf(false)
    val errorFetchingEvents = mutableStateOf(false)
    private val _recommendationsList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    val recommendationsList = _recommendationsList.asStateFlow()
    val selectedKeywords = mutableStateOf(emptyList<String>())
    val selectedCategories = mutableStateOf(emptyList<String>())


    fun setCategories(categories: List<String>): List<String> {
        selectedCategories.value = categories
        return selectedCategories.value
    }

    fun setKeywords(keywords: List<String>): List<String> {
        selectedKeywords.value = keywords
        return selectedKeywords.value
    }

    init {
        all()
    }

    fun all() {
        _user.value = Firebase.auth.currentUser
        if (user.value == null)
            return
        isInterestSurveyFilled(user.value!!.uid)
        getKeywords()
        getCategories()
        getRecommendations(userId = user.value!!.uid, numberOfEvents = 5)
    }

    fun getKeywords(): List<String> {
        viewModelScope.launch {
            try {
                val keywords = eventRepository.getKeywords()
                _predefinedKeywords.value = keywords
                Log.d("RecommendationsViewModel", "getKeywords: $keywords")
            } catch (e: Exception) {
                Log.d("RecommendationsViewModel", "failed to getKeywords: ${e.message}")
            }
        }
        return emptyList()
    }

    fun getCategories(): List<String> {
        viewModelScope.launch {
            try {
                val categories = eventRepository.getCategories()
                _predefinedCategories.value = categories
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

    fun isInterestSurveyFilled(userId: String): Int {
        var isFilled: Boolean
        var statusCode = 0
        viewModelScope.launch {
            try {
                val status = eventRepository.getInterestSurvey(userId = userId)
                isFilled = status.code == 200
                _isSurveyFilled.value = isFilled
                statusCode = status.code
                Log.d("RecommendationsViewModel", "surveyFilled: $isFilled")
            } catch (e: Exception) {
                Log.d("RecommendationsViewModel", "failed to get status of survey: ${e.message}")
            }
        }
        return statusCode
    }

    fun storeInterestSurvey(
        userId: String,
        keywords: List<String>,
        categories: List<String>
    ): Int {
        var statusCode = 0
        var success: Boolean
        if (keywords.size == 3 || categories.size == 3) {

            viewModelScope.launch {
                try {
                    val status = eventRepository.storeInterestSurvey(
                        userId = userId,
                        keywords = keywords,
                        categories = categories
                    )
                    success = status.code == 200
                    statusCode = status.code
                    _isSurveyFilled.value = success
                    Log.d("RecommendationsViewModel", "surveyFilled: $success")
                    isInterestSurveyFilled(userId)
                } catch (e: Exception) {
                    Log.d(
                        "RecommendationsViewModel",
                        "failed to get status of survey: ${e.message}"
                    )
                }
            }
            return statusCode
        }
        return statusCode
    }

}
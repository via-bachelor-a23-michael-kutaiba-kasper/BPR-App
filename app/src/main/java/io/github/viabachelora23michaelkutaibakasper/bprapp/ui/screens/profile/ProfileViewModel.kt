package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.EventRating
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.IEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {
    private val eventRepository: IEventRepository = repository
    private val _eventList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _finishedJoinedList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _reviewIds = MutableStateFlow<List<EventRating>>(emptyList())
    private val _experienceHistory = MutableStateFlow<List<ExperienceHistory>>(emptyList())
    private val _highestRatedCategory = MutableStateFlow("")
    val eventList = _eventList.asStateFlow()
    val finishedJoinedEvents = _finishedJoinedList.asStateFlow()
    val reviewIds = _reviewIds.asStateFlow()
    val experienceHistory = _experienceHistory.asStateFlow()
    val isLoading = mutableStateOf(false)
    val highestRatedCategory = _highestRatedCategory.asStateFlow()
    val reviewCreated = mutableStateOf(false)
    var user = mutableStateOf(Firebase.auth.currentUser)
    val errorFetchingEvents = mutableStateOf(false)


    fun allOfThem() {
        if (user.value == null)
            return

        getEvents(hostId = user.value!!.uid, includePrivate = true)
        getReviewIds(hostId = user.value!!.uid)
        getFinishedJoinedEvents(userId = user.value!!.uid)
        getExperienceHistory(hostId = user.value!!.uid)
    }

    data class EventWithCategory(
        val eventId: Int,
        val rating: Float,
        val category: String
    )

    fun getFavoriteCategory() {
        val newList = mutableListOf<EventWithCategory>()
        for (event in _finishedJoinedList.value) {
            for (review in _reviewIds.value) {
                if (event.eventId == review.eventId) {
                    newList.add(
                        EventWithCategory(
                            event.eventId,
                            review.rating,
                            event.selectedCategory
                        )
                    )
                }
            }
        }
        val categoryAverageRatings = newList
            .groupBy { it.category }
            .mapValues { (_, events) ->
                events.map { it.rating }.average()
            }

        val categoryWithHighestAverageRating = categoryAverageRatings.maxByOrNull { it.value }

        _highestRatedCategory.value = categoryWithHighestAverageRating?.key ?: ""
        Log.d("profileviewmodel", "getFavoriteCategory: ${_highestRatedCategory.value}")
    }


    private fun getEvents(hostId: String, includePrivate: Boolean? = null) {
        viewModelScope.launch {
            errorFetchingEvents.value = false
            try {
                isLoading.value = true
                val events =
                    eventRepository.getEvents(hostId = hostId, includePrivate = includePrivate)
                _eventList.value = events
                Log.d("profileviewmodel", "getevents: $events")
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.message}")
                isLoading.value = false
                errorFetchingEvents.value = true
            }
        }
    }

    fun getFinishedJoinedEvents(userId: String) {
        viewModelScope.launch {
            try {
                val events = eventRepository.getFinishedJoinedEvents(userId)
                _finishedJoinedList.value = events
                getFavoriteCategory()
                Log.d("profileviewmodel", "getevents: $events")

            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.message}")
            }
        }
    }

    fun createReview(eventId: Int, userId: String, rating: Float, reviewDate: String) {
        reviewCreated.value = false
        viewModelScope.launch {
            try {
                Log.d("profileviewmodel", "createReview: $eventId, $userId, $rating, $reviewDate")
                val review = eventRepository.createReview(eventId, userId, rating, reviewDate)
                Log.d("profileviewmodel", "getevents: $review")
                reviewCreated.value = true
            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.printStackTrace()}")
                reviewCreated.value = false
            }
        }
    }

    fun getReviewIds(hostId: String) {
        viewModelScope.launch {
            try {
                val reviewIds = eventRepository.getReviewIds(hostId)
                _reviewIds.value = reviewIds
                Log.d("profileviewmodel", "the idsss!!: $reviewIds")
            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.printStackTrace()}")
            }
        }
    }

    fun getExperienceHistory(hostId: String) {
        viewModelScope.launch {
            try {
                val experienceHistory = eventRepository.getUserExperienceHistory(hostId)
                _experienceHistory.value = experienceHistory
                Log.d("profileViewViewModel", "the experienceHistory!!: $experienceHistory")
            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.printStackTrace()}")
            }
        }
    }

}
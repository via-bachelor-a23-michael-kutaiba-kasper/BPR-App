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
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.events.EventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.events.IEventRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.progress.IProgressRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.review.IReviewRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.progress.ProgressRepository
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.repository.review.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val reviewRepository: IReviewRepository = ReviewRepository(),
    private val eventRepository: IEventRepository = EventRepository(),
    private val progressRepository: IProgressRepository = ProgressRepository()
) : ViewModel() {
    //private val reviewRepository = reviewRepo
    private val _createdEvents = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _finishedJoinedList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _currentJoinedList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _reviewIds = MutableStateFlow<List<EventRating>>(emptyList())
    private val _experienceHistory = MutableStateFlow<List<ExperienceHistory>>(emptyList())
    private val _highestRatedCategory = MutableStateFlow("")
    val createdEvents = _createdEvents.asStateFlow()
    val finishedJoinedEvents = _finishedJoinedList.asStateFlow()
    val currentJoinedEvents = _currentJoinedList.asStateFlow()
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

        getCreatedEvents(hostId = user.value!!.uid, includePrivate = true)
        getReviewIds(hostId = user.value!!.uid)
        getFinishedJoinedEvents(userId = user.value!!.uid)
        getCurrentJoinedEvents(userId = user.value!!.uid)
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


    private fun getCreatedEvents(hostId: String, includePrivate: Boolean? = null) {
        viewModelScope.launch {
            errorFetchingEvents.value = false
            try {
                isLoading.value = true
                val events =
                    eventRepository.getEvents(hostId = hostId, includePrivate = includePrivate)
                _createdEvents.value = events
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
                val events = eventRepository.getJoinedEvents(userId, EventState.COMPLETED.name)
                _finishedJoinedList.value = events
                getFavoriteCategory()
                Log.d("profileviewmodel", "getFinishedJoinedEvents: $events")

            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.message}")
            }
        }
    }

    fun getCurrentJoinedEvents(userId: String) {
        viewModelScope.launch {
            try {
                val events = eventRepository.getJoinedEvents(userId, EventState.CURRENT.name)
                _currentJoinedList.value = events
                Log.d("profileviewmodel", "getCurrentJoinedEvents: $events")

            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.message}")
            }
        }
    }

    enum class EventState {
        COMPLETED,
        CURRENT
    }

    fun createReview(eventId: Int, userId: String, rating: Float, reviewDate: String) {
        reviewCreated.value = false
        viewModelScope.launch {
            try {
                Log.d("profileviewmodel", "createReview: $eventId, $userId, $rating, $reviewDate")
                val review = reviewRepository.createReview(eventId, userId, rating, reviewDate)
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
                val reviewIds = reviewRepository.getReviewIds(hostId)
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
                val experienceHistory = progressRepository.getUserExperienceHistory(hostId)
                _experienceHistory.value = experienceHistory
                Log.d("profileViewViewModel", "the experienceHistory!!: $experienceHistory")
            } catch (e: Exception) {
                Log.d("profileViewViewModel", "error message: ${e.printStackTrace()}")
            }
        }
    }

}
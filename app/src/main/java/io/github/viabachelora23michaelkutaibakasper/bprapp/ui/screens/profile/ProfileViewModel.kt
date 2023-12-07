package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile


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


class ProfileViewModel(repository: IEventRepository = EventRepository()) : ViewModel() {
    private val eventRepository: IEventRepository = repository
    private val _eventList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _finishedJoinedList = MutableStateFlow<List<MinimalEvent>>(emptyList())
    private val _reviewIds = MutableStateFlow<List<Int>>(emptyList())
    val eventList = _eventList.asStateFlow()
    val finishedJoinedEvents = _finishedJoinedList.asStateFlow()
    val reviewIds = _reviewIds.asStateFlow()
    val isLoading = mutableStateOf(false)
    val reviewCreated = mutableStateOf(false)
    var user = mutableStateOf(Firebase.auth.currentUser)
    val errorFetchingEvents = mutableStateOf(false)


    fun allOfThem() {

        getEvents(hostId = user.value!!.uid, includePrivate = true)
        getReviewIds(hostId = user.value!!.uid)
        getFinishedJoinedEvents(userId = user.value!!.uid)

    }


    fun getEvents(hostId: String, includePrivate: Boolean? = null) {
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

}
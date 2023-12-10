package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AchievementsViewModel : ViewModel() {

    val isLoading = mutableStateOf(false)
    private var _user = MutableStateFlow(Firebase.auth.currentUser)
    val user = _user.asStateFlow()
    fun getAchievements() {
        _user.value = Firebase.auth.currentUser
        isLoading.value = true
        isLoading.value = false
    }
}
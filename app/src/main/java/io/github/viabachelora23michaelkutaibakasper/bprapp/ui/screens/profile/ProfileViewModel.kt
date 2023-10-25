package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.SignInResult
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()


    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                errorMessage = result.errorMessage
            )
        }
    }


    fun resetState() {
        _state.update { SignInState() }
    }
}
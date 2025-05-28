package com.example.daily_habit_tracker_app.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_habit_tracker_app.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _email = mutableStateOf("")
    var email: String
        get() = _email.value
        set(value) {
            _email.value = value
        }

    private val _password = mutableStateOf("")
    var password: String
        get() = _password.value
        set(value) {
            _password.value = value
        }

    private val _isLoading = mutableStateOf(false)
    var isLoading: Boolean
        get() = _isLoading.value
        set(value) {
            _isLoading.value = value
        }

    private val _errorMessage = mutableStateOf<String?>(null)
    var errorMessage: String?
        get() = _errorMessage.value
        set(value) {
            _errorMessage.value = value
        }

    private val _isAuthenticated = mutableStateOf(authRepository.getCurrentUser() != null)
    var isAuthenticated: Boolean
        get() = _isAuthenticated.value
        set(value) {
            _isAuthenticated.value = value
        }
    fun login() = viewModelScope.launch {
        isLoading = true
        errorMessage = null
        val result = authRepository.login(email, password)
        isAuthenticated = result.isSuccess
        if (result.isFailure) {
            errorMessage = result.exceptionOrNull()?.message
        }
        isLoading = false
    }

    fun signup() = viewModelScope.launch {
        isLoading = true
        errorMessage = null
        val result = authRepository.signup(email, password)
        isAuthenticated = result.isSuccess
        if (result.isFailure) {
            errorMessage = result.exceptionOrNull()?.message
        }
        isLoading = false
    }

    fun logout() {
        authRepository.logout()
        isAuthenticated = false
    }

    fun getCurrentUser(): FirebaseUser? = authRepository.getCurrentUser()
}

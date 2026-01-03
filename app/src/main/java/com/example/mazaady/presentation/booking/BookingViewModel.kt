package com.example.mazaady.presentation.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.domain.useCase.BookTripsUseCase
import com.example.mazaady.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val bookTripsUseCase: BookTripsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookingState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<BookingEffect>()
    val effect = _effect.asSharedFlow()

    fun handleIntent(intent: BookingIntent) {
        when (intent) {
            is BookingIntent.OnEmailChange -> onEmailChange(intent.email)
            is BookingIntent.OnLaunchIdChange -> onLaunchIdChange(intent.launchId)
            is BookingIntent.OnLoginClick -> login()
            is BookingIntent.OnBookClick -> bookTrips()
            is BookingIntent.OnDismissSuccess -> dismissSuccess()
            is BookingIntent.OnDismissError -> dismissError()
        }
    }

    private fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = if (email.isValidEmail()) null else "Invalid email format"
            )
        }
    }

    private fun onLaunchIdChange(launchId: String) {
        _state.update { currentState ->
            val ids = launchId.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            currentState.copy(selectedLaunchIds = ids)
        }
    }

    private fun login() {
        val email = _state.value.email

        if (!email.isValidEmail()) {
            _state.update { it.copy(emailError = "Please enter a valid email") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = loginUseCase(email)

            result.fold(
                onSuccess = { loginResult ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            error = null
                        )
                    }
                    _effect.emit(BookingEffect.ShowSuccess("Login successful!"))
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: "Login failed"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = errorMessage
                        )
                    }
                    _effect.emit(BookingEffect.ShowError(errorMessage))
                }
            )
        }
    }

    private fun bookTrips() {
        val launchIds = _state.value.selectedLaunchIds

        if (launchIds.isEmpty()) {
            viewModelScope.launch {
                _effect.emit(BookingEffect.ShowError("Please enter at least one launch ID"))
            }
            return
        }

        if (!_state.value.isLoggedIn) {
            viewModelScope.launch {
                _effect.emit(BookingEffect.ShowError("Please login first"))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = bookTripsUseCase(launchIds)

            result.fold(
                onSuccess = { bookingResult ->
                    val message = if (bookingResult.success) {
                        bookingResult.message ?: "Booking successful!"
                    } else {
                        bookingResult.message ?: "Booking failed"
                    }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            successMessage = if (bookingResult.success) message else null,
                            error = if (!bookingResult.success) message else null
                        )
                    }

                    if (bookingResult.success) {
                        _effect.emit(BookingEffect.ShowSuccess(message))
                    } else {
                        _effect.emit(BookingEffect.ShowError(message))
                    }
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: "Booking failed"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = errorMessage
                        )
                    }
                    _effect.emit(BookingEffect.ShowError(errorMessage))
                }
            )
        }
    }

    private fun dismissSuccess() {
        _state.update { it.copy(successMessage = null) }
    }

    private fun dismissError() {
        _state.update { it.copy(error = null) }
    }

    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}
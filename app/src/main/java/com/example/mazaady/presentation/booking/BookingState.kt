package com.example.mazaady.presentation.booking

data class BookingState(
    val email: String = "",
    val selectedLaunchIds: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val emailError: String? = null
)
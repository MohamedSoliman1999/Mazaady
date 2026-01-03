package com.example.mazaady.presentation.booking

sealed interface BookingIntent {
    data class OnEmailChange(val email: String) : BookingIntent
    data class OnLaunchIdChange(val launchId: String) : BookingIntent
    object OnLoginClick : BookingIntent
    object OnBookClick : BookingIntent
    object OnDismissSuccess : BookingIntent
    object OnDismissError : BookingIntent
}
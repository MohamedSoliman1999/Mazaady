package com.example.mazaady.presentation.booking

sealed interface BookingEffect {
    data class ShowSuccess(val message: String) : BookingEffect
    data class ShowError(val message: String) : BookingEffect
    object NavigateBack : BookingEffect
}

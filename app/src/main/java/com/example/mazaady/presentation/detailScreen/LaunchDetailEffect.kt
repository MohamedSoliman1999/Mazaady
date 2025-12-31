package com.example.mazaady.presentation.detailScreen

sealed interface LaunchDetailEffect {
    object NavigateBack : LaunchDetailEffect
    data class ShowError(val message: String) : LaunchDetailEffect
}
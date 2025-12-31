package com.example.mazaady.presentation.listScreen

sealed interface LaunchesListEffect {
    data class NavigateToDetail(val launchId: String) : LaunchesListEffect
    data class ShowError(val message: String) : LaunchesListEffect
}
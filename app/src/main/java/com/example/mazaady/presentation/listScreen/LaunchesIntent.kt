package com.example.mazaady.presentation.listScreen

import com.example.mazaady.domain.model.Launch

sealed interface LaunchesListIntent {
    object LoadLaunches : LaunchesListIntent
    object RetryLoad : LaunchesListIntent
    data class OnLaunchClick(val launchId: String) : LaunchesListIntent
    data class OnFavoriteClick(val launch: Launch) : LaunchesListIntent
}
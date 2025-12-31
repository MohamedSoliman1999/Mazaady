package com.example.mazaady.presentation.listScreen

sealed interface LaunchesListIntent {
    object LoadLaunches : LaunchesListIntent
    object RetryLoad : LaunchesListIntent
    data class OnLaunchClick(val launchId: String) : LaunchesListIntent
}
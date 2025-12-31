package com.example.mazaady.presentation.detailScreen

sealed interface LaunchDetailIntent {
    data class LoadDetail(val id: String) : LaunchDetailIntent
    object RetryLoad : LaunchDetailIntent
    object OnBackClick : LaunchDetailIntent
}
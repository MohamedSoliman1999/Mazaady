package com.example.mazaady.presentation.favorites

sealed interface FavoritesEffect {
    data class NavigateToDetail(val launchId: String) : FavoritesEffect
    data class ShowRemoved(val launchName: String) : FavoritesEffect
}
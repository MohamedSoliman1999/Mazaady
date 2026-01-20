package com.example.mazaady.presentation.favorites

import com.example.mazaady.domain.model.Launch

sealed interface FavoritesIntent {
    object LoadFavorites : FavoritesIntent
    data class OnLaunchClick(val launchId: String) : FavoritesIntent
    data class OnRemoveFavorite(val launch: Launch) : FavoritesIntent
}
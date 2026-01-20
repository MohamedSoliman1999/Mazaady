package com.example.mazaady.presentation.favorites

import com.example.mazaady.domain.model.Launch

data class FavoritesState(
    val favorites: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
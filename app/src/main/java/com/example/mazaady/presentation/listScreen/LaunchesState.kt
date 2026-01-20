package com.example.mazaady.presentation.listScreen

import androidx.compose.runtime.Immutable
import com.example.mazaady.domain.model.Launch

@Immutable // Helps with recomposition optimization
data class LaunchesListState(
    val launches: List<Launch> = emptyList(),
    val favoriteLaunchIds: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null
)
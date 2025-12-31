package com.example.mazaady.presentation.listScreen

import com.example.mazaady.domain.model.Launch


data class LaunchesListState(
    val launches: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
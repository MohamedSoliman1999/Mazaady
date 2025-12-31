package com.example.mazaady.presentation.detailScreen

import com.example.mazaady.domain.model.LaunchDetail


data class LaunchDetailState(
    val launchDetail: LaunchDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
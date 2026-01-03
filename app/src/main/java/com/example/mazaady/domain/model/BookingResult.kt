package com.example.mazaady.domain.model

data class BookingResult(
    val success: Boolean,
    val message: String?,
    val launchIds: List<String>
)
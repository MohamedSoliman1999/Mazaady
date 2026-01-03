package com.example.mazaady.domain.repository

import com.example.mazaady.domain.model.BookingResult
import com.example.mazaady.domain.model.LoginResult

interface IBookingRepository {
    suspend fun login(email: String): Result<LoginResult>
    suspend fun bookTrips(launchIds: List<String>): Result<BookingResult>
    suspend fun cancelTrip(launchId: String): Result<BookingResult>
}
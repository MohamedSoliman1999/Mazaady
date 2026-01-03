package com.example.mazaady.data.remote.datasource


import com.example.mazaadytask.BookTripsMutation
import com.example.mazaadytask.CancelTripMutation
import com.example.mazaadytask.LoginMutation
import javax.inject.Inject

interface BookingRemoteDataSource {
    suspend fun login(email: String): Result<LoginMutation.Data>
    suspend fun bookTrips(launchIds: List<String>): Result<BookTripsMutation.Data>
    suspend fun cancelTrip(launchId: String): Result<CancelTripMutation.Data>
}
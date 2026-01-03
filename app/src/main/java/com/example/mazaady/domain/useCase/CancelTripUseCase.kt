package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.model.BookingResult
import com.example.mazaady.domain.repository.IBookingRepository
import javax.inject.Inject

class CancelTripUseCase @Inject constructor(
    private val repository: IBookingRepository
) {
    suspend operator fun invoke(launchId: String): Result<BookingResult> {
        return repository.cancelTrip(launchId)
    }
}
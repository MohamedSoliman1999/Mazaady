package com.example.mazaady.data.mapper

import com.example.mazaady.domain.model.BookingResult
import com.example.mazaady.domain.model.LoginResult
import com.example.mazaadytask.BookTripsMutation
import com.example.mazaadytask.CancelTripMutation
import com.example.mazaadytask.LoginMutation

object BookingMapper {

    fun toDomainModel(login: LoginMutation.Login?): LoginResult? {
        return login?.let {
            LoginResult(
                userId = it.id,
                token = it.token ?: ""
            )
        }
    }

    fun toDomainModel(bookTrips: BookTripsMutation.BookTrips): BookingResult {
        return BookingResult(
            success = bookTrips.success,
            message = bookTrips.message,
            launchIds = bookTrips.launches?.mapNotNull { it?.id } ?: emptyList()
        )
    }

    fun toDomainModel(cancelTrip: CancelTripMutation.CancelTrip): BookingResult {
        return BookingResult(
            success = cancelTrip.success,
            message = cancelTrip.message,
            launchIds = cancelTrip.launches?.mapNotNull { it?.id } ?: emptyList()
        )
    }
}
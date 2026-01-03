package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.model.LoginResult
import com.example.mazaady.domain.repository.IBookingRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: IBookingRepository
) {
    suspend operator fun invoke(email: String): Result<LoginResult> {
        return repository.login(email)
    }
}
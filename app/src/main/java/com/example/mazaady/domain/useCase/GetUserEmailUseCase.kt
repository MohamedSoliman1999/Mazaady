package com.example.mazaady.domain.useCase

import com.example.mazaady.data.local.TokenManager
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): String? {
        return tokenManager.getUserEmail()
    }
}
package com.example.mazaady.domain.useCase

import com.example.mazaady.data.local.TokenManager
import javax.inject.Inject

class CheckAuthStatusUseCase @Inject constructor(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): Boolean {
        return tokenManager.isAuthenticated()
    }
}
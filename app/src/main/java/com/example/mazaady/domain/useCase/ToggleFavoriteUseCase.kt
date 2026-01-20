package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.repository.IFavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: IFavoriteRepository
) {
    suspend operator fun invoke(launch: Launch): Result<Boolean> {
        return repository.toggleFavorite(launch)
    }
}
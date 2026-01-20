package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: IFavoriteRepository
) {
    operator fun invoke(launchId: String): Flow<Boolean> {
        return repository.isFavorite(launchId)
    }
}
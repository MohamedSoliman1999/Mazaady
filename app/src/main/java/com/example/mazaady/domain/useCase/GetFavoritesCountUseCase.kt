package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesCountUseCase @Inject constructor(
    private val repository: IFavoriteRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getFavoritesCount()
    }
}
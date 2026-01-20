package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteLaunchesUseCase @Inject constructor(
    private val repository: IFavoriteRepository
) {
    operator fun invoke(): Flow<List<Launch>> {
        return repository.getAllFavorites()
    }
}
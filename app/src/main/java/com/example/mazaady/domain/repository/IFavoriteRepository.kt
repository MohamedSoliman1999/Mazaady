package com.example.mazaady.domain.repository

import com.example.mazaady.domain.model.Launch
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun getAllFavorites(): Flow<List<Launch>>
    fun isFavorite(launchId: String): Flow<Boolean>
    suspend fun addToFavorites(launch: Launch): Result<Unit>
    suspend fun removeFromFavorites(launchId: String): Result<Unit>
    suspend fun toggleFavorite(launch: Launch): Result<Boolean>
    fun getFavoritesCount(): Flow<Int>
}
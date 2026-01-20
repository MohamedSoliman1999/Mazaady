package com.example.mazaady.data.repository

import android.util.Log
import com.example.mazaady.data.local.dao.FavoriteLaunchDao
import com.example.mazaady.data.mapper.FavoriteLaunchMapper
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLaunchDao: FavoriteLaunchDao
) : IFavoriteRepository {

    override fun getAllFavorites(): Flow<List<Launch>> {
        return favoriteLaunchDao.getAllFavorites()
            .map { entities ->
                FavoriteLaunchMapper.toDomainModelList(entities)
            }
    }

    override fun isFavorite(launchId: String): Flow<Boolean> {
        return favoriteLaunchDao.isFavorite(launchId)
    }

    override suspend fun addToFavorites(launch: Launch): Result<Unit> {
        return try {
            val entity = FavoriteLaunchMapper.toEntity(launch)
            favoriteLaunchDao.insertFavorite(entity)
            Log.d("FavoriteRepository", "Added to favorites: ${launch.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to add favorite", e)
            Result.failure(e)
        }
    }

    override suspend fun removeFromFavorites(launchId: String): Result<Unit> {
        return try {
            favoriteLaunchDao.deleteFavoriteById(launchId)
            Log.d("FavoriteRepository", "Removed from favorites: $launchId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to remove favorite", e)
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(launch: Launch): Result<Boolean> {
        return try {
            val isFav = favoriteLaunchDao.getFavoriteById(launch.id) != null

            if (isFav) {
                favoriteLaunchDao.deleteFavoriteById(launch.id)
                Log.d("FavoriteRepository", "Toggled OFF: ${launch.id}")
                Result.success(false)
            } else {
                val entity = FavoriteLaunchMapper.toEntity(launch)
                favoriteLaunchDao.insertFavorite(entity)
                Log.d("FavoriteRepository", "Toggled ON: ${launch.id}")
                Result.success(true)
            }
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to toggle favorite", e)
            Result.failure(e)
        }
    }

    override fun getFavoritesCount(): Flow<Int> {
        return favoriteLaunchDao.getFavoritesCount()
    }
}
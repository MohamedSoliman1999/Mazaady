package com.example.mazaady.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mazaady.data.local.entity.FavoriteLaunchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLaunchDao {

    /**
     * Get all favorite launches ordered by added date
     */
    @Query("SELECT * FROM favorite_launches ORDER BY added_at DESC")
    fun getAllFavorites(): Flow<List<FavoriteLaunchEntity>>

    /**
     * Get favorite launch by ID
     */
    @Query("SELECT * FROM favorite_launches WHERE launch_id = :launchId")
    suspend fun getFavoriteById(launchId: String): FavoriteLaunchEntity?

    /**
     * Check if launch is favorite
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_launches WHERE launch_id = :launchId)")
    fun isFavorite(launchId: String): Flow<Boolean>

    /**
     * Insert favorite launch
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(launch: FavoriteLaunchEntity)

    /**
     * Delete favorite launch
     */
    @Delete
    suspend fun deleteFavorite(launch: FavoriteLaunchEntity)

    /**
     * Delete favorite by ID
     */
    @Query("DELETE FROM favorite_launches WHERE launch_id = :launchId")
    suspend fun deleteFavoriteById(launchId: String)

    /**
     * Delete all favorites
     */
    @Query("DELETE FROM favorite_launches")
    suspend fun deleteAllFavorites()

    /**
     * Get favorites count
     */
    @Query("SELECT COUNT(*) FROM favorite_launches")
    fun getFavoritesCount(): Flow<Int>
}
package com.example.mazaady.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mazaady.data.local.dao.FavoriteLaunchDao
import com.example.mazaady.data.local.entity.FavoriteLaunchEntity

@Database(
    entities = [FavoriteLaunchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LaunchesDatabase : RoomDatabase() {
    abstract fun favoriteLaunchDao(): FavoriteLaunchDao

    companion object {
        const val DATABASE_NAME = "launches_database"
    }
}
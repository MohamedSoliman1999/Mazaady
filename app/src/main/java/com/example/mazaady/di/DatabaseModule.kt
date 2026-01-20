package com.example.mazaady.di

import android.content.Context
import androidx.room.Room
import com.example.mazaady.data.local.dao.FavoriteLaunchDao
import com.example.mazaady.data.local.database.LaunchesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLaunchesDatabase(
        @ApplicationContext context: Context
    ): LaunchesDatabase {
        return Room.databaseBuilder(
            context,
            LaunchesDatabase::class.java,
            LaunchesDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteLaunchDao(
        database: LaunchesDatabase
    ): FavoriteLaunchDao {
        return database.favoriteLaunchDao()
    }
}

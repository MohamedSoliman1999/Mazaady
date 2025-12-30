package com.example.mazaady.di

import com.example.mazaady.data.repository.LaunchRepository
import com.example.mazaady.domain.repository.ILaunchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLaunchRepository(
        impl: LaunchRepository
    ): ILaunchRepository
}
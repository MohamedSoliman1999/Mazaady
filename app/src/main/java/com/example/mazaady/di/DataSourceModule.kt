package com.example.mazaady.di

import com.example.mazaady.data.remote.datasource.LaunchRemoteDataSource
import com.example.mazaady.data.remote.datasource.LaunchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLaunchRemoteDataSource(
        impl: LaunchRemoteDataSourceImpl
    ): LaunchRemoteDataSource
}
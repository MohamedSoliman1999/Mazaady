package com.example.mazaady.di

import com.example.mazaady.data.remote.datasource.BookingRemoteDataSource
import com.example.mazaady.data.remote.datasource.BookingRemoteDataSourceImpl
import com.example.mazaady.data.repository.BookingRepository
import com.example.mazaady.domain.repository.IBookingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookingDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookingRemoteDataSource(
        impl: BookingRemoteDataSourceImpl
    ): BookingRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BookingRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookingRepository(
        impl: BookingRepository
    ): IBookingRepository
}
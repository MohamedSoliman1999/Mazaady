package com.example.mazaady.di

import com.apollographql.apollo.ApolloClient
import com.example.mazaady.data.local.TokenManager
import com.example.mazaady.data.remote.apollo.ApolloClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApolloClient(
        apolloClientProvider: ApolloClientProvider
    ): ApolloClient {
        return apolloClientProvider.apolloClient
    }

    @Provides
    @Singleton
    fun provideTokenManager(): TokenManager {
        return TokenManager()
    }
}
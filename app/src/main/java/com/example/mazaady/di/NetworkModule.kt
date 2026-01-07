package com.example.mazaady.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.example.mazaady.data.local.TokenManager
import com.example.mazaady.data.remote.apollo.ApolloClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideApolloClient(
        apolloClientProvider: ApolloClientProvider
    ): ApolloClient {
        return apolloClientProvider.apolloClient
    }
}
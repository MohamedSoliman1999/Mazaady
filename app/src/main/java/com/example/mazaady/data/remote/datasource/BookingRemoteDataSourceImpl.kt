package com.example.mazaady.data.remote.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.example.mazaadytask.BookTripsMutation
import com.example.mazaadytask.CancelTripMutation
import com.example.mazaadytask.LoginMutation
import javax.inject.Inject

class BookingRemoteDataSourceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : BookingRemoteDataSource {

    override suspend fun login(email: String): Result<LoginMutation.Data> {
        return try {
            val response = apolloClient.mutation(LoginMutation(email)).execute()

            if (response.hasErrors()) {
                Result.failure(
                    Exception(response.errors?.firstOrNull()?.message ?: "Login failed")
                )
            } else {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data received"))
            }
        } catch (e: ApolloException) {
            Result.failure(e)
        }
    }

    override suspend fun bookTrips(launchIds: List<String>): Result<BookTripsMutation.Data> {
        return try {
            val response = apolloClient.mutation(BookTripsMutation(launchIds)).execute()

            if (response.hasErrors()) {
                Result.failure(
                    Exception(response.errors?.firstOrNull()?.message ?: "Booking failed")
                )
            } else {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data received"))
            }
        } catch (e: ApolloException) {
            Result.failure(e)
        }
    }

    override suspend fun cancelTrip(launchId: String): Result<CancelTripMutation.Data> {
        return try {
            val response = apolloClient.mutation(CancelTripMutation(launchId)).execute()

            if (response.hasErrors()) {
                Result.failure(
                    Exception(response.errors?.firstOrNull()?.message ?: "Cancellation failed")
                )
            } else {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data received"))
            }
        } catch (e: ApolloException) {
            Result.failure(e)
        }
    }
}
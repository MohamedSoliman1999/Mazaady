package com.example.mazaady.data.remote.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.example.mazaadytask.GetLaunchDetailQuery
import com.example.mazaadytask.GetLaunchesQuery
import javax.inject.Inject
class LaunchRemoteDataSourceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : LaunchRemoteDataSource {

    override suspend fun getLaunches(): Result<GetLaunchesQuery.Data> {
        return try {
            val response = apolloClient.query(GetLaunchesQuery()).execute()

            if (response.hasErrors()) {
                Result.failure(
                    Exception(response.errors?.firstOrNull()?.message ?: "Unknown GraphQL error")
                )
            } else {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data received"))
            }
        } catch (e: ApolloException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLaunchDetail(id: String): Result<GetLaunchDetailQuery.Data> {
        return try {
            val response = apolloClient.query(GetLaunchDetailQuery(id)).execute()

            if (response.hasErrors()) {
                Result.failure(
                    Exception(response.errors?.firstOrNull()?.message ?: "Unknown GraphQL error")
                )
            } else {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data received"))
            }
        } catch (e: ApolloException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
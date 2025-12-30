package com.example.mazaady.data.remote.datasource

import com.example.mazaadytask.GetLaunchDetailQuery
import com.example.mazaadytask.GetLaunchesQuery


interface LaunchRemoteDataSource {
    suspend fun getLaunches(): Result<GetLaunchesQuery.Data>
    suspend fun getLaunchDetail(id: String): Result<GetLaunchDetailQuery.Data>
}
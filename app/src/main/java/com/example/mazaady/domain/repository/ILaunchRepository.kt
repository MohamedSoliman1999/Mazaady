package com.example.mazaady.domain.repository

import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.model.LaunchDetail

interface ILaunchRepository {
    suspend fun getLaunches(): Result<List<Launch>>
    suspend fun getLaunchDetail(id: String): Result<LaunchDetail>
}
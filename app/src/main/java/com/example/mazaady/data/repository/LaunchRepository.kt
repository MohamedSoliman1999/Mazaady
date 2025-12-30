package com.example.mazaady.data.repository

import com.example.mazaady.data.mapper.LaunchMapper
import com.example.mazaady.data.remote.datasource.LaunchRemoteDataSource
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.model.LaunchDetail
import com.example.mazaady.domain.repository.ILaunchRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.mapCatching

@Singleton
class LaunchRepository @Inject constructor(
    private val remoteDataSource: LaunchRemoteDataSource
) : ILaunchRepository {

    override suspend fun getLaunches(): Result<List<Launch>> {
        return remoteDataSource.getLaunches()
            .mapCatching { data ->
                data.launches.launches.mapNotNull { launch ->
                    launch?.let { LaunchMapper.toDomainModel(it) }
                }
            }
    }

    override suspend fun getLaunchDetail(id: String): Result<LaunchDetail> {
        return remoteDataSource.getLaunchDetail(id)
            .mapCatching { data ->
                data.launch?.let { launch ->
                    LaunchMapper.toDomainModel(launch)
                } ?: throw Exception("Launch not found")
            }
    }
}
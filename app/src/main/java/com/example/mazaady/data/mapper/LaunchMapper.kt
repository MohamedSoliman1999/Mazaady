package com.example.mazaady.data.mapper

import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.model.LaunchDetail
import com.example.mazaadytask.GetLaunchDetailQuery
import com.example.mazaadytask.GetLaunchesQuery


object LaunchMapper {

    fun toDomainModel(launch: GetLaunchesQuery.Launch): Launch {
        return Launch(
            id = launch.id,
            site = launch.site,
            missionName = launch.mission?.name,
            missionPatch = launch.mission?.missionPatch,
            rocketName = launch.rocket?.name,
            rocketType = launch.rocket?.type
        )
    }

    fun toDomainModel(launch: GetLaunchDetailQuery.Launch): LaunchDetail {
        return LaunchDetail(
            id = launch.id,
            site = launch.site,
            missionName = launch.mission?.name,
            missionPatch = launch.mission?.missionPatch,
            rocketId = launch.rocket?.id,
            rocketName = launch.rocket?.name,
            rocketType = launch.rocket?.type,
            isBooked = launch.isBooked
        )
    }
}

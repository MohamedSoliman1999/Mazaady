package com.example.mazaady.data.mapper

import com.example.mazaady.data.local.entity.FavoriteLaunchEntity
import com.example.mazaady.domain.model.Launch


object FavoriteLaunchMapper {

    fun toEntity(launch: Launch): FavoriteLaunchEntity {
        return FavoriteLaunchEntity(
            id = launch.id,
            site = launch.site,
            missionName = launch.missionName,
            missionPatch = launch.missionPatch,
            rocketName = launch.rocketName,
            rocketType = launch.rocketType,
            isFavorite = true,
            addedAt = System.currentTimeMillis()
        )
    }

    fun toDomainModel(entity: FavoriteLaunchEntity): Launch {
        return Launch(
            id = entity.id,
            site = entity.site,
            missionName = entity.missionName,
            missionPatch = entity.missionPatch,
            rocketName = entity.rocketName,
            rocketType = entity.rocketType
        )
    }

    fun toDomainModelList(entities: List<FavoriteLaunchEntity>): List<Launch> {
        return entities.map { toDomainModel(it) }
    }
}
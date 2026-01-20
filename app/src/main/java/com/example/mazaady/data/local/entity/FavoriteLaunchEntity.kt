package com.example.mazaady.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_launches")
data class FavoriteLaunchEntity(
    @PrimaryKey
    @ColumnInfo(name = "launch_id")
    val id: String,

    @ColumnInfo(name = "site")
    val site: String?,

    @ColumnInfo(name = "mission_name")
    val missionName: String?,

    @ColumnInfo(name = "mission_patch")
    val missionPatch: String?,

    @ColumnInfo(name = "rocket_name")
    val rocketName: String?,

    @ColumnInfo(name = "rocket_type")
    val rocketType: String?,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = true,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis()
)
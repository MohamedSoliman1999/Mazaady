package com.example.mazaady.domain.model

data class Launch(
    val id: String,
    val site: String?,
    val missionName: String?,
    val missionPatch: String?,
    val rocketName: String?,
    val rocketType: String?
)
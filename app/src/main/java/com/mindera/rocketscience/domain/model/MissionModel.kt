package com.mindera.rocketscience.domain.model

data class MissionModel(
    val mission_name: String,
    val launch_date_utc: String,
    val rocket: Rocket,
    val links: Links,
    val launch_success: String,
    val launch_year: String
)

data class Links(
    val mission_patch: String,
    val mission_patch_small: String,
    val wikipedia: String,
    val video_link: String
)

data class Rocket(
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String
)



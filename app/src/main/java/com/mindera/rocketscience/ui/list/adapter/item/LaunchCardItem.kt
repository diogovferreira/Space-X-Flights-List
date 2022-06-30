package com.mindera.rocketscience.ui.list.adapter.item

data class LaunchCardItem(
    val image: String?,
    val missionName: String,
    val dateTime: String,
    val rocketName: String,
    val days: String,
    val launchSuccess: Boolean,
    val wikipediaLink: String?,
    val youtubeLink: String?,
    val launchYear: Int
)



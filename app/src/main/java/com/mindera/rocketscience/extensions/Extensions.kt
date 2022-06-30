package com.mindera.rocketscience.extensions

import com.mindera.rocketscience.domain.model.CompanyInfoModel
import com.mindera.rocketscience.domain.model.MissionModel
import com.mindera.rocketscience.ui.list.adapter.item.LaunchCardItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


fun List<MissionModel>.convertToLaunchCardModel(): List<LaunchCardItem> {
    var listLaunchCardItems = mutableListOf<LaunchCardItem>()

    this.forEach { mission ->
        listLaunchCardItems.add(
            LaunchCardItem(
                mission.links.mission_patch_small,
                mission.mission_name,
                mission.launch_date_utc.getFormatedDate(),
                mission.rocket.rocket_name + "/" + mission.rocket.rocket_type,
                mission.launch_date_utc.getDaysBetween(),
                mission.launch_success.toBoolean(),
                mission.links.wikipedia,
                mission.links.video_link,
                mission.launch_year.toInt()
            )
        )
    }
    return listLaunchCardItems
}


fun String.getFormatedDate(): String {

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss", Locale.ENGLISH)

    val date = LocalDateTime.parse(this, inputFormatter)

    return outputFormatter.format(date)
}


fun String.getDaysBetween(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    val today = LocalDateTime.now()
    val missionDate = LocalDateTime.parse(this, inputFormatter)

    return ChronoUnit.DAYS.between(today, missionDate).toString()
}

fun String.replaceTitle(companyInfo: CompanyInfoModel): String {

    val companyInfoFinal = replace("{companyName}", companyInfo.name)
        .replace("{founderName}", companyInfo.founder)
        .replace("{year}", companyInfo.founded)
        .replace("{employees}", companyInfo.employees)
        .replace("{launch_sites}", companyInfo.launch_sites)
        .replace("{valuation}", companyInfo.valuation)


    return companyInfoFinal

}
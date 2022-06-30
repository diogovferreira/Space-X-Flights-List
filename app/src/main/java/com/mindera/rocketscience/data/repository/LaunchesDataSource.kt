package com.mindera.rocketscience.data.repository

import com.mindera.rocketscience.data.api.ApiServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaunchesDataSource @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getLaunches() = apiServices.getLaunches()

    suspend fun getCompanyInfo() = apiServices.getCompanyInfo()

}
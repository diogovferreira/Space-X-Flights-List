package com.mindera.rocketscience.data.repository

import com.mindera.rocketscience.data.api.ApiResponse
import com.mindera.rocketscience.domain.model.CompanyInfoModel
import com.mindera.rocketscience.domain.model.MissionModel
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    fun getLaunches(): Flow<ApiResponse.ApiResult<List<MissionModel>>>
    fun getCompanyInfo(): Flow<ApiResponse.ApiResult<CompanyInfoModel>>
}
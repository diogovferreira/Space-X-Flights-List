package com.mindera.rocketscience.data.repository

import com.mindera.rocketscience.data.api.ApiResponse
import com.mindera.rocketscience.domain.model.CompanyInfoModel
import com.mindera.rocketscience.domain.model.MissionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(private val launchesDataSource: LaunchesDataSource) :
    ApiResponse(),LaunchesRepository {

    override fun getLaunches() : Flow<ApiResult<List<MissionModel>>>{
        return flow {
            emit(safeApiCall {
                launchesDataSource.getLaunches()
            })
        }.flowOn(Dispatchers.IO)
    }

    override fun getCompanyInfo(): Flow<ApiResult<CompanyInfoModel>> {
        return flow {
            emit(safeApiCall {
                launchesDataSource.getCompanyInfo()
            })
        }.flowOn(Dispatchers.IO)
    }


}
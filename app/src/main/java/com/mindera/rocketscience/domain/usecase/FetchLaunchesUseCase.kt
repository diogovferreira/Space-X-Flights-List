package com.mindera.rocketscience.domain.usecase

import com.mindera.rocketscience.data.api.ApiResponse
import com.mindera.rocketscience.data.repository.LaunchesRepository
import com.mindera.rocketscience.domain.model.MissionModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FetchLaunchesUseCase @Inject constructor(private val repository: LaunchesRepository) {

    suspend operator fun invoke(): ApiResponse.ApiResult<List<MissionModel>>? {
        val lauches = repository.getLaunches()
        var launchesReponse: ApiResponse.ApiResult<List<MissionModel>>? = null
        lauches.collect {

            launchesReponse = it

        }
        return launchesReponse
    }

}
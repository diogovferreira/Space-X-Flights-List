package com.mindera.rocketscience.domain.usecase

import com.mindera.rocketscience.data.api.ApiResponse
import com.mindera.rocketscience.data.repository.LaunchesRepository
import com.mindera.rocketscience.domain.model.CompanyInfoModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FetchCompanyInfoUseCase @Inject constructor(private val repository: LaunchesRepository) {

    suspend operator fun invoke(): ApiResponse.ApiResult<CompanyInfoModel>? {
        val companyInfo = repository.getCompanyInfo()
        var companyInfoResponse: ApiResponse.ApiResult<CompanyInfoModel>? = null
        companyInfo.collect {
            companyInfoResponse = it
        }
        return companyInfoResponse
    }
}
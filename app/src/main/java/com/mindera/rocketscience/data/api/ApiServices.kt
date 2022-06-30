package com.mindera.rocketscience.data.api

import com.mindera.rocketscience.data.Constants
import com.mindera.rocketscience.domain.model.CompanyInfoModel
import com.mindera.rocketscience.domain.model.MissionModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET(Constants.GET_LAUNCHES)
    suspend fun getLaunches(): Response<List<MissionModel>>

    @GET(Constants.COMPANY_INFO)
    suspend fun getCompanyInfo(): Response<CompanyInfoModel>

}
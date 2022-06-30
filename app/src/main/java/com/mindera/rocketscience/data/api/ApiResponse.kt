package com.mindera.rocketscience.data.api

import retrofit2.Response

abstract class ApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ApiResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): ApiResult<T> =
        ApiResult.Error("Api failed $errorMessage")


    sealed class ApiResult<T>(
        val data: T? = null,
        val message: String? = null
    ) {
        class Success<T>(data: T) : ApiResult<T>(data)
        class Error<T>(message: String, data: T? = null) : ApiResult<T>(data, message)
    }
}
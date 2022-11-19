package com.block.stocks.network

import com.block.stocks.models.ApiError
import com.block.stocks.models.DataFetchResult
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

internal object NetworkUtils {
    suspend fun <T> Retrofit.getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): DataFetchResult<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return DataFetchResult.success(result.body())
            } else {
                val errorResponse = parseError(result, this)
                DataFetchResult.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Exception) {
            DataFetchResult.error("Unknown Error", null)
        }
    }

    private fun parseError(response: Response<*>, retrofit: Retrofit): ApiError? {
        return try {
            retrofit
                .responseBodyConverter<ApiError>(ApiError::class.java, arrayOfNulls(0))
                .convert(response.errorBody()!!)
        } catch (e: IOException) {
            ApiError()
        }
    }
}
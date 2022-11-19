package com.block.stocks.data.remote

import com.block.stocks.models.CashStocksApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CashStocksApiService {
    @GET("portfolio.json")
    suspend fun getSchoolData(@Query("dbn") dbn: String?): Response<CashStocksApiData>
}
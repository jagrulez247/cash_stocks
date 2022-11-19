package com.block.stocks.data.remote

import com.block.stocks.models.CashStocksApiData
import retrofit2.Response
import retrofit2.http.GET

interface CashStocksApiService {
    @GET("portfolio.json")
    suspend fun getPortfolio(): Response<CashStocksApiData>
}
package com.block.stocks.data.remote

import com.block.stocks.data.mappers.CashStocksDataMapper
import com.block.stocks.models.CashStockData
import com.block.stocks.models.DataFetchResult
import com.block.stocks.network.NetworkUtils.getResponse
import retrofit2.Retrofit
import javax.inject.Inject

class CashStocksRemoteDataSource@Inject constructor(
    private val retrofit: Retrofit,
    private val apiService: CashStocksApiService,
    private val mapper: CashStocksDataMapper
) {

    suspend fun fetchPortfolio(): DataFetchResult<List<CashStockData>> {
        val apiResponse = retrofit.getResponse(
            request = { apiService.getPortfolio() },
            defaultErrorMessage = "Error fetching portfolio"
        )
        return when (apiResponse.status == DataFetchResult.Status.SUCCESS) {
            true -> apiResponse
                .data
                ?.stocks
                ?.takeIf { it.isNotEmpty() }
                ?.map { mapper.mapToCashStockData(it) }
                ?.let { DataFetchResult.success(it) }
                ?: DataFetchResult.error("Unknown Error")
            else -> DataFetchResult.error(apiResponse.message ?: "", apiResponse.error)
        }
    }
}
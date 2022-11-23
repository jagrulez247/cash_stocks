package com.block.stocks.data.repository

import com.block.stocks.data.local.CashStocksDataDao
import com.block.stocks.data.remote.CashStocksRemoteDataSource
import com.block.stocks.models.CashStockData
import com.block.stocks.models.DataFetchResult
import com.block.stocks.utils.LocalCoroutineDispatcher.io
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CashStocksRepository @Inject constructor(
    private val remoteDataSource: CashStocksRemoteDataSource,
    private val stocksDao: CashStocksDataDao
) {
    private val cashStocks: CopyOnWriteArrayList<CashStockData> = CopyOnWriteArrayList()

    fun getPortfolioFromCache(): List<CashStockData> = cashStocks

    suspend fun fetchPortfolio(): Flow<DataFetchResult<List<CashStockData>>?> {
        return flow {
            emit(stocksDao.fetchPortfolioCached(backup = cashStocks))
            emit(DataFetchResult.loading())
            emit(remoteDataSource.fetchPortfolio()
                .apply {
                    if (!data.isNullOrEmpty()) {
                        stocksDao.refreshPortfolio(data)
                        cashStocks.clear()
                        cashStocks.addAll(data)
                    }
                }
            )
        }.flowOn(io())
    }
}
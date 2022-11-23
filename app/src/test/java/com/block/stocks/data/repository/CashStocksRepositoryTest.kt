package com.block.stocks.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.block.stocks.CoroutineTestRule
import com.block.stocks.data.local.CashStocksDataDao
import com.block.stocks.data.remote.CashStocksRemoteDataSource
import com.block.stocks.models.CashStockData
import com.block.stocks.models.DataFetchResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CashStocksRepositoryTest {

    companion object {
        private val MOCK_API_STOCK_LIST = mutableListOf<CashStockData>().apply {
            add(CashStockData(ticker = "BRKA", name = "Berkshire Hathaway Inc"))
            add(CashStockData(ticker = "GSPC", name = "S & P 500"))
            add(CashStockData(ticker = "TRUNK", name = "Trunk Club"))
        }

        private val MOCK_CACHED_STOCK_LIST = mutableListOf<CashStockData>().apply {
            add(CashStockData(ticker = "RUN", name = "Run"))
            add(CashStockData(ticker = "BAC", name = "Bank of America Corporation"))
            add(CashStockData(ticker = "TWTR", name = "Twitter Inc"))
        }
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    private lateinit var remoteDataSource: CashStocksRemoteDataSource

    @RelaxedMockK
    private lateinit var stocksDao: CashStocksDataDao

    private lateinit var repository: CashStocksRepository

    private val cashStocks: MutableList<CashStockData> = mutableListOf()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = CashStocksRepository(remoteDataSource, stocksDao)
        every {
            stocksDao.fetchPortfolioCached(mutableListOf())
        } returns DataFetchResult.success(mutableListOf())
        coEvery {
            remoteDataSource.fetchPortfolio()
        } returns DataFetchResult.success(mutableListOf())
    }

    @Test
    fun shouldGetFromDbIfAvailable() {
        givenCashStocksFromDb()
        whenFetchPortfolio()
        thenCheckStocksAreCollected(MOCK_CACHED_STOCK_LIST)
    }

    @Test
    fun shouldSaveSatDataToCacheWhenFromRemote() {
        givenCashStocksFromRemote()
        whenFetchPortfolio()
        thenCheckStocksAreCollected(MOCK_API_STOCK_LIST)
        thenStocksSavedToCache(MOCK_API_STOCK_LIST)
    }

    private fun givenCashStocksFromDb() {
        every { stocksDao.getAllStockData() } returns MOCK_CACHED_STOCK_LIST
        every {
            stocksDao.fetchPortfolioCached(mutableListOf())
        } returns DataFetchResult.success(MOCK_CACHED_STOCK_LIST)
    }

    private fun givenCashStocksFromRemote() {
        coEvery {
            remoteDataSource.fetchPortfolio()
        } returns DataFetchResult.success(MOCK_API_STOCK_LIST)
    }

    private fun whenFetchPortfolio() {
        runTest {
            repository.fetchPortfolio().collect {
                if (it?.data?.isNotEmpty() == true) {
                    cashStocks.clear()
                    cashStocks.addAll(it.data ?: mutableListOf())
                }
            }
        }
    }

    private fun thenCheckStocksAreCollected(expectedList: List<CashStockData>) {
        assertEquals(expectedList.size, cashStocks.size)
        expectedList.forEachIndexed { index, stockData ->
            assertEquals(stockData.ticker, cashStocks[index].ticker)
        }
    }

    private fun thenStocksSavedToCache(expectedList: List<CashStockData>) {
        val actualList = repository.getPortfolioFromCache()
        assertEquals(expectedList.size, actualList.size)
        expectedList.forEachIndexed { index, stockData ->
            assertEquals(stockData.ticker, actualList[index].ticker)
        }
    }
}
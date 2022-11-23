package com.block.stocks.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.block.stocks.CoroutineTestRule
import com.block.stocks.data.repository.CashStocksRepository
import com.block.stocks.models.CashStockData
import com.block.stocks.models.DataFetchResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    companion object {
        private val MOCK_STOCK_LIST = mutableListOf<CashStockData>().apply {
            add(CashStockData(ticker = "BRKA", name = "Berkshire Hathaway Inc"))
            add(CashStockData(ticker = "GSPC", name = "S & P 500"))
            add(CashStockData(ticker = "TRUNK", name = "Trunk Club"))
        }
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    private lateinit var repository: CashStocksRepository

    @RelaxedMockK
    private lateinit var stateObserver: Observer<MainUiStates>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(repository)
        viewModel.uiState().observeForever(stateObserver)
    }

    @After
    fun tearDown() {
        if (::stateObserver.isInitialized) viewModel.uiState().removeObserver(stateObserver)
    }

    @Test
    fun shouldHandleLoadingScenario() {
        givenStocksFetchLoading()
        whenOnViewCreated()
        thenLoadingStateIsSent()
        thenNoDataIsNotSent()
        thenLoadedStateIsNotSent()
    }

    @Test
    fun shouldHandleErrorScenario() {
        givenStocksFetchError()
        whenOnViewCreated()
        thenNoDataIsSent()
        thenLoadingStateIsNotSent()
        thenLoadedStateIsNotSent()
    }

    @Test
    fun shouldHandleSuccessScenario() {
        givenStocksFetchSuccess()
        whenOnViewCreated()
        thenLoadedStateIsSent()
        thenNoDataIsNotSent()
        thenLoadingStateIsNotSent()
    }

    private fun givenStocksFetchSuccess() {
        coEvery {
            repository.fetchPortfolio()
        } returns flowOf(DataFetchResult.success(MOCK_STOCK_LIST))
    }

    private fun givenStocksFetchError() {
        coEvery {
            repository.fetchPortfolio()
        } returns flowOf(DataFetchResult.error("Error message"))
    }

    private fun givenStocksFetchLoading() {
        coEvery {
            repository.fetchPortfolio()
        } returns flowOf(DataFetchResult.loading())
    }

    private fun whenOnViewCreated() {
        viewModel.onViewCreated()
    }

    private fun thenLoadingStateIsSent() {
        verify { stateObserver.onChanged(MainUiStates.ShowProgress) }
    }

    private fun thenNoDataIsSent() {
        verify { stateObserver.onChanged(MainUiStates.NoData) }
    }

    private fun thenNoDataIsNotSent() {
        verify(exactly = 0) { stateObserver.onChanged(MainUiStates.NoData) }
    }

    private fun thenLoadingStateIsNotSent() {
        verify(exactly = 0) { stateObserver.onChanged(MainUiStates.ShowProgress) }
    }

    private fun thenLoadedStateIsSent() {
        val slots = mutableListOf<MainUiStates>()
        verify { stateObserver.onChanged(capture(slots)) }

        val updateStocksState = slots
            .find { it is MainUiStates.UpdateStocks }
            ?.let { it as MainUiStates.UpdateStocks }
        val actualList = updateStocksState?.stocks ?: mutableListOf()
        val expectedList = MOCK_STOCK_LIST
        expectedList.forEachIndexed { index, cashStockData ->
            val actualData = actualList[index]
            assertEquals(cashStockData.ticker, actualData.ticker)
        }
    }

    private fun thenLoadedStateIsNotSent() {
        val slots = mutableListOf<MainUiStates>()
        verify { stateObserver.onChanged(capture(slots)) }

        val updateStocksState = slots
            .find { it is MainUiStates.UpdateStocks }
            ?.let { it as MainUiStates.UpdateStocks }
        assertNull(updateStocksState)
    }
}
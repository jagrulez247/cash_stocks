package com.block.stocks.ui.main.adapter

import android.content.Context
import com.block.stocks.R
import com.block.stocks.models.CashStockData
import com.block.stocks.ui.main.adaper.CashStockItemDataProvider
import com.block.stocks.ui.main.adaper.CashStockViewHolderData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CashStockItemDataProviderTest {

    companion object {
        private const val EMPTY_DATA_BASE = "___"
        private const val SHARE_COUNT_BASE = "(%1\$d shares)"
        private const val PRICE_PER_STOCK_BASE = "(%1\$s)"
        private const val LAST_UPDATED_BASE = "Last Updated At: %1\$s"
        private val MOCK_STOCK_DATA = CashStockData(
            ticker = "GSPC",
            quantity = 25,
            name = "S&P 500",
            current_price_cents = 318157,
            current_price_timestamp = 1636657688,
            currency = "USD"
        )
    }

    @RelaxedMockK
    private lateinit var appContext: Context

    private lateinit var dataProvider: CashStockItemDataProvider

    private lateinit var viewHolderData: CashStockViewHolderData

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { appContext.getString(R.string.empty_data) } returns EMPTY_DATA_BASE
        every { appContext.getString(R.string.share_count) } returns SHARE_COUNT_BASE
        every { appContext.getString(R.string.price_per_stock) } returns PRICE_PER_STOCK_BASE
        every { appContext.getString(R.string.last_updated_time) } returns LAST_UPDATED_BASE
        dataProvider = CashStockItemDataProvider(appContext)
    }

    @Test
    fun shouldGiveProperViewHolderData() {
        whenGetStockItemViewHolderData()
        thenCheckViewHolderDataIsProperlyMapped()
    }

    private fun whenGetStockItemViewHolderData() {
        viewHolderData = dataProvider.getStockItemViewHolderData(MOCK_STOCK_DATA)
    }

    private fun thenCheckViewHolderDataIsProperlyMapped() {
        assertEquals(viewHolderData.ticker, "GSPC")
        assertEquals(viewHolderData.name, "S&P 500")
        assertEquals(viewHolderData.total, "$79,539.25")
        assertEquals(viewHolderData.shares, "(25 shares)")
        assertEquals(viewHolderData.pricePerStock, "($3,181.57)")
        assertEquals(viewHolderData.lastUpdated, "Last Updated At: 11 Nov 2021 11:08 AM")
    }
}
package com.block.stocks.data.mappers

import com.block.stocks.models.CashStockApiData
import com.block.stocks.models.CashStockData
import javax.inject.Inject

class CashStocksDataMapper @Inject constructor() {
    fun mapToCashStockData(apiData: CashStockApiData): CashStockData {
        return CashStockData(
            ticker = apiData.ticker ?: "",
            quantity = apiData.quantity,
            name = apiData.name,
            current_price_timestamp = apiData.current_price_timestamp,
            current_price_cents = apiData.current_price_cents,
            currency = apiData.currency
        )
    }
}
package com.block.stocks.ui.main

import com.block.stocks.models.CashStockData

sealed class MainUiStates {
    object ShowProgress: MainUiStates()
    object HideProgress: MainUiStates()
    object NoData: MainUiStates()
    class UpdateStocks(val stocks: List<CashStockData>): MainUiStates()
}
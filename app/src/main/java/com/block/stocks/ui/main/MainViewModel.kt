package com.block.stocks.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.block.stocks.data.repository.CashStocksRepository
import com.block.stocks.models.CashStockData
import com.block.stocks.utils.LocalCoroutineDispatcher.main
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CashStocksRepository
) : ViewModel() {

    private val uiState: MutableLiveData<MainUiStates> = MutableLiveData()
    internal fun uiState(): LiveData<MainUiStates> = uiState

    private val cashStocks: MutableList<CashStockData> = mutableListOf()

    fun onViewCreated() {
        viewModelScope.launch(main()) {
            repository.fetchPortfolio().collect { result ->
                val isLoading = result?.isLoadingStatus() == true
                if (isLoading && cashStocks.isEmpty()) {
                    uiState.value = MainUiStates.ShowProgress
                    return@collect
                }

                uiState.value = MainUiStates.HideProgress

                result
                    ?.data
                    ?.sortedBy { it.ticker }
                    ?.sortedByDescending {
                        (it.quantity ?: 0) * (it.current_price_cents ?: 0)
                    }?.takeIf { it.isNotEmpty() }
                    ?.apply {
                        cashStocks.clear()
                        cashStocks.addAll(this)
                    }

                updateUi()
            }
        }
    }

    private fun updateUi() {
        if (cashStocks.isEmpty()) {
            uiState.value = MainUiStates.NoData
            return
        }
        uiState.value = MainUiStates.UpdateStocks(cashStocks)
    }
}
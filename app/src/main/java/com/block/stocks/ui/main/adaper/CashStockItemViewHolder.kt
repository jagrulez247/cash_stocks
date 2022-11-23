package com.block.stocks.ui.main.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.block.stocks.databinding.ViewHolderStockItemBinding

class CashStockItemViewHolder(
    parent: ViewGroup,
    private val itemBinding: ViewHolderStockItemBinding = getStockItemBinding(parent)
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(stockData: CashStockViewHolderData) = itemBinding.apply {
        cashStockTicker.text = stockData.ticker
        cashStockShares.text = stockData.shares
        cashStockPrice.text = stockData.total
        cashStockName.text = stockData.name
        cashStockPerStockPrice.text = stockData.pricePerStock
        cashStockTimestamp.text = stockData.lastUpdated
    }

    companion object {
        private fun getStockItemBinding(parent: ViewGroup) =
            ViewHolderStockItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
    }
}
package com.block.stocks.ui.main.adaper

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.block.stocks.models.CashStockData

class CashStockAdapter : RecyclerView.Adapter<CashStockItemViewHolder>() {

    private var onItemClick: (String, Int) -> Unit = { _, _ -> }
    private val cashStocks: MutableList<CashStockData> = mutableListOf()
    private lateinit var dataProvider: CashStockItemDataProvider

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashStockItemViewHolder {
        if (!::dataProvider.isInitialized) {
            dataProvider = CashStockItemDataProvider(parent.context)
        }
        return CashStockItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CashStockItemViewHolder, position: Int) {
        val stockItem = cashStocks[position]
        val viewHolderData = dataProvider.getStockItemViewHolderData(stockItem)
        holder.bind(viewHolderData)
        holder.itemView.setOnClickListener { onItemClick(stockItem.ticker, position) }
    }

    override fun getItemCount(): Int = cashStocks.size

    fun updateStocks(newItems: List<CashStockData>) {
        val diffResult = DiffUtil.calculateDiff(CashStockDiff(cashStocks, newItems))
        cashStocks.clear()
        cashStocks.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClicked(callback: (String, Int) -> Unit) {
        onItemClick = callback
    }

    fun cleanUp() {
        onItemClick = { _,_ -> }
    }

    inner class CashStockDiff(
        private val oldList: List<CashStockData>,
        private val newList: List<CashStockData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItemData = oldList[oldItemPosition]
            val newItemData = newList[newItemPosition]
            return oldItemData.ticker == newItemData.ticker
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItemData = oldList[oldItemPosition]
            val newItemData = newList[newItemPosition]
            return oldItemData == newItemData
        }
    }
}
package com.block.stocks.data.local

import androidx.room.*
import com.block.stocks.models.CashStockData
import com.block.stocks.models.DataFetchResult

@Dao
interface CashStocksDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStockData(satData: List<CashStockData>)

    @Delete
    fun deleteAllStockData(satData: List<CashStockData>)

    @Query("SELECT * FROM cashstocks order by ticker DESC")
    fun getAllStockData(): List<CashStockData>?

    fun fetchPortfolioCached(backup: MutableList<CashStockData>): DataFetchResult<List<CashStockData>>? {
        return getAllStockData()
            ?.let {
                backup.clear()
                backup.addAll(it)
                DataFetchResult.success(it)
            }
    }

    fun refreshPortfolio(allSats: List<CashStockData>?) {
        if (allSats.isNullOrEmpty()) return
        deleteAllStockData(allSats)
        insertAllStockData(allSats)
    }
}
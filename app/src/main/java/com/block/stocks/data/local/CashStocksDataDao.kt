package com.block.stocks.data.local

import androidx.room.*
import com.block.stocks.models.CashStockData

@Dao
interface CashStocksDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStockData(satData: List<CashStockData>)

    @Delete
    fun deleteAllStockData(satData: List<CashStockData>)

    @Query("SELECT * FROM cashstocks order by ticker DESC")
    fun getAllStockData(): List<CashStockData>?
}
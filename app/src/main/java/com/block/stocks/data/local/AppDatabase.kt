package com.block.stocks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.block.stocks.BuildConfig
import com.block.stocks.models.CashStockData

@Database(
    entities = [CashStockData::class],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = BuildConfig.DATABASE_EXPORT_SCHEMA
)

@TypeConverters(CashStocksDataConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cashStocksDataDao(): CashStocksDataDao
}
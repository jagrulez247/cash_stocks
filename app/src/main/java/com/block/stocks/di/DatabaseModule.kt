package com.block.stocks.di

import android.content.Context
import androidx.room.Room
import com.block.stocks.data.local.AppDatabase
import com.block.stocks.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCashStocksDao(db: AppDatabase) = db.cashStocksDataDao()
}
package com.block.stocks.models

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CashStocksApiData(
    @Json(name = "stocks") val stocks: List<CashStockApiData>?
)

@Keep
@JsonClass(generateAdapter = true)
data class CashStockApiData(
    @Json(name = "currency") val currency: String?,
    @Json(name = "current_price_cents") val current_price_cents: Int?,
    @Json(name = "current_price_timestamp") val current_price_timestamp: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "quantity") val quantity: Int?,
    @Json(name = "ticker") val ticker: String?
)

@Entity(tableName = "cashstocks")
data class CashStockData(
    @NonNull
    @PrimaryKey
    val ticker: String,
    val quantity: Int? = null,
    val name: String? = null,
    val current_price_timestamp: Int? = null,
    val current_price_cents: Int? = null,
    val currency: String? = null
)

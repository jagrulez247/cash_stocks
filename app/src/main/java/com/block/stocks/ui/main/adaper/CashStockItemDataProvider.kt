package com.block.stocks.ui.main.adaper

import android.content.Context
import com.block.stocks.R
import com.block.stocks.models.CashStockData
import com.block.stocks.utils.PriceUtils.formatPriceFromCents
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToLong

class CashStockItemDataProvider @Inject constructor(appContext: Context) {

    private val emptyData = appContext.getString(R.string.empty_data)
    private val shareCountBase = appContext.getString(R.string.share_count)
    private val pricePerStockBase = appContext.getString(R.string.price_per_stock)
    private val clientDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm aa", Locale.US)
    private val lastUpdatedDateString = appContext.getString(R.string.last_updated_time)

    fun getStockItemViewHolderData(itemData: CashStockData): CashStockViewHolderData {
        return CashStockViewHolderData(
            ticker = itemData.ticker,
            shares = getShareCount(itemData),
            name = itemData.name ?: emptyData,
            total = getTotalPrice(itemData),
            pricePerStock = getPerStockPrice(itemData),
            lastUpdated = getLastUpdatedAtTime(itemData)
        )
    }

    private fun getShareCount(itemData: CashStockData): String {
        val shares = itemData.quantity ?: 0
        if (shares <= 0) return ""
        return String.format(shareCountBase, shares)
    }

    private fun getTotalPrice(itemData: CashStockData): String {
        val totalCents = (itemData.current_price_cents ?: 0) * (itemData.quantity ?: 0)
        if (totalCents <= 0) return emptyData
        val formattedPrice = formatPriceFromCents(totalCents, itemData.currency)
        return formattedPrice.takeIf { it.isNotBlank() } ?: emptyData
    }

    private fun getPerStockPrice(itemData: CashStockData): String {
        return formatPriceFromCents(itemData.current_price_cents, itemData.currency)
            .takeIf { it.isNotBlank() }
            ?.let { String.format(pricePerStockBase, it) }
            ?: emptyData
    }

    private fun getLastUpdatedAtTime(itemData: CashStockData): String {
        val updatedDate = getFormattedDate(itemData.current_price_timestamp?.toLong() ?: 0L)
        if (updatedDate.isBlank()) return ""
        return String.format(
            lastUpdatedDateString,
            updatedDate
        )
    }

    private fun getFormattedDate(timeInSeconds: Long): String = try {
        if (timeInSeconds > 0) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInSeconds * 1000
            clientDateFormat.format(calendar.time) ?: ""
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}
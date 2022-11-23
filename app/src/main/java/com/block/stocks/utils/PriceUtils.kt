package com.block.stocks.utils

import java.text.NumberFormat
import java.util.*

object PriceUtils {
    private val allCurrencies: Map<String, String> = getAvailableCurrencies()
    private val defaultCode: String = "USD"

    fun formatPriceFromCents(cents: Int?, code: String? = defaultCode): String {
        if (cents == null) return ""
        val validCurrencyCode = when {
            code.isNullOrBlank() -> defaultCode
            !allCurrencies.containsKey(code) -> defaultCode
            else -> code
        }

        return try {
            val formatter = NumberFormat.getInstance()
            formatter.currency = Currency.getInstance(validCurrencyCode)
            formatter.format(cents.toDouble()/100)
        } catch (ex: Exception) {
            ""
        }
    }

    private fun getAvailableCurrencies(): Map<String, String> = Locale
        .getAvailableLocales()
        .mapNotNull { locale ->
            try {
                Pair(Currency.getInstance(locale).currencyCode, locale.displayCountry)
            } catch (e: Exception) {
                null
            }
        }.toMap()
}
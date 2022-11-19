package com.block.stocks.models

data class ApiError(
    val status_code: Int = 0,
    val status_message: String? = null
)
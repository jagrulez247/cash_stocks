package com.block.stocks.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CashStocksApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addRequestHeaders()
            .build()
        val response = chain.proceed(request)

        val requestUrl = request.url.toString()
        val responseBody = response.body?.toString() ?: ""
        val responseCode = response.code.toString()

        Log.d(
            "NycSchoolApiInterceptor",
            "Response for $requestUrl - \n$responseCode: $responseBody"
        )

        return response
    }

    private fun Request.Builder.addRequestHeaders(): Request.Builder {
        //Any additional request headers here
        return this
    }
}
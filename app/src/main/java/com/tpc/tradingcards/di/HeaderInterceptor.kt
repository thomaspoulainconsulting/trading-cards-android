package com.tpc.tradingcards.di

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val requestBuilder = originRequest.newBuilder().apply {
            addHeader("X-Api-Key", "1229e20b-13b7-43de-9c13-ee0f87c7f87c")
        }

        return chain.proceed(requestBuilder.build())
    }
}
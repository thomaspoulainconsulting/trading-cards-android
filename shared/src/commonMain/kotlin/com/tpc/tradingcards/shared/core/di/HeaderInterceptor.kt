package com.tpc.tradingcards.shared.core.di

import com.tpc.tradingcards.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val requestBuilder = originRequest.newBuilder().apply {
            addHeader("X-Api-Key", BuildConfig.API_POKEMON)
        }

        return chain.proceed(requestBuilder.build())
    }
}
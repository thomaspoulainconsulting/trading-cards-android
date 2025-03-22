package com.tpc.tradingcards.core.di

import android.content.Context
import com.tpc.tradingcards.BuildConfig
import com.tpc.tradingcards.data.service.PokemonTradingCardService
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File


private val json = Json {
    ignoreUnknownKeys = true
}

private val loggingInterceptor: HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
    }

val networkCacheInterceptor = Interceptor { chain ->
    val response = chain.proceed(chain.request())
    response.newBuilder()
        .header("Cache-Control", "public, max-age=60") // 60 secondes de cache
        .build()
}

private fun provideOkHttpCache(context: Context): Cache {
    val cacheSize = 10 * 1024 * 1024L // 10 MiB
    val cacheDir = File(context.cacheDir, "http_cache")
    return Cache(cacheDir, cacheSize)
}

private fun provideOkHttpClient(
    headersInterceptor: HeaderInterceptor,
    cache: Cache,
): OkHttpClient =
    OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(headersInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(networkCacheInterceptor)
        .build()

private fun providePokemonCardsRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.pokemontcg.io/v2/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

private fun providePokemonTradingCardService(retrofit: Retrofit): PokemonTradingCardService =
    retrofit.create(PokemonTradingCardService::class.java)

val networkModule = module {
    singleOf(::HeaderInterceptor)
    single { provideOkHttpCache(get()) }
    single { provideOkHttpClient(get(), get()) }
    single { providePokemonCardsRetrofit(get()) }
    single { providePokemonTradingCardService(get()) }
}
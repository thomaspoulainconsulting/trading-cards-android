package com.tpc.tradingcards.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tpc.tradingcards.BuildConfig
import com.tpc.tradingcards.data.service.PokemonTradingCardService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private fun provideMoshi(): Moshi =
    Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
    }

private fun provideOkHttpClient(
    headersInterceptor: HeaderInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(headersInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

private fun providePokemonCardsRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.pokemontcg.io/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

private fun providePokemonTradingCardService(retrofit: Retrofit): PokemonTradingCardService =
    retrofit.create(PokemonTradingCardService::class.java)

val networkModule = module {
    singleOf(::HeaderInterceptor)
    single { provideMoshi() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { providePokemonCardsRetrofit(get(),get()) }
    single { providePokemonTradingCardService(get()) }
}
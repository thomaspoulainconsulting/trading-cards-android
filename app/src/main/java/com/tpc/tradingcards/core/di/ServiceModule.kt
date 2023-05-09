package com.tpc.tradingcards.core.di

import com.tpc.tradingcards.data.service.PokemonTradingCardService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun providePokemonTradingCardService(retrofit: Retrofit): PokemonTradingCardService =
        retrofit.create(PokemonTradingCardService::class.java)
}
package com.tpc.tradingcards.shared.core.di

import com.tpc.tradingcards.shared.data.service.PokemonTradingCardService
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module


private val json = Json {
    ignoreUnknownKeys = true
}

private fun provideHttpClient(): HttpClient = HttpClient()

private fun providePokemonCardsRetrofit(httpClient: HttpClient): Ktorfit =
    Ktorfit.Builder().httpClient(httpClient)
        .baseUrl("https://api.pokemontcg.io/v2/")
        .build()

private fun providePokemonTradingCardService(retrofit: Ktorfit): PokemonTradingCardService = retrofit.create<PokemonTradingCardService>()

val networkModule = module {
    single { provideHttpClient() }
    single { providePokemonCardsRetrofit(get()) }
    single { providePokemonTradingCardService(get()) }
}
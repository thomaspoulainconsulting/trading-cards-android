package com.tpc.pokemontradingcards.data.service

import com.tpc.pokemontradingcards.data.dto.HoldingPokemonCardSetsData
import com.tpc.pokemontradingcards.data.dto.HoldingPokemonCardsData
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonTradingCardService {

    @GET("cards")
    suspend fun getPokemonCards(
        @Query("q") query: String,
        @Query("orderBy") orderBy: String,
        @Query("select") select: String
    ): HoldingPokemonCardsData

    @GET("sets")
    suspend fun getPokemonCardSets(): HoldingPokemonCardSetsData
}
package com.tpc.tradingcards.data.service

import com.tpc.tradingcards.data.dto.HoldingPokemonCardSetsDataTO
import com.tpc.tradingcards.data.dto.HoldingPokemonCardTypesDataTO
import com.tpc.tradingcards.data.dto.HoldingPokemonCardsDataTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonTradingCardService {

    @GET("cards")
    suspend fun getPokemonCards(
        @Query("q") query: String,
        @Query("orderBy") orderBy: String,
        @Query("select") select: String
    ): HoldingPokemonCardsDataTO

    @GET("sets")
    suspend fun getPokemonCardSets(): HoldingPokemonCardSetsDataTO

    @GET("supertypes")
    suspend fun getPokemonCardTypes(): HoldingPokemonCardTypesDataTO
}
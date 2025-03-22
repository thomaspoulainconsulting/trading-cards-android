package com.tpc.tradingcards.shared.data.service

import com.tpc.tradingcards.shared.data.dto.HoldingPokemonCardSetsDataTO
import com.tpc.tradingcards.shared.data.dto.HoldingPokemonCardTypesDataTO
import com.tpc.tradingcards.shared.data.dto.HoldingPokemonCardsDataTO
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

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
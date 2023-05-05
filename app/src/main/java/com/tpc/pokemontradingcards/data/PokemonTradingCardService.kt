package com.tpc.pokemontradingcards.data

import com.tpc.pokemontradingcards.data.dto.HoldingPokemonData
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonTradingCardService {

    @GET("cards")
    suspend fun getPokemonCards(
        @Query("q") query: String,
        @Query("orderBy") orderBy: String,
        @Query("select") select: String
    ): HoldingPokemonData
}
package com.tpc.pokemontradingcards.data.dto

import com.squareup.moshi.Json

data class PokemonCardData(
    val id: String,
    val name: String,
    @Json(name = "nationalPokedexNumbers") val number: List<Int>,
    val images: CardSize,
)

data class CardSize(
    val small: String,
    val large: String
)

data class HoldingPokemonData(@Json(name = "data") val cards: List<PokemonCardData>)
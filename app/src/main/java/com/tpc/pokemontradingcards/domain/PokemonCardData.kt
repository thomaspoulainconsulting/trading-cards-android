package com.tpc.pokemontradingcards.domain

import com.squareup.moshi.Json

data class HoldingPokemonData(@Json(name = "data") val cards: List<PokemonCardData>)

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

enum class PokemonSet(val id: String) {
    Base("base1"),
    Jungle("base2"),
    Wizards_Black_Star_Promos("basep"),
    Fossil("base3"),
    Base_Set_2("base4"),
    Pokemon_Go("pgo")
}

val PokemonCardDataEmpty: PokemonCardData = PokemonCardData("", "", emptyList(), CardSize("", ""))
package com.tpc.pokemontradingcards.data.dto

data class PokemonCardData(
    val id: String,
    val name: String,
    val number: String,
    val images: CardSize,
)

data class CardSize(
    val small: String,
    val large: String
)

data class HoldingPokemonCardsData(val data: List<PokemonCardData>)

data class PokemonCardSetData(
    val id: String,
    val name: String,
    val releaseDate: String,
    val images: SetImages
)

data class SetImages(
    val symbol: String,
    val logo: String
)

data class HoldingPokemonCardSetsData(val data: List<PokemonCardSetData>)
package com.tpc.tradingcards.data.dto

data class PokemonCardDataTO(
    val id: String,
    val name: String,
    val number: String,
    val supertype: String,
    val images: CardSizeTO,
)

data class CardSizeTO(
    val small: String,
    val large: String
)

data class HoldingPokemonCardsDataTO(val data: List<PokemonCardDataTO>)

data class PokemonCardSetDataTO(
    val id: String,
    val name: String,
    val releaseDate: String,
    val images: SetImagesTO
)

data class SetImagesTO(
    val symbol: String,
    val logo: String
)

data class HoldingPokemonCardSetsDataTO(val data: List<PokemonCardSetDataTO>)


data class HoldingPokemonCardTypesDataTO(val data: List<String>)
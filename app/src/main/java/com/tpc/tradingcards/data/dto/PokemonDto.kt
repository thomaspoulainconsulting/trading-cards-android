package com.tpc.tradingcards.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonCardDataTO(
    val id: String,
    val name: String,
    val number: String,
    val supertype: String,
    val images: CardSizeTO,
)

@Serializable
data class CardSizeTO(
    val small: String,
    val large: String
)

@Serializable
data class HoldingPokemonCardsDataTO(val data: List<PokemonCardDataTO>)

@Serializable
data class PokemonCardSetDataTO(
    val id: String,
    val name: String,
    val releaseDate: String,
    val images: SetImagesTO
)

@Serializable
data class SetImagesTO(
    val symbol: String,
    val logo: String
)

@Serializable
data class HoldingPokemonCardSetsDataTO(val data: List<PokemonCardSetDataTO>)


@Serializable
data class HoldingPokemonCardTypesDataTO(val data: List<String>)
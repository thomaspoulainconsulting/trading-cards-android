package com.tpc.tradingcards.data.repository.pokemon

import com.tpc.tradingcards.data.model.CardType


sealed class PokemonCardType : CardType {
    data class Pokemon(
        override val name: String = "Pokemon",
        override var isSelected: Boolean = true
    ) : PokemonCardType()

    data class Trainer(
        override val name: String = "Trainer",
        override var isSelected: Boolean = false
    ) : PokemonCardType()

    data class Potion(
        override val name: String = "Potion",
        override var isSelected: Boolean = true
    ) : PokemonCardType()
}

class PokemonCardTypeSource {

    fun getCardTypes(): List<CardType> {
        return listOf(
            PokemonCardType.Pokemon(),
            PokemonCardType.Trainer(),
            PokemonCardType.Potion()
        )
    }
}
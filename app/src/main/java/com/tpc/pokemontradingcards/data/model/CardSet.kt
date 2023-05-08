package com.tpc.pokemontradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardSet(
    @PrimaryKey val id: String,
    val name: String,
    val cardType: CardType,
    val symbol: String,
)

val CardSetEmpty = CardSet("1", "Base", CardType.POKEMON, "")
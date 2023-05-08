package com.tpc.pokemontradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey val id: String,
    val idSet: String,
    val number: Int,
    val name: String,
    val description: String = "",
    val urlSmall: String,
    val urlLarge: String,
    val cardType: CardType
)

val CardEmpty = Card(
    id = "1",
    name = "charizard",
    urlSmall = "https://images.pokemontcg.io/base1/4.png",
    urlLarge = "https://images.pokemontcg.io/base1/4_hires.png",
    number = 0,
    idSet = "1",
    cardType = CardType.POKEMON
)
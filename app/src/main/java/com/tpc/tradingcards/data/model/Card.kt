package com.tpc.tradingcards.data.model

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
    val cardType: CardType,
    val isFavorite: Boolean
)

val CardEmpty = Card(
    id = "1",
    name = "",
    urlSmall = "",
    urlLarge = "",
    number = 0,
    idSet = "1",
    cardType = CardType.POKEMON,
    isFavorite = false
)
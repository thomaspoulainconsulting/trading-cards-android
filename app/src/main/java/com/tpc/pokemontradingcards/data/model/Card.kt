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
)

val CardEmpty = Card(id = "", name = "", urlSmall = "", urlLarge = "", number = 0, idSet = "")
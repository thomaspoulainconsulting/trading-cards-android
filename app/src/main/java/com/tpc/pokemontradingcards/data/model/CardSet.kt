package com.tpc.pokemontradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardSet(
    @PrimaryKey val id: String,
    val name: String,
)

val CardSetEmpty = CardSet("", "")
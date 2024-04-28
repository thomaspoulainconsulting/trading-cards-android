package com.tpc.tradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey val id: String,
    val idSet: String,
    val number: Int,
    val name: String,
    val urlSmall: String,
    val urlLarge: String,
    val supertype: String,
    val tradingCardGame: TradingCardGame,
) {
    companion object {
        val mock = Card(
            id = "1",
            name = "",
            urlSmall = "",
            urlLarge = "",
            number = 0,
            idSet = "1",
            supertype = "",
            tradingCardGame = TradingCardGame.POKEMON,
        )
    }
}
package com.tpc.tradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardSet(
    @PrimaryKey val id: String,
    val name: String,
    val tradingCardGame: TradingCardGame,
    val symbol: String,
    val releaseDate: String,
) {
    companion object {
        val mock = CardSet("1", "Base", TradingCardGame.POKEMON, "", "")
    }
}
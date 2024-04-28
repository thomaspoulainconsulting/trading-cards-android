package com.tpc.tradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardType(
    @PrimaryKey val name: String,
    val tradingCardGame: TradingCardGame,
) {
    companion object {
        val mock = CardType("Base", TradingCardGame.POKEMON)
    }
}
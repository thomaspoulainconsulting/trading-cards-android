package com.tpc.tradingcards.shared.data.model

data class CardSet(
    val id: String,
    val name: String,
    val tradingCardGame: TradingCardGame,
    val symbol: String,
    val releaseDate: String,
) {
    companion object {
        val mock = CardSet("1", "Base", TradingCardGame.POKEMON, "", "")
    }
}
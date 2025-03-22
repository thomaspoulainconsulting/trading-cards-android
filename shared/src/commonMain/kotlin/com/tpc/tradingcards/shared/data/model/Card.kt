package com.tpc.tradingcards.shared.data.model

data class Card(
    val id: String,
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
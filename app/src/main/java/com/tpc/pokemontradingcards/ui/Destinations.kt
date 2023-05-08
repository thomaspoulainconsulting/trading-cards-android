package com.tpc.pokemontradingcards.ui

sealed class Destinations(
    val route: String,
) {

    object CardList : Destinations("cards")

    object CardDetails : Destinations("cards/{id}")

}
package com.tpc.pokemontradingcards.ui

sealed class PokemonUiState {
    object Loading : PokemonUiState()
    //class Loaded(val data: PokemonUIModel())
}
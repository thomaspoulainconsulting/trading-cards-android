package com.tpc.tradingcards.ui

sealed class PokemonUiState {
    object Loading : PokemonUiState()
    //class Loaded(val data: PokemonUIModel())
}
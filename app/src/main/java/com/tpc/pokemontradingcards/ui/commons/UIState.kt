package com.tpc.pokemontradingcards.ui.commons

sealed class UIState<T> {
    class Loading<T>(val preloadedData: T? = null) : UIState<T>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error<T>(val exception: Throwable? = null) : UIState<T>()
}
package com.tpc.tradingcards.shared.ui.list.state

import com.tpc.tradingcards.shared.data.model.CardSet

sealed class CardListState {
    data object Loading : CardListState()
    class Success(val sets: List<CardSet>) : CardListState()
    class Error(val throwable: Throwable) : CardListState()
}
package com.tpc.tradingcards.ui.details.state

import com.tpc.tradingcards.data.model.Card

sealed class CardDetailsState {
    data object Loading : CardDetailsState()
    class Success(val cards: List<Card>) : CardDetailsState()
    class Error(val throwable: Throwable) : CardDetailsState()

}
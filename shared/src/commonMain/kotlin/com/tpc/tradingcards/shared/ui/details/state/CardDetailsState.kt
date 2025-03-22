package com.tpc.tradingcards.shared.ui.details.state

import com.tpc.tradingcards.shared.data.model.Card

sealed class CardDetailsState {
    data object Loading : CardDetailsState()
    class Success(val cards: List<Card>) : CardDetailsState()
    class Error(val throwable: Throwable) : CardDetailsState()

}
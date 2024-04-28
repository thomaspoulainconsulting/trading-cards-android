package com.tpc.tradingcards.ui.details.state

import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardType

sealed class CardDetailsState {
    data object Loading : CardDetailsState()
    class Success(val cards: List<Card>, val types: List<CardType>) : CardDetailsState()
    class Error(val throwable: Throwable) : CardDetailsState()

}
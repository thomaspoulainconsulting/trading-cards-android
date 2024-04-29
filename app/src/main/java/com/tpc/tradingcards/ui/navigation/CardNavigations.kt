package com.tpc.tradingcards.ui.navigation

import com.tpc.tradingcards.core.navigation.TradingCardArgument
import com.tpc.tradingcards.core.navigation.TradingCardNavigation

internal enum class CardNavigations : TradingCardNavigation {
    Start,
    List,
    Details {
        override var arguments = listOf(CardArguments.IdSet)
    }
}

internal enum class CardArguments : TradingCardArgument {
    IdSet,
}

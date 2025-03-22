package com.tpc.tradingcards.shared.core.navigation

/**
 * This interface is used for the screens destinations
 * It exposes a route formatted with the concatenation of
 * the interface's name, the implemented enum's name, the enum's value name
 * and the optional arguments
 */
internal interface TradingCardNavigation {

    val route: String
        get() =
            TradingCardNavigation::class.java.simpleName.plus("/")
                .plus(this::class.java.simpleName).plus("/")
                .plus(toString()).plus("/")
                .plus(arguments.joinToString("/") { "{$it}" })

    val arguments: List<TradingCardArgument>
        get() = emptyList()
}

/**
 * Interface used when defining route's arguments
 */
internal interface TradingCardArgument

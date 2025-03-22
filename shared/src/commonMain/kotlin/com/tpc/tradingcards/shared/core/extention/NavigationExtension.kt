package com.tpc.tradingcards.shared.core.extention

import androidx.navigation.NavController

internal fun NavController.navigateWithArguments(route: String, arguments: Map<String, String>) {
    val routeWithArgs = arguments.entries.fold(route) { acc, entry ->
        acc.replace("{${entry.key}}", entry.value)
    }
    navigate(routeWithArgs)
}
package com.tpc.tradingcards.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.tpc.tradingcards.core.extention.navigateWithArguments
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.ui.navigation.CardArguments
import com.tpc.tradingcards.ui.navigation.CardNavigations
import com.tpc.tradingcards.ui.navigation.cardsGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            setSingletonImageLoaderFactory { context ->
                ImageLoader.Builder(context)
                    .crossfade(true)
                    .build()
            }

            TradingCardsTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = CardNavigations.Start.route,
                ) {
                    cardsGraph(
                        navigateToDetails = { idSet ->
                            navController.navigateWithArguments(
                                route = CardNavigations.Details.route,
                                arguments = mapOf(
                                    CardArguments.IdSet.name to idSet
                                )
                            )
                        },
                        onBack = navController::popBackStack
                    )
                }
            }
        }
    }
}
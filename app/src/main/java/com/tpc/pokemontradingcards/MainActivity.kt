package com.tpc.pokemontradingcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tpc.pokemontradingcards.ui.cardListRoute
import com.tpc.pokemontradingcards.ui.cardsGraph
import com.tpc.pokemontradingcards.ui.commons.theme.PokemonTradingCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTradingCardsTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = cardListRoute,
                        modifier = Modifier.consumeWindowInsets(innerPadding)
                    ) {
                        cardsGraph(navController)
                    }
                }
            }
        }
    }
}
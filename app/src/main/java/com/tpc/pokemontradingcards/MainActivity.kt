package com.tpc.pokemontradingcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tpc.pokemontradingcards.ui.PokemonListScreen
import com.tpc.pokemontradingcards.ui.commons.theme.PokemonTradingCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTradingCardsTheme {
                PokemonListScreen()
            }
        }
    }
}
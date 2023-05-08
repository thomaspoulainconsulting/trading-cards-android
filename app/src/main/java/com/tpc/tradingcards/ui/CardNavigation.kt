package com.tpc.tradingcards.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

const val cardListRoute = "cards"
private const val cardDetailsRoute = "cards-details"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cardsGraph(
    navController: NavController,
) {
    composable(cardListRoute) {
        val pokemonViewModel: CardViewModel = hiltViewModel()
        val sets by pokemonViewModel.sets.collectAsStateWithLifecycle()

        CardListScreen(sets) { idSet ->
            pokemonViewModel.fetchCards(idSet)
            navController.navigateToCardSetDetails()
        }
    }

    composable(cardDetailsRoute) { backStackEntry ->
        val parentEntry =
            remember(backStackEntry) { navController.getBackStackEntry(cardListRoute) }
        val pokemonViewModel: CardViewModel = hiltViewModel(parentEntry)
        val cards by pokemonViewModel.cards.collectAsStateWithLifecycle()

        CardDetailsScreen(cards) {
            navController.popBackStack()
        }
    }
}

fun NavController.navigateToCardSetDetails() {
    navigate(cardDetailsRoute)
}
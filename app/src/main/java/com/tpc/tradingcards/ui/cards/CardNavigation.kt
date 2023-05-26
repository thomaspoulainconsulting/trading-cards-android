package com.tpc.tradingcards.ui.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tpc.tradingcards.ui.cards.screen.CardDetailsScreen
import com.tpc.tradingcards.ui.cards.screen.CardSetsListScreen

const val cardListRoute = "cards"
private const val cardDetailsRoute = "cards-details"

fun NavGraphBuilder.cardsGraph(
    navController: NavController,
) {
    composable(
        route = cardListRoute,
    ) {
        val pokemonViewModel: CardViewModel = hiltViewModel()
        val sets by pokemonViewModel.sets.collectAsStateWithLifecycle()

        CardSetsListScreen(sets) { idSet ->
            pokemonViewModel.fetchCards(idSet)
            navController.navigateToCardSetDetails()
        }
    }

    composable(
        route = cardDetailsRoute,
    ) { backStackEntry ->
        val parentEntry =
            remember(backStackEntry) { navController.getBackStackEntry(cardListRoute) }
        val cardViewModel: CardViewModel = hiltViewModel(parentEntry)

        val cards by cardViewModel.cards.collectAsStateWithLifecycle()
        val set by cardViewModel.cardSetSelected.collectAsStateWithLifecycle()
        val types by cardViewModel.types.collectAsStateWithLifecycle()

        CardDetailsScreen(
            set,
            cards,
            types,
            onTypeChanged = cardViewModel::toggleCardTypeSelection
        ) {
            navController.popBackStack()
        }
    }
}

fun NavController.navigateToCardSetDetails() {
    navigate(cardDetailsRoute)
}
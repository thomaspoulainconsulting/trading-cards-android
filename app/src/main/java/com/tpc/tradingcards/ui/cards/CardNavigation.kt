package com.tpc.tradingcards.ui.cards

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.tpc.tradingcards.ui.cards.screen.CardDetailsScreen
import com.tpc.tradingcards.ui.cards.screen.CardSetsListScreen

const val cardListRoute = "cards"
private const val cardDetailsRoute = "cards-details"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cardsGraph(
    navController: NavController,
) {
    composable(
        route = cardListRoute,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, tween(700))
        }
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
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(700))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(700))
        }
    ) { backStackEntry ->
        val parentEntry =
            remember(backStackEntry) { navController.getBackStackEntry(cardListRoute) }
        val pokemonViewModel: CardViewModel = hiltViewModel(parentEntry)

        val cards by pokemonViewModel.cards.collectAsStateWithLifecycle()
        val set by pokemonViewModel.cardSetSelected.collectAsStateWithLifecycle()

        CardDetailsScreen(set, cards) {
            navController.popBackStack()
        }
    }
}

fun NavController.navigateToCardSetDetails() {
    navigate(cardDetailsRoute)
}
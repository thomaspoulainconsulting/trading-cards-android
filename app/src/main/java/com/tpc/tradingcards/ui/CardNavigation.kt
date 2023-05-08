package com.tpc.tradingcards.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable

const val cardListRoute = "cards"
private const val cardDetailsRoute = "cards/{id}"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cardsGraph(
    navController: NavController,
) {
    composable(cardListRoute) {
        val pokemonViewModel: PokemonListViewModel = hiltViewModel()
        val sets by pokemonViewModel.sets.collectAsStateWithLifecycle()

        CardListScreen(sets) { idSet ->
            pokemonViewModel.fetchCards(idSet)
            navController.navigateToCardSetDetails(idSet)
        }
    }

    composable(
        cardDetailsRoute,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
        val cardSetId = it.arguments?.getString("id") ?: ""
        val pokemonViewModel: PokemonListViewModel = hiltViewModel()
        val cards = pokemonViewModel.cards.collectAsStateWithLifecycle()
            .value.filter { card -> card.idSet == cardSetId }

        CardDetailsScreen(cards) {
            navController.popBackStack()
        }
    }
}

fun NavController.navigateToCardSetDetails(idSet: String) {
    this.navigate("cards/$idSet")
}
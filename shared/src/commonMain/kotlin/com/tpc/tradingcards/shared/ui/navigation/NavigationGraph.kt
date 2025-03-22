package com.tpc.tradingcards.shared.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tpc.tradingcards.shared.ui.details.ui.CardDetailsScreen
import com.tpc.tradingcards.shared.ui.details.vm.CardDetailsViewModel
import com.tpc.tradingcards.shared.ui.list.ui.CardSetsListScreen
import com.tpc.tradingcards.shared.ui.list.vm.CardListViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.cardsGraph(
    navigateToDetails: (id: String) -> Unit,
    onBack: () -> Unit,
) {
    navigation(
        route = CardNavigations.Start.route,
        startDestination = CardNavigations.List.route,
    )
    {
        composable(
            route = CardNavigations.List.route,
        ) {
            val vm: CardListViewModel = koinViewModel()
            val state by vm.state.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                vm.fetchCardSets()
            }

            CardSetsListScreen(
                state = state,
                navigateToDetails = { navigateToDetails(it.id) },
            )
        }

        composable(
            route = CardNavigations.Details.route,
            arguments = CardNavigations.Details.arguments.map {
                navArgument(it.toString()) { type = NavType.StringType }
            }
        ) {
            val vm: CardDetailsViewModel = koinViewModel()
            val state by vm.state.collectAsStateWithLifecycle()
            val selectedTypes by vm.selectedTypes.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                vm.setIdSet(it.arguments?.getString(CardArguments.IdSet.name).orEmpty())
                vm.getCards()
            }

            CardDetailsScreen(
                state = state,
                selectedTypes = selectedTypes,
                onFilter = vm::updateSelectedType,
                onBack = onBack,
            )
        }
    }
}

package com.tpc.pokemontradingcards.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.pokemontradingcards.R
import com.tpc.pokemontradingcards.ui.composables.CardSetComposable
import com.tpc.pokemontradingcards.ui.composables.PokemonCardCompact
import com.tpc.pokemontradingcards.ui.composables.PokemonCardFull

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PokemonListScreen(pokemonViewModel: PokemonListViewModel = hiltViewModel()) {

    val cards by pokemonViewModel.cards.collectAsStateWithLifecycle()
    val sets by pokemonViewModel.sets.collectAsStateWithLifecycle()
    var selectedCardIndex by remember { mutableStateOf(-1) }

    Box(Modifier.fillMaxSize()) {
        Column {
            Text(
                text = stringResource(R.string.home_screen_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 20.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(sets, key = { it.id }) {
                        CardSetComposable(
                            modifier = Modifier.animateItemPlacement(),
                            cardSet = it,
                            onClick = { idSet ->
                                pokemonViewModel.updatePokemonSet(idSet)
                            })
                    }
                }
            )
        }

        pokemonViewModel.currentCardSet?.let {
            ModalBottomSheet(
                scrimColor = Color.White.copy(alpha = 0f),
                onDismissRequest = {
                    selectedCardIndex = -1
                    pokemonViewModel.updatePokemonSet(null)
                }
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(90.dp),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        val contentToShow =
                            cards.filter { it.idSet == pokemonViewModel.currentCardSet }

                        if (contentToShow.isEmpty()) {
                            item {
                                Text(stringResource(R.string.no_pokemon_cards))
                            }
                        }

                        items(contentToShow, key = { it.id }) { pokemonCard ->
                            PokemonCardCompact(
                                modifier = Modifier.animateItemPlacement(),
                                data = pokemonCard
                            ) {
                                selectedCardIndex =
                                    contentToShow.indexOf(pokemonCard)
                            }
                        }
                    })
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            visible = pokemonViewModel.currentCardSet != null && selectedCardIndex != -1,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            cards
                .filter { it.idSet == pokemonViewModel.currentCardSet }
                .getOrNull(selectedCardIndex)
                ?.let { card ->
                    PokemonCardFull(
                        modifier = Modifier.align(Alignment.Center),
                        data = card,
                        canBeRotated = true
                    ) {
                        selectedCardIndex = -1
                    }
                }
        }
    }
}
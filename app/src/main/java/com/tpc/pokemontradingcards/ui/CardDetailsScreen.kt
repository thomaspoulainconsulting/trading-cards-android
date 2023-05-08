package com.tpc.pokemontradingcards.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tpc.pokemontradingcards.R
import com.tpc.pokemontradingcards.ui.composables.PokemonCardCompact
import com.tpc.pokemontradingcards.ui.composables.PokemonCardFull

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun CardDetailsScreen(
    navController: NavController,
    cardSetId: String,
    pokemonViewModel: PokemonListViewModel = hiltViewModel()
) {

    val cards by pokemonViewModel.cards.collectAsStateWithLifecycle()
    var selectedCardIndex by remember { mutableStateOf(-1) }

    Box(Modifier.fillMaxSize()) {

        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(bottom = 32.dp, top = 16.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
                Text(stringResource(R.string.back), fontWeight = FontWeight.Medium)
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    val contentToShow =
                        cards.filter { it.idSet == cardSetId }

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
                            selectedCardIndex = contentToShow.indexOf(pokemonCard)
                        }
                    }
                })
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.Center),
            visible = selectedCardIndex != -1,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            cards
                .filter { it.idSet == cardSetId }
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
package com.tpc.pokemontradingcards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.pokemontradingcards.domain.PokemonSet

@Composable
fun PokemonListScreen(pokemonViewModel: PokemonListViewModel = hiltViewModel()) {

    val pokemonCards by pokemonViewModel.pokemonCardsData.collectAsStateWithLifecycle()

    Box() {

        LazyVerticalGrid(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
            columns = GridCells.Adaptive(166.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(pokemonCards, key = { it.id }) { pokemonCard ->
                    PokemonCard(data = pokemonCard)
                }
            })

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(8.dp),
            onClick = {
                pokemonViewModel.updatePokemonSet(PokemonSet.Base_Set_2)
            }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "change set"
                )

                Text("Change Set")
            }
        }
    }
}
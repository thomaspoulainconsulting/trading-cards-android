package com.tpc.pokemontradingcards.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tpc.pokemontradingcards.R
import com.tpc.pokemontradingcards.data.model.CardSet
import com.tpc.pokemontradingcards.ui.composables.CardSetComposable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardListScreen(
    sets: List<CardSet>,
    onNavigateToCardSetDetails: (String) -> Unit
) {
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
                        onClick = onNavigateToCardSetDetails
                    )
                }
            }
        )
    }
}
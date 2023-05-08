package com.tpc.tradingcards.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.R
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.ui.commons.theme.TradingCardsTheme
import com.tpc.tradingcards.ui.composables.CardSetComposable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardListScreen(
    sets: List<CardSet>,
    onNavigateToCardSetDetails: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.app_name),
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

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
fun CardListScreenPreview() {
    TradingCardsTheme {
        val sets = listOf(
            CardSet(id = "1", name = "Base", cardType = CardType.POKEMON, symbol = ""),
            CardSet(id = "2", name = "Fossil", cardType = CardType.POKEMON, symbol = ""),
        )
        CardListScreen(sets = sets) {}
    }
}
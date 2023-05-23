package com.tpc.tradingcards.ui.cards.screen

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.composable.Loading
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.ui.cards.composables.TradingCardSet
import com.tpc.tradingcards.ui.cards.testtag.CardListTestTag

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardSetsListScreen(
    cardSets: List<CardSet>,
    onNavigateToCardSetDetails: (CardSet) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(largeSize)
                .padding(top = 20.dp)
                .semantics { heading() }
        )

        LazyColumn(
            contentPadding = PaddingValues(largeSize),
            verticalArrangement = Arrangement.spacedBy(largeSize),
            content = {
                if (cardSets.isEmpty()) {
                    item {
                        Loading(Modifier.testTag(CardListTestTag.Loading.tag))
                    }
                } else {
                    items(cardSets, key = { it.id }) {
                        TradingCardSet(
                            modifier = Modifier
                                .animateItemPlacement()
                                .testTag(CardListTestTag.Data.tag),
                            cardSet = it,
                            onClick = onNavigateToCardSetDetails
                        )
                    }
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
        CardSetsListScreen(cardSets = sets) {}
    }
}
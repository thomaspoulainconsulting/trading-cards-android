package com.tpc.tradingcards.shared.ui.list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.shared.data.model.CardSet
import com.tpc.tradingcards.shared.data.model.TradingCardGame
import com.tpc.tradingcards.shared.ui.list.state.CardListState
import com.tpc.tradingcards.shared.ui.list.ui.composable.TradingCardSet
import com.tpc.tradingcards.shared.ui.list.ui.composable.TradingCardSetLoading

enum class CardListTestTag(val tag: String) {
    Loading("cardListLoading"),
    Data("cardListData"),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CardSetsListScreen(
    state: CardListState,
    navigateToDetails: (CardSet) -> Unit
) {
    Column(
        modifier = Modifier.background(Color.White),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = padding(largeSize)
                .padding(top = 20.dp)
                .semantics { heading() }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(largeSize),
            verticalArrangement = Arrangement.spacedBy(largeSize),
            content = {
                when (state) {
                    is CardListState.Loading -> {
                        items(count = 10) {
                            TradingCardSetLoading()
                        }
                    }

                    is CardListState.Error -> {
                        item {
                            Text(state.throwable.message.orEmpty())
                        }
                    }

                    is CardListState.Success -> {
                        itemsIndexed(state.sets) { index, item ->
                            var animationTrigger by remember { mutableStateOf(false) }

                            LaunchedEffect(item) {
                                animationTrigger = true
                            }

                            AnimatedVisibility(
                                visible = animationTrigger,
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        delayMillis = index * 100
                                    )
                                )
                            ) {
                                TradingCardSet(
                                    modifier = Modifier
                                        .animateItem()
                                        .testTag(CardListTestTag.Data.tag),
                                    cardSet = item,
                                    onClick = navigateToDetails
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
private fun LoadingPreview() {
    TradingCardsTheme {
        CardSetsListScreen(
            state = CardListState.Loading,
            navigateToDetails = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
private fun ErrorPreview() {
    TradingCardsTheme {
        CardSetsListScreen(
            state = CardListState.Error(IllegalStateException("error")),
            navigateToDetails = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
private fun SuccessPreview() {
    TradingCardsTheme {
        val sets = listOf(
            CardSet(
                id = "1",
                name = "Base",
                tradingCardGame = TradingCardGame.POKEMON,
                symbol = "",
                ""
            ),
            CardSet(
                id = "2",
                name = "Fossil",
                tradingCardGame = TradingCardGame.POKEMON,
                symbol = "",
                ""
            ),
        )
        CardSetsListScreen(
            state = CardListState.Success(sets),
            navigateToDetails = {}
        )
    }
}
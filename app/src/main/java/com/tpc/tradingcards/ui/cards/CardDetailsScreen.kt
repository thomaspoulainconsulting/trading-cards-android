package com.tpc.tradingcards.ui.cards

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.UIState
import com.tpc.tradingcards.core.ui.composable.ErrorFetchingData
import com.tpc.tradingcards.core.ui.composable.Loading
import com.tpc.tradingcards.core.ui.theme.Dark80
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.largerSize
import com.tpc.tradingcards.core.ui.theme.mediumSize
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardEmpty
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.ui.cards.composables.TradingCardCompact
import com.tpc.tradingcards.ui.cards.composables.TradingCardFull
import com.tpc.tradingcards.ui.cards.composables.TradingCardSet

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun CardDetailsScreen(
    cardSet: CardSet,
    cards: UIState<List<Card>>,
    onBack: () -> Unit
) {
    var selectedCard: Card? by remember { mutableStateOf(null) }

    BackHandler {
        if (selectedCard == null) onBack()
        else selectedCard = null
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.padding(
                start = largeSize,
                end = largeSize,
                top = largeSize,
                bottom = 0.dp,
            ),
        ) {
            Row {
                Icon(
                    modifier = Modifier
                        .padding(bottom = largerSize, top = largeSize)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = onBack
                        ),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )
            }

            TradingCardSet(cardSet = cardSet) {}

            when (cards) {
                is UIState.Error -> {
                    ErrorFetchingData(Modifier.testTag(CardDetailsTestTag.Error.tag))
                }

                is UIState.Loading -> {
                    Loading(
                        Modifier
                            .padding(top = mediumSize)
                            .testTag(CardDetailsTestTag.Loading.tag)
                    )
                }

                is UIState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        contentPadding = PaddingValues(top = largeSize),
                        horizontalArrangement = Arrangement.spacedBy(largeSize),
                        verticalArrangement = Arrangement.spacedBy(largeSize),
                        content = {
                            items(cards.data, key = { it.id }) { pokemonCard ->
                                TradingCardCompact(
                                    modifier = Modifier.animateItemPlacement(),
                                    data = pokemonCard
                                ) {
                                    selectedCard = pokemonCard
                                }
                            }
                        })
                }
            }
        }

        AnimatedVisibility(
            visible = selectedCard != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable {
                        selectedCard = null
                    }
                    .background(Dark80.copy(alpha = 0.8f))) {}
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = selectedCard != null,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            selectedCard?.let { card ->
                TradingCardFull(
                    modifier = Modifier.align(Alignment.Center),
                    data = card,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDetailsScreenWithoutDataPreview() {
    TradingCardsTheme {
        CardDetailsScreen(CardSetEmpty, UIState.Loading()) {}
    }
}

@Preview(showBackground = true)
@Composable
fun CardDetailsScreenWithDataPreview() {
    TradingCardsTheme {
        CardDetailsScreen(CardSetEmpty, UIState.Success(listOf(CardEmpty))) {}
    }
}
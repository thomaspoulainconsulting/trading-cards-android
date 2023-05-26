package com.tpc.tradingcards.ui.cards.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.composable.Loading
import com.tpc.tradingcards.core.ui.theme.Dark80
import com.tpc.tradingcards.core.ui.theme.PurpleGrey40
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.mediumSize
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardEmpty
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.ui.cards.composables.TradingCardCompact
import com.tpc.tradingcards.ui.cards.composables.TradingCardFull
import com.tpc.tradingcards.ui.cards.testtag.CardDetailsTestTag

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
)
@Composable
fun CardDetailsScreen(
    cardSet: CardSet,
    cards: List<Card>,
    types: List<CardType>,
    onTypeChanged: (CardType) -> Unit,
    onBack: () -> Unit
) {
    var selectedCard: Card? by remember { mutableStateOf(null) }
    var isFilterByTypeVisible by remember { mutableStateOf(false) }

    BackHandler {
        if (selectedCard == null) onBack()
        else selectedCard = null
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.padding(
                start = largeSize,
                end = largeSize,
                top = mediumSize,
                bottom = 0.dp,
            ),
        ) {
            Row(
                modifier = Modifier.padding(top = mediumSize, bottom = mediumSize),
                horizontalArrangement = Arrangement.spacedBy(largeSize),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = onBack
                        ),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )
                Column(Modifier.align(Alignment.CenterVertically)) {
                    Text(cardSet.name)
                    Text(
                        modifier = Modifier.placeholder(
                            visible = cards.isEmpty(),
                            highlight = PlaceholderHighlight.fade(highlightColor = PurpleGrey40)
                        ),
                        text = pluralStringResource(
                            R.plurals.number_or_cards,
                            count = cards.size,
                            cards.size
                        )
                    )
                }
            }


            if (cards.isEmpty()) {
                Loading(
                    Modifier
                        .padding(top = mediumSize)
                        .testTag(CardDetailsTestTag.Loading.tag)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    contentPadding = PaddingValues(top = largeSize, bottom = largeSize),
                    horizontalArrangement = Arrangement.spacedBy(largeSize),
                    verticalArrangement = Arrangement.spacedBy(largeSize),
                    content = {
                        items(cards, key = { it.id }) { pokemonCard ->
                            TradingCardCompact(
                                data = pokemonCard
                            ) {
                                selectedCard = pokemonCard
                            }
                        }
                    })
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

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(largeSize),
            onClick = {
                isFilterByTypeVisible = true
            }
        ) {
            Row(
                modifier = Modifier.padding(start = mediumSize, end = largeSize),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(mediumSize)
            ) {
                Icon(
                    imageVector = Icons.TwoTone.FilterAlt,
                    contentDescription = stringResource(R.string.filter_card_types_content_description)
                )
                Text(stringResource(R.string.filter))
            }
        }

        FilterContent(types, isFilterByTypeVisible, onTypeChanged) {
            isFilterByTypeVisible = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterContent(
    types: List<CardType>,
    isFilterByTypeVisible: Boolean,
    onTypeChanged: (CardType) -> Unit,
    onFilterVisibilityChanged: () -> Unit
) {
    AnimatedVisibility(visible = isFilterByTypeVisible) {
        ModalBottomSheet(
            onDismissRequest = onFilterVisibilityChanged,
        ) {
            Column(Modifier.padding(largeSize)) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(mediumSize)
                ) {
                    items(types) { cardType ->
                        FilterChip(
                            selected = cardType.isSelected,
                            onClick = {
                                onTypeChanged(cardType)
                            },
                            label = {
                                Text(text = cardType.name)
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.TwoTone.CheckCircle,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDetailsScreenWithoutDataPreview() {
    TradingCardsTheme {
        CardDetailsScreen(CardSetEmpty, emptyList(), emptyList(), {}) {}
    }
}

@Preview(showBackground = true)
@Composable
fun CardDetailsScreenWithDataPreview() {
    TradingCardsTheme {
        CardDetailsScreen(
            CardSetEmpty,
            listOf(CardEmpty),
            emptyList(),
            {}) {}
    }
}
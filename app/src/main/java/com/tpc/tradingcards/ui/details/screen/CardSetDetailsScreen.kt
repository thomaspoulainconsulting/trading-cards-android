package com.tpc.tradingcards.ui.details.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.theme.Dark80
import com.tpc.tradingcards.core.ui.theme.Pink80
import com.tpc.tradingcards.core.ui.theme.PurpleGrey40
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.mediumSize
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.ui.details.composable.SuccessState
import com.tpc.tradingcards.ui.details.composable.TradingCardFull
import com.tpc.tradingcards.ui.details.state.CardDetailsState

@Composable
fun CardDetailsScreen(
    state: CardDetailsState,
    onFilter: (CardType) -> Unit,
    onBack: () -> Unit
) {
    var selectedCard: Card? by remember { mutableStateOf(null) }

    /*BackHandler {
        if (selectedCard == null) onBack()
        else selectedCard = null
    }*/

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Pink80)
                    .padding(mediumSize),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(mediumSize)
            ) {
                Icon(
                    imageVector = Icons.TwoTone.FilterAlt,
                    contentDescription = stringResource(R.string.filter_card_types_content_description)
                )
                Text(stringResource(R.string.filter))
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Box(
            Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
        ) {
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                    Column(Modifier.align(Alignment.CenterVertically)) {
                        Text("Set")
                        Text(
                            modifier = Modifier.placeholder(
                                visible = state is CardDetailsState.Loading,
                                highlight = PlaceholderHighlight.fade(highlightColor = PurpleGrey40)
                            ),
                            text = pluralStringResource(
                                R.plurals.number_or_cards,
                                count = if (state is CardDetailsState.Success) state.cards.size else 0,
                                if (state is CardDetailsState.Success) state.cards.size else 0
                            )
                        )
                    }
                }

                when (state) {
                    is CardDetailsState.Loading -> {

                    }

                    is CardDetailsState.Error -> {

                    }

                    is CardDetailsState.Success -> {
                        SuccessState(
                            cards = state.cards,
                            onClick = {
                                selectedCard = it
                            }
                        )

                        /*FilterContent(types, isFilterByTypeVisible, onFilter) {
                        isFilterByTypeVisible = false
                    }*/
                    }
                }
            }

            selectedCard?.let {
                AnimatedVisibility(
                    visible = true,
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
                            card = card,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    TradingCardsTheme {
        CardDetailsScreen(
            state = CardDetailsState.Loading,
            onFilter = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessPreview() {
    TradingCardsTheme {
        CardDetailsScreen(
            state = CardDetailsState.Success(listOf(Card.mock)),
            onFilter = {},
            onBack = {},
        )
    }
}
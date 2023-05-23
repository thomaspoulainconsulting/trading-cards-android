package com.tpc.tradingcards.ui.cards.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.extention.debugPlaceholder
import com.tpc.tradingcards.core.ui.theme.Dark60
import com.tpc.tradingcards.core.ui.theme.Purple40
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.largerSize
import com.tpc.tradingcards.core.ui.theme.mediumElevation
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardSetEmpty

@Composable
fun TradingCardSet(
    modifier: Modifier = Modifier,
    cardSet: CardSet,
    onClick: (CardSet) -> Unit
) {
    val semanticLabel = stringResource(R.string.see_cards_from_card_set, cardSet.name)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(cardSet) })
            .clearAndSetSemantics {
                text = AnnotatedString(text = semanticLabel)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = mediumElevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Dark60, Purple40),
                        start = Offset(100f, 0f),
                        end = Offset(
                            Float.POSITIVE_INFINITY,
                            Float.POSITIVE_INFINITY
                        )
                    )
                )
                .padding(largeSize),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = cardSet.name)
            AsyncImage(
                modifier = Modifier.size(largerSize),
                model = ImageRequest.Builder(LocalContext.current).data(cardSet.symbol)
                    .crossfade(true).build(),
                placeholder = debugPlaceholder(debugPreview = R.drawable.debug_set_placehold),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun CardSetPreview() {
    TradingCardsTheme {
        TradingCardSet(cardSet = CardSetEmpty) {}
    }
}
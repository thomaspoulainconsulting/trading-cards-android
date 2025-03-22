package com.tpc.tradingcards.ui.list.ui.composable

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.largerSize
import com.tpc.tradingcards.core.ui.theme.mediumElevation
import com.tpc.tradingcards.data.model.CardSet

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
            .clearAndSetSemantics {
                text = AnnotatedString(text = semanticLabel)
            },
        onClick = {
            onClick(cardSet)
        },
        elevation = CardDefaults.cardElevation(defaultElevation = mediumElevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFE0B2), Color(0xFF81D4FA)),
                        start = Offset(0f, 0f),
                        end = Offset(800f, 800f)
                    )
                )
                .padding(largeSize),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = cardSet.name, color = Color.Black)
            AsyncImage(
                modifier = Modifier.size(largerSize),
                model = cardSet.symbol,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TradingCardsTheme {
        TradingCardSet(cardSet = CardSet.mock) {}
    }
}
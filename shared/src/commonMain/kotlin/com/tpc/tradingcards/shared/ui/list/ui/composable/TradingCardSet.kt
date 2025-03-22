package com.tpc.tradingcards.shared.ui.list.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.largerSize
import com.tpc.tradingcards.shared.data.model.CardSet

@Composable
fun TradingCardSet(
    modifier: Modifier = Modifier,
    cardSet: CardSet,
    onClick: (CardSet) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = {
            onClick(cardSet)
        },
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
                modifier = size(largerSize),
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
package com.tpc.tradingcards.ui.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tpc.tradingcards.data.model.Card

@Composable
fun TradingCardFull(
    modifier: Modifier = Modifier,
    card: Card,
) {
    Card(
        modifier = modifier.background(Color.Transparent),
    ) {
        AsyncImage(
            model = card.urlLarge,
            contentDescription = null,
            modifier = Modifier.width(300.dp)
        )
    }
}
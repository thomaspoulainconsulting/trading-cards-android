package com.tpc.tradingcards.ui.list.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme

@Composable
fun TradingCardSetLoading(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {}
}

@Preview
@Composable
private fun Preview() {
    TradingCardsTheme {
        TradingCardSetLoading()
    }
}
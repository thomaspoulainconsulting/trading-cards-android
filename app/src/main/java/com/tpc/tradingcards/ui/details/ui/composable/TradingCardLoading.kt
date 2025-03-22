package com.tpc.tradingcards.ui.details.ui.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.mediumElevation

@Composable
fun TradingCardLoading(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .width(92.dp)
            .height(128.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = mediumElevation),
    ) {

    }
}

@Preview
@Composable
private fun LoadingPreview() {
    TradingCardsTheme {
        TradingCardLoading()
    }
}
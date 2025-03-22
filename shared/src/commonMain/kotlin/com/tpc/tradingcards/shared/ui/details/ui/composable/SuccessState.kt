package com.tpc.tradingcards.shared.ui.details.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.shared.data.model.Card

@Composable
fun SuccessState(
    cards: List<Card>,
    onClick: (card: Card) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(top = largeSize, bottom = largeSize),
        horizontalArrangement = Arrangement.spacedBy(largeSize),
        verticalArrangement = Arrangement.spacedBy(largeSize),
        content = {
            items(cards, key = { it.id }) { card ->
                TradingCardCompact(
                    card = card,
                    onClick = onClick
                )
            }
        }
    )
}
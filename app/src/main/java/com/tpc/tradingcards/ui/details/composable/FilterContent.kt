package com.tpc.tradingcards.ui.details.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.mediumSize
import com.tpc.tradingcards.data.model.CardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterContent(
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

@Preview
@Composable
private fun Preview() {
    TradingCardsTheme {
        val cardTypes = listOf(CardType.mock)
        FilterContent(
            types = cardTypes,
            isFilterByTypeVisible = true,
            onTypeChanged = {},
            onFilterVisibilityChanged = {})
    }

}
package com.tpc.tradingcards.ui.details.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.CheckCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.Typography
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.largerSize
import com.tpc.tradingcards.core.ui.theme.mediumSize
import com.tpc.tradingcards.core.ui.theme.smallSize
import com.tpc.tradingcards.data.model.CardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterContent(
    types: Map<String,Boolean>,
    onFilterClicked: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(Modifier.padding(horizontal = largeSize)) {
            Text(text = stringResource(R.string.filter_by_types), style = Typography.titleMedium)

            LazyRow(
                contentPadding = PaddingValues(bottom = largerSize, top = smallSize),
                horizontalArrangement = Arrangement.spacedBy(mediumSize)
            ) {
                types.forEach { cardType, isSelected ->
                    item {
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                onFilterClicked(cardType)
                            },
                            label = {
                                Text(text = cardType)
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (false) Icons.TwoTone.CheckCircle else Icons.TwoTone.CheckCircleOutline, // FIXME
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
            types = mapOf("Energy" to true),
            onFilterClicked = {},
            onDismiss = {})
    }
}
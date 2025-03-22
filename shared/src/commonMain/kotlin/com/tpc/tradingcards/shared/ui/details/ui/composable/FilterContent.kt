package com.tpc.tradingcards.shared.ui.details.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.theme.Typography
import com.tpc.tradingcards.core.ui.theme.largeSize
import com.tpc.tradingcards.core.ui.theme.largerSize
import com.tpc.tradingcards.core.ui.theme.mediumSize
import com.tpc.tradingcards.core.ui.theme.smallSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterContent(
    types: Map<String, Boolean>,
    onFilterClicked: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(Modifier.padding(horizontal = largeSize)) {
            Text(
                text = stringResource(R.string.filter_by_types),
                style = Typography.titleMedium,
            )

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
                        )
                    }
                }
            }
        }
    }
}
package com.tpc.tradingcards.core.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tpc.tradingcards.R

@Composable
fun ErrorFetchingData(modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.error_fetching_data),
        )
    }
}
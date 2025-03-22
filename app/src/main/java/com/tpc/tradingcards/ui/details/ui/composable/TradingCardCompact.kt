package com.tpc.tradingcards.ui.details.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import coil3.compose.AsyncImage
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.ui.theme.mediumElevation
import com.tpc.tradingcards.core.ui.theme.ultraExtraSmallSize
import com.tpc.tradingcards.data.model.Card

@Composable
fun TradingCardCompact(
    modifier: Modifier = Modifier,
    card: Card,
    onClick: (card: Card) -> Unit
) {
    val semanticLabel = stringResource(R.string.card_name, card.name)
    Card(
        modifier = modifier
            .wrapContentSize()
            .semantics(true) {
                text = AnnotatedString(text = semanticLabel)
            }
            .clickable(onClick = { onClick(card) }),
        shape = RoundedCornerShape(ultraExtraSmallSize),
        elevation = CardDefaults.cardElevation(defaultElevation = mediumElevation),
    ) {
        AsyncImage(
            model = card.urlSmall,
            contentDescription = null,
        )
    }
}
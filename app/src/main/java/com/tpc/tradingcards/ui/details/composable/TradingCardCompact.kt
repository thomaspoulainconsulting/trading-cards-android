package com.tpc.tradingcards.ui.details.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.extention.debugPlaceholder
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
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
            model = ImageRequest.Builder(LocalContext.current)
                .data(card.urlSmall)
                .crossfade(500).build(),
            placeholder = debugPlaceholder(R.drawable.debug_card_placeholder),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonCardCompactPreview() {
    TradingCardsTheme {
        TradingCardCompact(
            card = Card.mock.copy(
                urlSmall = "https://images.pokemontcg.io/swsh12pt5/160_hires.png",
                number = 1,
                name = "Pikachu"
            ),
            onClick = {}
        )
    }
}
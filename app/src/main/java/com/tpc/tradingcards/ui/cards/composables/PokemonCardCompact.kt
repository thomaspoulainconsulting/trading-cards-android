package com.tpc.tradingcards.ui.cards.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.tradingcards.R
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardEmpty
import com.tpc.tradingcards.core.ui.theme.DefaultCardShape
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.extention.debugPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCardCompact(
    modifier: Modifier = Modifier,
    data: Card,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.wrapContentSize(),
        shape = DefaultCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.urlSmall)
                    .crossfade(500).build(),
                placeholder = debugPlaceholder(R.drawable.debug_card_placeholder),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonCardCompactPreview() {
    TradingCardsTheme {
        PokemonCardCompact(
            data = CardEmpty.copy(
                urlSmall = "https://images.pokemontcg.io/swsh12pt5/160_hires.png",
                number = 1,
                name = "Pikachu"
            )
        ) {}
    }
}
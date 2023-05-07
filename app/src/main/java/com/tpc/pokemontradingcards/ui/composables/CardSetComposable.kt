package com.tpc.pokemontradingcards.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.pokemontradingcards.data.model.CardSet
import com.tpc.pokemontradingcards.data.model.CardSetEmpty
import com.tpc.pokemontradingcards.ui.commons.theme.PokemonTradingCardsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSetComposable(
    modifier: Modifier = Modifier,
    cardSet: CardSet,
    onClick: (idCardSet: String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = {
            onClick(cardSet.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = cardSet.name)
            AsyncImage(
                modifier = Modifier.size(32.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cardSet.symbol).build(),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun CardSetPreview() {
    PokemonTradingCardsTheme {
        CardSetComposable(cardSet = CardSetEmpty) {}
    }
}
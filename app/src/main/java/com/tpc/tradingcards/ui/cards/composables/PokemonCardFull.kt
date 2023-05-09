package com.tpc.tradingcards.ui.cards.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.extention.debugPlaceholder
import com.tpc.tradingcards.core.ui.theme.DefaultCardShape
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardEmpty

@Composable
fun PokemonCardFull(
    modifier: Modifier = Modifier,
    data: Card
) {
    var isShimmerVisible by remember { mutableStateOf(true) }

    val rotX by rememberInfiniteTransition().animateFloat(
        initialValue = -10f, targetValue = 10f, animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse, animation = tween(
                durationMillis = 4000,
                easing = LinearEasing,
            )
        )
    )

    val rotY by rememberInfiniteTransition().animateFloat(
        initialValue = 0f, targetValue = 10f, animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse, animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            )
        )
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationX = rotX
                rotationY = rotY
            },
        shape = DefaultCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .widthIn(max = 200.dp)
                .heightIn(max = 300.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.urlLarge)
                .listener { _, _ -> isShimmerVisible = false }
                .build(),
            placeholder = debugPlaceholder(debugPreview = R.drawable.debug_card_placeholder),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x28292a)
@Composable
fun PokemonCardPreview() {
    TradingCardsTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            PokemonCardFull(
                modifier = Modifier.align(Alignment.Center),
                data = CardEmpty
            )
        }
    }
}
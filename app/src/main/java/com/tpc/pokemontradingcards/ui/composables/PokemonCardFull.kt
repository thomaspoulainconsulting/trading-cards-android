package com.tpc.pokemontradingcards.ui.composables

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.tpc.pokemontradingcards.data.model.Card
import com.tpc.pokemontradingcards.data.model.CardEmpty
import com.tpc.pokemontradingcards.ui.commons.theme.PokemonTradingCardsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCardFull(
    modifier: Modifier = Modifier,
    data: Card,
    canBeRotated: Boolean = false,
    onClick: () -> Unit
) {
    //val accelerometerState = rememberGravitySensorState(sensorDelay = SensorDelay.UI)
    var isShimmerVisible by remember { mutableStateOf(true) }

    val rotX by rememberInfiniteTransition(label = "test").animateFloat(
        initialValue = -10f, targetValue = 10f, animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse, animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            )
        ), label = "test"
    )

    val rotY by rememberInfiniteTransition(label = "test").animateFloat(
        initialValue = 0f, targetValue = 10f, animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse, animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            )
        ), label = "test"
    )

    Card(
        modifier = modifier
            /*.placeholder(
                color = Color.DarkGray,
                visible = isShimmerVisible,
                highlight = PlaceholderHighlight.fade(),
            )*/
            .graphicsLayer {
                if (canBeRotated) {
                    rotationX = rotX//accelerometerState.yForce
                    rotationY = rotY//accelerometerState.xForce
                }
            },
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .widthIn(max = 200.dp)
                .heightIn(max = 300.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.urlLarge)
                .listener { request, result ->
                    isShimmerVisible = false
                }.build(),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x28292a)
@Composable
fun PokemonCardPreview() {
    PokemonTradingCardsTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            PokemonCardFull(
                modifier = Modifier.align(Alignment.Center),
                data = CardEmpty.copy(
                    urlLarge = "https://images.pokemontcg.io/swsh12pt5/160_hires.png"
                ),
                canBeRotated = true,
                onClick = {

                }
            )
        }
    }
}
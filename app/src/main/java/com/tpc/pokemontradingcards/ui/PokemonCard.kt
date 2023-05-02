package com.tpc.pokemontradingcards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.mutualmobile.composesensors.SensorDelay
import com.mutualmobile.composesensors.rememberGravitySensorState
import com.tpc.pokemontradingcards.domain.CardSize
import com.tpc.pokemontradingcards.domain.PokemonCardData
import com.tpc.pokemontradingcards.domain.PokemonCardDataEmpty
import com.tpc.pokemontradingcards.ui.theme.PokemonTradingCardsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    data: PokemonCardData,
    canBeRotated: Boolean = false,
    onClick: () -> Unit
) {
    val accelerometerState = rememberGravitySensorState(sensorDelay = SensorDelay.UI)
    var isShimmerVisible by remember { mutableStateOf(true) }

    val colors1 = listOf(
        Color(255, 119, 115),
        Color(255, 237, 95),
        Color(168, 255, 95),
        Color(131, 255, 247),
        Color(120, 148, 255),
        Color(216, 117, 255),
        Color(255, 119, 115)
    )

    val brushs =
        listOf(
            Brush.verticalGradient(
                colors = colors1,
                tileMode = TileMode.Repeated,
            ),
            Brush.linearGradient(
                0f to Color(14, 21, 46),
                0.38f to Color(143, 163, 163),
                0.45f to Color(143, 193, 193),
                0.52f to Color(143, 163, 163),
                0.1f to Color(14, 21, 46),
                tileMode = TileMode.Repeated,
            )
        )



    Card(
        modifier = modifier
            .defaultMinSize(
                minWidth = 190.dp, minHeight = 200.dp
            )
            .placeholder(
                color = Color.DarkGray,
                visible = isShimmerVisible,
                highlight = PlaceholderHighlight.fade(),
            )
            .graphicsLayer {
                if (canBeRotated) {
                    rotationX = accelerometerState.yForce * 2
                    rotationY = accelerometerState.xForce * 2
                }
            }
            .drawWithContent {
                drawContent()
                if (canBeRotated) {
                    //drawRect(brush = brushs.last(), blendMode = BlendMode.Multiply)
                    //drawRect(brush = brushs.first(), blendMode = BlendMode.Multiply)
                }
            },
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.images.large)
                .crossfade(500)
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCacheKey(data.id)
                .listener { request, result ->
                    isShimmerVisible = false
                }.build(),
            contentDescription = data.name,
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
            PokemonCard(
                modifier = Modifier.align(Alignment.Center),
                data = PokemonCardDataEmpty.copy(
                    images = CardSize(
                        "",
                        "https://images.pokemontcg.io/swsh12pt5/160_hires.png"
                    )
                ),
                canBeRotated = true,
                onClick = {

                }
            )
        }
    }
}
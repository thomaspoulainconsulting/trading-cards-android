package com.tpc.pokemontradingcards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier, data: PokemonCardData
) {
    var isShimmerVisible by remember { mutableStateOf(true) }

    // Get a sensor
    val accelerometerState =
        rememberGravitySensorState(sensorDelay = SensorDelay.UI)

    // Accelerometer = intéressant ! mais peu fluide
    // Magnetic field = no
    // Gyroscope = bof, valeur qui se cap rapidement
    // Gravity = interessant ! mais ça penche vers la gauche
    // Linear Acceleration = no
    // Rotation Vector = no
    // Uncalibred magnetic field = no
    // Game Rotation Vector = no
    // Geometric rotation vector = no

    val x = accelerometerState.xForce
    val y = accelerometerState.yForce

    /*Text(
        text = """
            x: $x
            y: $y
            z: $z
        """.trimIndent(),
        color = Color.White
    )*/

    Card(
        modifier = modifier
            .defaultMinSize(
                minWidth = 190.dp, minHeight = 264.dp
            ) // Default value for showing loading card
            .placeholder(
                color = Color.DarkGray,
                visible = isShimmerVisible,
                highlight = PlaceholderHighlight.fade(),
            )
            .graphicsLayer {
                rotationX = y / 2
                rotationY = x * 2
            },
        shape = RoundedCornerShape(13.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(data.images.large)
                .crossfade(durationMillis = 500).listener { request, result ->
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
                )
            )
        }
    }
}
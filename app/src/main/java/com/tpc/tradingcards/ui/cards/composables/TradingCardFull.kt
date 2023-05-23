package com.tpc.tradingcards.ui.cards.composables

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.tpc.tradingcards.R
import com.tpc.tradingcards.core.extention.debugPlaceholder
import com.tpc.tradingcards.core.ui.theme.DefaultTradingCardShape
import com.tpc.tradingcards.core.ui.theme.ShaderChromaticAberration
import com.tpc.tradingcards.core.ui.theme.TradingCardsTheme
import com.tpc.tradingcards.core.ui.theme.mediumElevation
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardEmpty
import kotlinx.coroutines.delay

@Composable
fun TradingCardFull(
    modifier: Modifier = Modifier, data: Card
) {
    var urlToLoad by remember { mutableStateOf(data.urlSmall) }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(urlToLoad).build(),
        placeholder = debugPlaceholder(debugPreview = R.drawable.debug_card_placeholder),
        onSuccess = {
            if (urlToLoad == data.urlSmall) {
                urlToLoad = data.urlLarge
            }
        })

    // Shader and animation
    val shader by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(RuntimeShader(ShaderChromaticAberration))
        } else mutableStateOf(null)
    }
    var isAnimationPlayed by remember { mutableStateOf(false) }
    var isAnimationFinished by remember { mutableStateOf(false) }
    val springAnimation by animateFloatAsState(
        targetValue = if (isAnimationFinished) 0f else if (isAnimationPlayed) 20f else -20f,
        label = "chromatic spring animation",
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
        finishedListener = {
            isAnimationFinished = true
        }
    )

    // Trigger the start of the animation
    LaunchedEffect(Unit) {
        delay(150)
        isAnimationPlayed = true
    }

    Card(
        modifier = modifier,
        shape = DefaultTradingCardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = mediumElevation)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(250.dp, 344.dp) // Magic Number, but why not
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        shader?.let { currentShader ->
                            currentShader.setFloatUniform("amount", springAnimation)
                            clip = true
                            renderEffect = RenderEffect
                                .createRuntimeShaderEffect(currentShader, "composable")
                                .asComposeRenderEffect()
                        }
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonCardPreview() {
    TradingCardsTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TradingCardFull(
                modifier = Modifier.align(Alignment.Center), data = CardEmpty
            )
        }
    }
}
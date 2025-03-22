package com.tpc.tradingcards.ui.details.ui.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tpc.tradingcards.data.model.Card
import kotlin.math.abs

@Composable
fun TradingCardFull(card: Card) {
    var targetRotationX by remember { mutableFloatStateOf(0f) }
    var targetRotationY by remember { mutableFloatStateOf(0f) }

    // Animate rotation using animateFloatAsState
    val animatedRotationX by animateFloatAsState(
        targetValue = targetRotationX,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "rotationX"
    )

    val animatedRotationY by animateFloatAsState(
        targetValue = targetRotationY,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "rotationY"
    )

    // Calculate intensity based on card rotation
    val tiltIntensity = remember(animatedRotationX, animatedRotationY) {
        // Normalize rotation values to get intensity between 0 and 1
        val xFactor = abs(animatedRotationX) / 10f
        val yFactor = abs(animatedRotationY) / 10f
        (xFactor + yFactor).coerceIn(0f, 1f)
    }

    // Animate the intensity smoothly
    val animatedIntensity by animateFloatAsState(
        targetValue = tiltIntensity,
        animationSpec = tween(150),
        label = "intensity"
    )

    val rotationLimitX = 10f
    val rotationLimitY = 10f

    val cardWidth = 300.dp
    val cardHeight = 420.dp

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF121212).copy(alpha = 0.8f))
    ) {
        // Card with effects
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(cardWidth, cardHeight)
                .graphicsLayer {
                    this.rotationX = animatedRotationX
                    this.rotationY = animatedRotationY
                    cameraDistance = 15f
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            targetRotationX = 0f
                            targetRotationY = 0f
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val sensitivityX = 0.2f
                            val sensitivityY = 0.2f
                            targetRotationY = (targetRotationY + dragAmount.x * sensitivityY).coerceIn(-rotationLimitY, rotationLimitY)
                            targetRotationX = (targetRotationX - dragAmount.y * sensitivityX).coerceIn(-rotationLimitX, rotationLimitX)
                        }
                    )
                }
        ) {
            // Card image
            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = card.urlLarge,
                    contentDescription = card.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Overlay with just the halo effect
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        // Only draw the effect based on intensity
                        if (animatedIntensity > 0) {
                            // Position du halo qui se déplace selon la rotation
                            val haloOffsetX = size.width * 0.5f + (animatedRotationY / rotationLimitY) * size.width * 0.4f
                            val haloOffsetY = size.height * 0.5f - (animatedRotationX / rotationLimitX) * size.height * 0.4f

                            // Créons un dégradé radial pour l'effet de halo
                            val haloGradient = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.4f * animatedIntensity),
                                    Color.White.copy(alpha = 0.2f * animatedIntensity),
                                    Color.White.copy(alpha = 0.1f * animatedIntensity),
                                    Color.White.copy(alpha = 0f)
                                ),
                                center = Offset(haloOffsetX, haloOffsetY),
                                radius = size.minDimension * 0.5f
                            )

                            // Dessiner le halo
                            drawRect(
                                brush = haloGradient,
                                blendMode = BlendMode.Screen
                            )
                        }
                    }
            )
        }
    }
}
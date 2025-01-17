package com.tpc.tradingcards.ui.details.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tpc.tradingcards.core.ui.theme.Dark80
import com.tpc.tradingcards.data.model.Card
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun TradingCardFull(
    card: Card,
) {
    // États animés pour les transitions fluides
    val animatedRotationX = remember { Animatable(0f) }
    val animatedRotationY = remember { Animatable(0f) }
    val animatedRotationZ = remember { Animatable(0f) }

    // Limites de rotation personnalisées pour chaque axe
    val rotationLimitX = 8f
    val rotationLimitY = 8f
    val rotationLimitZ = 8f

    val coroutineScope = rememberCoroutineScope()

    // Créer une source d'interaction personnalisée pour supprimer le ripple
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { /* Ne rien faire */ }
            )
            .background(Dark80.copy(alpha = 0.8f))
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Transparent)
                .graphicsLayer {
                    // Application des rotations avec les limites
                    this.rotationX = animatedRotationX.value.coerceIn(-rotationLimitX, rotationLimitX)
                    this.rotationY = animatedRotationY.value.coerceIn(-rotationLimitY, rotationLimitY)
                    this.rotationZ = animatedRotationZ.value.coerceIn(-rotationLimitZ, rotationLimitZ)

                    // Perspective améliorée
                    cameraDistance = 12f
                }
                .drawWithCache {
                    onDrawWithContent {
                        // Dessiner d'abord le contenu normal
                        drawContent()

                        // Calculer l'intensité de la brillance basée sur les angles de rotation
                        val intensity = (abs(animatedRotationX.value) + abs(animatedRotationY.value)) / 50f
                        val alpha = (intensity * 0.3f).coerceIn(0f, 0.3f)

                        // Créer un effet de brillance dynamique
                        val gradientBrush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0f),
                                Color.White.copy(alpha = alpha),
                                Color.White.copy(alpha = 0f)
                            ),
                            start = Offset(
                                x = size.width * (0.5f - animatedRotationY.value / 50f),
                                y = size.height * (0.5f - animatedRotationX.value / 50f)
                            ),
                            end = Offset(
                                x = size.width * (0.5f + animatedRotationY.value / 50f),
                                y = size.height * (0.5f + animatedRotationX.value / 50f)
                            ),
                            tileMode = TileMode.Clamp
                        )

                        // Appliquer l'effet de brillance
                        drawRect(brush = gradientBrush)
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { },
                        onDragEnd = {
                            // Animation de retour à la position initiale avec rebond
                            coroutineScope.launch {
                                launch { animatedRotationX.animateTo(0f) }
                                launch { animatedRotationY.animateTo(0f) }
                                launch { animatedRotationZ.animateTo(0f) }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            // Facteurs de sensibilité personnalisés pour chaque axe
                            val sensitivityX = 0.6f
                            val sensitivityY = 0.6f
                            val sensitivityZ = 0.3f

                            coroutineScope.launch {
                                // Mise à jour des rotations avec différentes sensibilités
                                launch {
                                    animatedRotationY.animateTo(
                                        animatedRotationY.value + dragAmount.x * sensitivityY
                                    )
                                }
                                launch {
                                    animatedRotationX.animateTo(
                                        animatedRotationX.value - dragAmount.y * sensitivityX
                                    )
                                }

                                // Rotation Z plus subtile basée sur le mouvement diagonal
                                val diagonal = dragAmount.x * dragAmount.y
                                launch {
                                    animatedRotationZ.animateTo(
                                        animatedRotationZ.value + diagonal * sensitivityZ * 0.01f
                                    )
                                }
                            }
                        }
                    )
                }
        ) {
            AsyncImage(
                model = card.urlLarge,
                contentDescription = null,
                modifier = Modifier.width(300.dp)
            )
        }
    }
}
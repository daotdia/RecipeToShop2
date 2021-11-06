package com.otero.recipetoshop.android.presentation.components.recipes

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor

@Composable
fun LoadingRecipeListShimmer(
    imageHeight: Dp,
    padding: Dp = 16.dp
){
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val cardWithPx = with(LocalDensity.current){(maxWidth - (padding*2)).toPx()}
        val cardHeightPx = with(LocalDensity.current){(imageHeight - padding).toPx()}
        val gradientWidth: Float = (0.2f*cardHeightPx)

        val infinitTransition = rememberInfiniteTransition()
        val xCardShimmer = infinitTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardWithPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val yCardShimmer = infinitTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardHeightPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val colors = listOf(
            secondaryLightColor.copy(alpha=.9f),
            secondaryLightColor.copy(alpha = .3f),
            secondaryLightColor.copy(alpha = .9f)
        )

        LazyColumn{
            items(5){
                ShimmerRecipeCardItem(
                    colors = colors,
                    xShimmer = xCardShimmer.value,
                    yShimmer = yCardShimmer.value,
                    cardHeight = imageHeight,
                    gradientWidth = gradientWidth,
                    padding = padding
                )
            }
        }
    }
}
package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
/*
Efecto de imagen que simula un smbreado dinámico mientras se cargan las cards.
 */
@Composable
fun GradientDemo() {
    val colors = listOf(
        secondaryLightColor.copy(alpha=.9f),
        secondaryLightColor.copy(alpha = .3f),
        secondaryLightColor.copy(alpha = .9f)
    )

    val brush = linearGradient(
        colors,
        start = Offset(200f,200f),
        end = Offset(400f,400f)
    )

    Surface(shape = MaterialTheme.shapes.small){
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}
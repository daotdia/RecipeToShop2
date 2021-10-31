package com.otero.recipetoshop.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.*

private val LightThemeColors = lightColors(
    primary = primaryColor,
    primaryVariant = primaryLightColor,
    onPrimary = primaryTextColor,
    secondary = secondaryColor,
    secondaryVariant = secondaryDarkColor,
    onSecondary = secondaryTextColor,
    error = complementaryColor,
    onError = primaryTextColor,
    background = secondaryLightColor,
    onBackground = primaryTextColor,
    surface = Color.White,
    onSurface = primaryTextColor
)

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AppTheme(
    displayProgressBar: Boolean,
    onRemoveHeadMessageFromQueue: () -> Unit,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = LoraTypography,
        shapes = appShapes
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = secondaryDarkColor)
        ){
            content()
        }
    }
}
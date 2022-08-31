package com.otero.recipetoshop.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.otero.recipetoshop.android.presentation.components.errorhandle.ProcessDialogQueue
import com.otero.recipetoshop.android.presentation.components.util.CircularIndeterminateProgressBar
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.dataEstructres.Queue

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
    dialogQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
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
            ProcessDialogQueue(
                dialogQueue = dialogQueue,
                onRemoveHeadMessageFromQueue = onRemoveHeadMessageFromQueue
            )
            content()
            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar, verticalBias = 0.3f)
        }
    }
}
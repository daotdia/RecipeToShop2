package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
/*
Este componente puede ser utilizado para añadir un Modifier de eliminar con Swipe.
 */
@ExperimentalMaterialApi
@Composable
fun rememberDeleteState(
    onDelete: () -> Unit
): DismissState {
    return rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                onDelete()
                true
            } else false
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun SimpleModifierSwipeToDelete(
    state: DismissState,
    content: @Composable RowScope.() -> Unit
){
    SwipeToDismiss(
        state = state,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
        },
        background = {
            val direction = state.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (state.targetValue) {
                    DismissValue.Default -> secondaryLightColor.copy(alpha = 0.5f)
                    else -> Color.Red.copy(alpha = 0.70f)
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val scale by animateFloatAsState(
                if (state.targetValue == DismissValue.Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove item",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = content
    )
}
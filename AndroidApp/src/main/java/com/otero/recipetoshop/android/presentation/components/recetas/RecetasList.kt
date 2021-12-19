package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.events.recetas.RecetaListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun RecetasList(
    stateListaRecetas: MutableState<RecetasListState>,
    onTriggeEvent: (RecetaListEvents) -> Unit,
    color: Color = Color.Transparent,
    offset: Dp = 48.dp
) {
    LazyColumn(
        modifier = Modifier
            .offset(y = offset)
            .padding(start = 4.dp, bottom = 4.dp, end = 4.dp, top = 12.dp)
            .background(color = color),
    ) {
        items(stateListaRecetas.value.recetas, { listItem: Receta -> listItem.id_Receta!! }) { item ->
            //Si no es un alimento, es decir su tipo es null; por tanto es una receta.
            var delete by remember { mutableStateOf(false) }
            val dismissStateReceta = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) delete = !delete
                    it != DismissValue.DismissedToStart
                }
            )
            SwipeToDismiss(
                state = dismissStateReceta,
                modifier = Modifier.padding(vertical = 4.dp),
                directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                dismissThresholds = { direction ->
                    FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
                },
                background = {
                    val direction = dismissStateReceta.dismissDirection ?: return@SwipeToDismiss
                    val color by animateColorAsState(
                        when (dismissStateReceta.targetValue) {
                            DismissValue.Default -> Color.LightGray
                            DismissValue.DismissedToEnd -> Color.Green
                            DismissValue.DismissedToStart -> Color.Red
                        }
                    )
                    val alignment = when (direction) {
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                    }
                    val icon = when (direction) {
                        DismissDirection.StartToEnd -> Icons.Default.Done
                        DismissDirection.EndToStart -> Icons.Default.Delete
                    }
                    val scale by animateFloatAsState(
                        if (dismissStateReceta.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    Box(
                        Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Localized description",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    if (delete) {
                        onTriggeEvent(RecetaListEvents.onDeleteReceta(item))
                    }
                    RecetaCard(
                        receta = item,
                        onCantidadChange = {
                            onTriggeEvent(
                                RecetaListEvents.onCantidadRecetaChange(
                                    cantidad = it.toInt(),
                                    receta = item
                                )
                            )
                        },
                        elevation = 4.dp
                    )
                }
            )
        }
    }
}

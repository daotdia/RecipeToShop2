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
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.despensa.FoodCard
import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun RecetasList(
    stateListaRecetas: MutableState<RecetasListState>,
    onTriggeEvent: (RecetasListEvents) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
        ,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NestedDownMenu(
            options = listOf("Eliminar Despensa"),
            onClickItem = { onTriggeEvent(RecetasListEvents.onDeleteRecetas(it)) }
        )
    }

    LazyColumn(
        modifier = Modifier
            .offset(y = 48.dp)
            .padding(1.dp),
    ) {
        items(stateListaRecetas.value.recetas, {listItem: Receta -> listItem.nombre}) { item ->
            //Si no es un alimento, es decir su tipo es null; por tanto es una receta.
            if(item.tipo == null){
                var delete by remember { mutableStateOf(false) }
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) delete = !delete
                        it != DismissValue.DismissedToStart
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier.padding(vertical = 4.dp),
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
                    },
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
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
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
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
                        if(delete){
                            onTriggeEvent(RecetasListEvents.onDeleteReceta(item))
                        }
                        RecetaCard(
                            receta = item,
                            onCantidadChange = {
                                onTriggeEvent(
                                    RecetasListEvents.onCantidadRecetaChange(
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
}
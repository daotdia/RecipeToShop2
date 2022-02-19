package com.otero.recipetoshop.android.presentation.components.despensa

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.events.despensa.DespensaEventos
import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.presentationlogic.states.despensa.ListaAlimentosState


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AlimentosDespensaLista (
    listState: MutableState<ListaAlimentosState>,
    onTriggeEvent: (DespensaEventos) -> Unit,
) {
    val onNewFood = remember { mutableStateOf(false)}
    //El contenido lo organizo en un scaffold para poner facilmente floatingbutton
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNewFood.value = true
                },
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        //PopUp de la ventana para crear nuevo alimento.
        if (onNewFood.value) {
            NewAlimentoPopUp(
                onAddAlimento = { nombre, tipo, cantidad ->
                    onTriggeEvent(
                        DespensaEventos.onAddAlimento(
                            nombre = nombre,
                            tipo = tipo,
                            cantidad = cantidad
                        )
                    )
                },
                onNewAlimento = onNewFood,
            )
        }
    }
    //Menu de la lista para controlar las cards de alimentos.
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
            onClickItem = { onTriggeEvent(DespensaEventos.onSelectedNestedMenuItem(it)) }
        )
    }

    LazyColumn(
        modifier = Modifier
            .offset(y = 48.dp)
            .padding(1.dp),
    ) {
        items(listState.value.allAlimentos, {listItem: Alimento -> listItem.id_alimento!!}) { item ->
            var delete by remember { mutableStateOf(false) }
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) delete = !delete
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
                            DismissValue.DismissedToEnd -> Color.Red
                            DismissValue.DismissedToStart -> Color.Red
                        }
                    )
                    val alignment = when (direction) {
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                    }
                    val icon = when (direction) {
                        DismissDirection.StartToEnd -> Icons.Default.Delete
                        DismissDirection.EndToStart -> Icons.Default.Delete
                    }
                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
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
                        onTriggeEvent(DespensaEventos.onAlimentoDelete(item))
                    }
                    FoodCard(
                        alimento = item,
                        onCantidadChange = {
                            onTriggeEvent(
                                DespensaEventos.onCantidadChange(
                                    cantidad = it,
                                    alimento = item
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

//    //Crear la lista de items.
//    LazyColumn(
//        modifier = Modifier
//            .offset(y = 48.dp)
//            .padding(1.dp),
//    ){
//        items(
//            items = listState.value.alimentos,
//            itemContent = { item ->
//                RevealSwipe(
//                    modifier = Modifier
//                        .padding(vertical = 5.dp),
//                    directions = setOf(
//                        RevealDirection.EndToStart
//                    ),
//                    hiddenContentEnd = {
//                        Icon(
//                            modifier = Modifier
//                                .padding(horizontal = 25.dp)
//                                .clickable {onTriggeEvent(DespensaEventos.onAlimentoDelete(item))}
//                            ,
//                            imageVector = Icons.Outlined.Delete,
//                            contentDescription = null
//                        )
//                    }
//                ) {
//                    FoodCard(
//                        alimento = item,
//                        onCantidadChange = {
//                            onTriggeEvent(
//                                DespensaEventos.onCantidadChange(
//                                    cantidad = it,
//                                    alimento = item
//                                )
//                            )
//                        },
//                        elevation = 4.dp
//                    )
//                }
//            }
//        )
//    }
//}
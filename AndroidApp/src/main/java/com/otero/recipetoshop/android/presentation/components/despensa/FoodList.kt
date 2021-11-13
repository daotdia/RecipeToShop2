package com.otero.recipetoshop.android.presentation.components.despensa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.presentattion.screens.despensa.FoodListState


@ExperimentalComposeUiApi
@Composable
fun FoodList (
    listState: MutableState<FoodListState>,
    onTriggeEvent: (FoodListEvents) -> Unit
) {
    //El contenido lo organizo en un scaffold para poner facilmente floatingbutton
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    listState.value = listState.value.copy(onNewFood = true)
                },
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        //PopUp de la ventana para crear nuevo alimento.
        if (listState.value.onNewFood) {
            NewFoodPopUp(
                onAddFood = { nombre, tipo, cantidad ->
                    onTriggeEvent(
                        FoodListEvents.onAddFood(
                            nombre = nombre,
                            tipo = tipo,
                            cantidad = cantidad
                        )
                    )
                },
                listState = listState,
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
            onClickItem = { onTriggeEvent(FoodListEvents.onSelectedNestedMenuItem(it)) }
        )
    }
    //Crear la lista de items.
    LazyColumn(
        modifier = Modifier.offset(y = 48.dp)
    ) {
        itemsIndexed(
            items = listState.value.alimentos
        ) { index, item ->
            FoodCard(
                food = item,
                onCantidadChange = {
                    onTriggeEvent(
                        FoodListEvents.onCantidadChange(
                            cantidad = it,
                            food = item
                        )
                    )
                }
            )
        }
    }
}

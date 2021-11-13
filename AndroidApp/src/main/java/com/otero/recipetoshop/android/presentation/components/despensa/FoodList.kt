package com.otero.recipetoshop.android.presentation.components.despensa

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.otero.recipetoshop.Interactors.despensa.FoodListEvents
import com.otero.recipetoshop.android.presentation.theme.analogousColorBlue
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentattion.screens.despensa.FoodListState


@ExperimentalComposeUiApi
@Composable
fun FoodList (
    listState: MutableState<FoodListState>,
    onTriggeEvent: (FoodListEvents) -> Unit
){
    //El contenido lo organizo en un scaffold para poner facilmente floatingbutton
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    listState.value = listState.value.copy(onNewFood = true)
                },
                backgroundColor = analogousColorBlue,
                contentColor = secondaryLightColor
            ){
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        //PopUp de la ventana para crear nuevo alimento.
        if(listState.value.onNewFood){
            Dialog(
                onDismissRequest = {
                    listState.value = listState.value.copy(onNewFood = false)
                }
            ) {
                NewFoodPopUp(
                    onAddFood = { nombre,tipo,cantidad ->
                        onTriggeEvent(FoodListEvents.onAddFood(
                            nombre = nombre,
                            tipo = tipo,
                            cantidad = cantidad
                        ))
                    }
                )
            }
        }
        //Crear la lista de items.
        LazyColumn(){
            itemsIndexed(
                items = listState.value.alimentos
            ){ index, item ->
                FoodCard(
                    food = item,
                    onCantidadChange = {
                        onTriggeEvent(FoodListEvents.onCantidadChange(
                            cantidad = it,
                            food = item)
                        )
                    }
                )
            }
        }
    }


}
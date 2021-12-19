package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.despensa.NewFoodPopUp
import com.otero.recipetoshop.android.presentation.components.util.BackLayerBackDrop
import com.otero.recipetoshop.android.presentation.components.util.MenuItemBackLayer
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.recetas.RecetaListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BackDropListaItemsListaDeRecetas(
    stateListaRecetas: MutableState<RecetasListState>,
    onTriggeEventReceta: (RecetaListEvents) -> Unit,
) {
    val backdropScaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    val rutaActual = remember { mutableStateOf("recetas") }
    val dialogElement = remember { mutableStateOf(false) }
    val dialogSaveReceta = remember { mutableStateOf(false) }


    val recetaFocused = remember { mutableStateOf(true) }
    val alimentosFocused = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .offset(x = -156.dp, y = -132.dp)
                ,
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
                onClick = {
                    dialogElement.value = true
                },
                content = {
                    Icon(Icons.Default.Add, null)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        val coroutineScope = rememberCoroutineScope()
        BackdropScaffold(
            appBar = {},
            scaffoldState = backdropScaffoldState,
            peekHeight = 66.dp,
            stickyFrontLayer = false,
            headerHeight = 66.dp,
            frontLayerShape = BackdropScaffoldDefaults.frontLayerShape,
            frontLayerElevation = BackdropScaffoldDefaults.FrontLayerElevation,
            frontLayerScrimColor = Color.Unspecified,
            backLayerContent = {
                if(backdropScaffoldState.isRevealed){
                    rutaActual.value = "recetas"
                }
                BackLayerBackDrop(
                    menuItem = MenuItemBackLayer("Recetas", recetaFocused, "recetas"),
                    onMenuItemClick = {
                        recetaFocused.value = false
                        alimentosFocused.value = false
                        rutaActual.value = it
                        if (backdropScaffoldState.isConcealed) {
                            coroutineScope.launch { backdropScaffoldState.reveal() }
                        }
                    },
                    stateListaRecetas = stateListaRecetas,
                    onTriggeEventReceta = onTriggeEventReceta,
                    scaffoldState = backdropScaffoldState,
                    coroutineScope = coroutineScope,
                    updateRuta =  {
                        rutaActual.value = "recetas"
                    }
                )
            },
            frontLayerContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clickable(onClick = {
                            rutaActual.value = "alimentos"
                            if (backdropScaffoldState.isRevealed) {
                                coroutineScope.launch { backdropScaffoldState.conceal() }
                            }
                        })
                ) {
                    if(backdropScaffoldState.isConcealed){
                        rutaActual.value = "alimentos"
                    }
                    AlimentosList(
                        stateListaRecetas = stateListaRecetas,
                        onTriggeEvent = onTriggeEventReceta,
                        scaffoldState = backdropScaffoldState,
                        coroutineScope = coroutineScope,
                        menuItem = MenuItemBackLayer("Alimentos", alimentosFocused, "alimentos"),
                        onMenuItemClick = {
                            recetaFocused.value = false
                            alimentosFocused.value = false
                            rutaActual.value = it
                            if (backdropScaffoldState.isRevealed) {
                                coroutineScope.launch { backdropScaffoldState.conceal() }
                            }
                        }
                    )
                }
            },
            backLayerBackgroundColor = Color.Transparent,
            backLayerContentColor = Color.Transparent,
            frontLayerBackgroundColor = secondaryLightColor,
        ) {
            if (dialogElement.value) {
                if (rutaActual.value.equals("recetas")) {
                    NewRecetaPopUp(
                        onAddReceta = { nombre, cantidad ->
                            onTriggeEventReceta(
                                RecetaListEvents.onAddUserReceta(
                                    nombre = nombre,
                                    cantidad = cantidad.toInt()
                                )
                            )
                        },
                        onNewReceta = dialogElement
                    )
                } else {
                    NewFoodPopUp(
                        onAddFood = { nombre, tipo, cantidad ->
                            onTriggeEventReceta(
                                RecetaListEvents.onAddAlimento(
                                    nombre = nombre,
                                    cantidad = cantidad.toInt(),
                                    tipoUnidad = TipoUnidad.valueOf(tipo)
                                )
                            )
                        },
                        onNewFood = dialogElement,
                    )
                }
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
        ) {
            FloatingActionButton(
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
                onClick = {
                    dialogSaveReceta.value = true
                },
                modifier = Modifier
                    .offset(x = 22.dp, y = -76.dp)
            ) {
                Icon(Icons.Default.Done, "Guardar Lista Recetas")
            }
        }
    }
}
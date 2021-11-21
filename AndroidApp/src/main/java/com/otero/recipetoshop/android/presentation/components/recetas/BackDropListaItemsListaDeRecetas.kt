package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.despensa.NewFoodPopUp
import com.otero.recipetoshop.android.presentation.components.util.BackLayerBackDrop
import com.otero.recipetoshop.android.presentation.components.util.MenuItemBackLayer
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.secondaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BackDropListaItemsListaDeRecetas(
    stateListaRecetas: MutableState<RecetasListState>,
    onTriggeEventReceta: (RecetasListEvents) -> Unit,
){
    val backdropScaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    val coroutineScope = rememberCoroutineScope()
    val rutaActual = remember {mutableStateOf("recetas")}
    val dialogElement = remember { mutableStateOf(false)}

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = secondaryDarkColor,
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
        BackdropScaffold(
            appBar = {
                Surface(
                    color = Color.Transparent,
                    onClick = {
                        if (backdropScaffoldState.isRevealed) {
                            coroutineScope.launch { backdropScaffoldState.conceal() }
                        } else if (backdropScaffoldState.isConcealed) {
                            coroutineScope.launch { backdropScaffoldState.reveal() }
                        }
                    },
                ) {
                    if(backdropScaffoldState.isRevealed){
                        Icon(Icons.Default.ExpandLess, null)
                    } else {
                        Icon(Icons.Default.ExpandMore, null)
                    }
                }
            },
            scaffoldState = backdropScaffoldState,
            peekHeight = BackdropScaffoldDefaults.PeekHeight,
            stickyFrontLayer = true,
            headerHeight = BackdropScaffoldDefaults.HeaderHeight,
            frontLayerShape = BackdropScaffoldDefaults.frontLayerShape,
            frontLayerElevation = BackdropScaffoldDefaults.FrontLayerElevation,
            frontLayerScrimColor = Color.Unspecified,
            backLayerContent = {
                val recetaFocused = remember {mutableStateOf(true)}
                val alimentosFocused = remember {mutableStateOf(false)}

                BackLayerBackDrop(
                    menuItems = listOf(
                        MenuItemBackLayer("Recetas", recetaFocused, "recetas"),
                        MenuItemBackLayer("Alimentos", alimentosFocused, "alimentos")
                    ),
                    onMenuItemClick = {
                        recetaFocused.value = false
                        alimentosFocused.value = false
                        rutaActual.value = it
                    }
                )
            },
            frontLayerContent = {
                when(rutaActual.value){
                    "recetas" -> {
                        RecetasList(
                            stateListaRecetas = stateListaRecetas,
                            onTriggeEvent = onTriggeEventReceta
                        )
                    }
                    "alimentos" -> {
                        AlimentosList(
                            stateListaRecetas = stateListaRecetas,
                            onTriggeEvent = onTriggeEventReceta
                        )
                    }
                }
            },
            backLayerBackgroundColor = primaryColor,
            backLayerContentColor = secondaryLightColor,
            frontLayerBackgroundColor = secondaryLightColor,
        ) {
            if(dialogElement.value){
                if(rutaActual.value.equals("recetas")){
                    NewFoodPopUp(
                        onAddFood = { nombre, tipo, cantidad ->
                            onTriggeEventReceta(
                                RecetasListEvents.onAddAlimento(
                                    nombre = nombre,
                                    cantidad = cantidad.toInt(),
                                    tipoUnidad = TipoUnidad.valueOf(tipo)
                                )
                            )
                        },
                        onNewFood = dialogElement,
                    )
                }else {
                    NewRecetaPopUp(
                        onAddReceta = { nombre, cantidad ->
                            onTriggeEventReceta(
                                RecetasListEvents.onAddReceta(
                                    nombre = nombre,
                                    cantidad = cantidad.toInt()
                                )
                            )
                        },
                        onNewReceta = dialogElement
                    )
                }
            }
            val dialogSaveReceta = remember { mutableStateOf(false)}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(2.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
            ) {
                FloatingActionButton(
                    backgroundColor = secondaryDarkColor,
                    contentColor = secondaryLightColor,
                    onClick = {
                              dialogSaveReceta.value = true
                    },
                ){
                    Icon(Icons.Default.Save, "Guardar Lista Recetas")
                }
            }
            if(dialogSaveReceta.value){
                NewListaRecetaPopUp(
                    onAddListaReceta = { nombre ->
                        onTriggeEventReceta(RecetasListEvents.onAddListaReceta(nombre = nombre))
                    },
                    onNewListaReceta = dialogSaveReceta)
            }
        }
    }
}
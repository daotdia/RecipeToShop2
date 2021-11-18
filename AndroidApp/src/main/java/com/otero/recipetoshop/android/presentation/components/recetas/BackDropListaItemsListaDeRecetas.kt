package com.otero.recipetoshop.android.presentation.components.recetas

import android.hardware.TriggerEvent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.otero.recipetoshop.android.presentation.components.despensa.FoodList
import com.otero.recipetoshop.android.presentation.components.util.BackLayerBackDrop
import com.otero.recipetoshop.android.presentation.components.util.MenuItemBackLayer
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.secondaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.model.recetas.toFood
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import com.otero.recipetoshop.presentattion.screens.despensa.FoodListState
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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = secondaryDarkColor,
                contentColor = secondaryLightColor,
                onClick = {
                    onTriggeEventReceta(RecetasListEvents.onAddreceta(

                    ))
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
                        } else {}
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
                        //Obtengo de los items aquellos que son alimentos, es decir su tipo no es null.
                        val foods: ArrayList<Receta> = arrayListOf()
                        stateListaRecetas.value.recetas.forEach{
                            if(it.tipo != null){
                                foods.add(it)
                            }
                        }
                        val foodListState = remember { mutableStateOf(RecetasListState())}
                        foodListState.value = foodListState.value.copy(recetas = foods)

                        AlimentosList(
                            listState = foodListState,
                            onTriggeEvent = onTriggeEventReceta
                        )
                    }
                }
            },
            backLayerBackgroundColor = primaryColor,
            backLayerContentColor = secondaryLightColor,
            frontLayerBackgroundColor = secondaryLightColor,
        ) {

        }
    }
}
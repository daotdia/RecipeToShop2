package com.otero.recipetoshop.android.presentation.components.recetas

import android.hardware.TriggerEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.despensa.FoodList
import com.otero.recipetoshop.android.presentation.components.util.BackLayerBackDrop
import com.otero.recipetoshop.android.presentation.components.util.MenuItemBackLayer
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.secondaryColor
import com.otero.recipetoshop.android.presentation.theme.secondaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaRecetas(
    onTriggerEvent: (RecetasListEvents) -> Unit
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
                    onTriggerEvent(RecetasListEvents.onAddreceta(

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

                        )
                    }
                    "alimentos" -> {
                        FoodList(
                            listState = ,
                            onTriggeEvent =
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
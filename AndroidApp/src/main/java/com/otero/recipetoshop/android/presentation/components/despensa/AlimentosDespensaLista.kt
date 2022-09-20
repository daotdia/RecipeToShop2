package com.otero.recipetoshop.android.presentation.components.despensa

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.presentationlogic.events.despensa.DespensaEventos
import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
import com.otero.recipetoshop.android.presentation.components.util.cards.IngredienteCard
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.presentationlogic.states.despensa.ListaAlimentosState

/*
Este es el componente que implementa la lista de alimentos disponibles en despensa.
 */
@ExperimentalFoundationApi
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
            AlimentoPopUp(
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
                autocompleteResults = listState.value.resultadoAutoCompletado,
                onAutoCompleteChange = { autocomplete ->
                    onTriggeEvent(
                        DespensaEventos.onAutoCompleteChange(
                            nombre = autocomplete
                        )
                    )
                },
                onAutoCompleteClick = {
                    onTriggeEvent(
                        DespensaEventos.onClickAutoCompleteElement()
                    )
                }
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

    LazyVerticalGrid(
        modifier = Modifier
            .offset(y = 48.dp)
            .padding(1.dp),
        cells = GridCells.Fixed(3)
    ) {
        items(listState.value.allAlimentos) { item ->
            var delete by remember { mutableStateOf(false) }
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) delete = !delete
                    it != DismissValue.DismissedToStart
                }
            )
            IngredienteCard(
                nombre = item.nombre,
                onClickAlimento = { id_alimento, nombre, tipo, cantidad ->
                    //Llamo a viewmodel para que edite el alimento con los eventos de la Despensa.
                    onTriggeEvent(
                        DespensaEventos.onEditaAlimento(id_alimento, nombre, tipo, cantidad)
                    )
                },
                cantidad = item.cantidad,
                onDelete = {
                    onTriggeEvent(
                        DespensaEventos.onAlimentoDelete(
                            alimento = item
                        )
                    )
                },
                alimento_actual = item,
                tipoUnidad = item.tipoUnidad
            )
        }
    }
}

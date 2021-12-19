package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.events.recetas.ListOfRecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.ListOfRecetasListState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaDeListaRectas(
    navController: NavController,
    recetasState: MutableState<ListOfRecetasListState>,
    onTriggeEvent: (ListOfRecetasListEvents) -> Any,
) {
    val dialogElement = remember { mutableStateOf(false)}

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogElement.value = true
                },
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        if(dialogElement.value){
            NewListaRecetaPopUp(
                onTriggerEvent = onTriggeEvent,
//                onAddListaReceta = { nombre ->
//                    onTriggeEvent(ListOfRecetasListEvents.onAddListaReceta(nombre = nombre))
//                },
                onNewListaReceta = dialogElement,
                navController = navController
            )
        }
        val readOnly = remember { mutableStateOf(false) }
        //Crear la lista de items.
        LazyColumn(
            modifier = Modifier
                .offset(y = 48.dp)
                .padding(1.dp),
        ) {
            items(
                items = recetasState.value.listaDeListasRecetas,
                { listItem: ListaRecetas -> listItem.id_listaRecetas!! }
            )
            { item ->
                RevealSwipe(
                    modifier = Modifier
                        .padding(vertical = 5.dp),
                    directions = setOf(
                        RevealDirection.EndToStart
                    ),
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                            ,
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                ) {
                    ListaRecetaCard(
                        nombre = remember { mutableStateOf(item.nombre) },
                        elevation = 4.dp,
                        onClick = {
                            val idListaRecetasActual: Int = item.id_listaRecetas!!
                            //Posteriormente se navega a la pantalal de lista de recetas clicada.
                            navController.navigate(RutasNavegacion.ListaRecetas.route + "/$idListaRecetasActual")
                        }
                    )
                }
            }
        }
    }
}
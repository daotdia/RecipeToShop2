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
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.events.recetas.ListaCestasCompra
import com.otero.recipetoshop.presentationlogic.states.recetas.ListaCestasCompraState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaCestasCompra(
    navController: NavController,
    listaCestaCompraState: MutableState<ListaCestasCompraState>,
    onTriggeEvent: (ListaCestasCompra) -> Any,
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
            NewCestaCompraPopUp(
                onTriggerEvent = onTriggeEvent,
//                onAddListaReceta = { nombre ->
//                    onTriggeEvent(ListaCestasCompra.onAddListaReceta(nombre = nombre))
//                },
                onNewCestaCompra = dialogElement,
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
                items = listaCestaCompraState.value.listaCestasCompra,
                { listItem: CestaCompra -> listItem.id_cestaCompra!! }
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
                            val idCestaCompraActual: Int = item.id_cestaCompra!!
                            //Posteriormente se navega a la pantalal de lista de recetas clicada.
                            navController.navigate(RutasNavegacion.CestaCompra.route + "/$idCestaCompraActual")
                        }
                    )
                }
            }
        }
    }
}
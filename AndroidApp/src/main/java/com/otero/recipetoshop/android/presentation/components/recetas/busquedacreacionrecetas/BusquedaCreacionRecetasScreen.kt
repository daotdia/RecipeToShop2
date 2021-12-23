package com.otero.recipetoshop.android.presentation.components.recetas.busquedacreacionrecetas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.recetas.NewRecetaPopUp
import com.otero.recipetoshop.android.presentation.components.recetas.RecetaCard
import com.otero.recipetoshop.android.presentation.components.util.SearchBarRecetas
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.events.recetas.BusquedaCreacionRecetasEvents
import com.otero.recipetoshop.events.recetas.RecetaListEvents
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetasCreacionBusquedaState

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun  BusquedaCreacionRecetasScreen(
    busquedaCreacionState: MutableState<RecetasCreacionBusquedaState>,
    onTriggeEventReceta: (BusquedaCreacionRecetasEvents) -> Unit,
    navController: NavController
) {
    val newRecetaUser = remember { mutableStateOf(false)}
    val recetaSeleccionada = remember { mutableStateOf(false)}
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchBarRecetas(
                query = busquedaCreacionState.value.query,
                onQueryChanged = {
                    onTriggeEventReceta(BusquedaCreacionRecetasEvents.updateQuery(it))
                },
                onExecuteSearch = {
                    onTriggeEventReceta(BusquedaCreacionRecetasEvents.buscarRecetas)
                } )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (busquedaCreacionState.value.lisaRecetasBuscadas.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 4.dp, bottom = 4.dp, end = 4.dp, top = 12.dp)
                        .background(color = secondaryLightColor)
                        .weight(5F)
                    ,
                ) {
                    items(busquedaCreacionState.value.lisaRecetasBuscadas){ item ->
                        RecetaCard(
                            receta = item,
                            elevation = 4.dp,
                            onCantidadChange = {},
                            onRecetaClick = {
                                onTriggeEventReceta(
                                    BusquedaCreacionRecetasEvents.onAddYummlyReceta(
                                        item.copy(
                                            cantidad = 1,
                                            id_listaRecetas = busquedaCreacionState.value.id_listaRecetas
                                        )
                                    )
                                )
                                navController.navigate(RutasNavegacion.ListaRecetas.route + "/$busquedaCreacionState.value.id_listaRecetas")
                            }
                        )
                    }
                }
            }
            Divider(color = primaryDarkColor, thickness = 2.dp)
            Row(
                modifier = Modifier
                    .clickable(onClick = {
                        newRecetaUser.value = true
                    })
                    .weight(1F),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(newRecetaUser.value){
                    NewRecetaPopUp(
                        onAddReceta = { nombre, cantidad ->
                            onTriggeEventReceta(
                                BusquedaCreacionRecetasEvents.onAddUserReceta(
                                    nombre = nombre,
                                    cantidad = cantidad.toInt()
                                )
                            )
                        },
                        onNewReceta = newRecetaUser,
                        recetaSeleccionada = recetaSeleccionada
                    )
                }
                if(recetaSeleccionada.value){
                    navController.navigate(RutasNavegacion.ListaRecetas.route + "/${busquedaCreacionState.value.id_listaRecetas}")
                }
                Text(
                    modifier = Modifier
                        .clickable(onClick = {
                            newRecetaUser.value = true
                        }),
                    text = "Añadir Receta Usuario",
                    color = primaryDarkColor,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}
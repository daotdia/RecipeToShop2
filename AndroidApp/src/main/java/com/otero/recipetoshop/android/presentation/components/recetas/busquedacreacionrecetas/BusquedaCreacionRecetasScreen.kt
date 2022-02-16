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
import com.otero.recipetoshop.events.recetas.BusquedaRecetasAPI
import com.otero.recipetoshop.presentationlogic.states.recetas.BusquedaRecetasAPIState

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun  BusquedaCreacionRecetasScreen(
    busquedaCreacionRecetasAPIState: MutableState<BusquedaRecetasAPIState>,
    onTriggeEventReceta: (BusquedaRecetasAPI) -> Unit,
    navController: NavController
) {
    val newRecetaUser = remember { mutableStateOf(false)}
    val recetaSeleccionada = remember { mutableStateOf(false)}
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchBarRecetas(
                query = busquedaCreacionRecetasAPIState.value.query,
                onQueryChanged = {
                    onTriggeEventReceta(BusquedaRecetasAPI.updateQuery(it))
                },
                onExecuteSearch = {
                    onTriggeEventReceta(BusquedaRecetasAPI.buscarRecetas)
                } )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (busquedaCreacionRecetasAPIState.value.lisaRecetasBuscadas.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 4.dp, bottom = 4.dp, end = 4.dp, top = 12.dp)
                        .background(color = secondaryLightColor)
                        .weight(5F)
                    ,
                ) {
                    items(busquedaCreacionRecetasAPIState.value.lisaRecetasBuscadas){ item ->
                        RecetaCard(
                            receta = item,
                            elevation = 4.dp,
                            onCantidadChange = {},
                            onRecetaClick = {
                                onTriggeEventReceta(
                                    BusquedaRecetasAPI.onAddYummlyReceta(
                                        item.copy(
                                            cantidad = 1,
                                            id_cestaCompra = busquedaCreacionRecetasAPIState.value.id_cestaCompra
                                        )
                                    )
                                )
                                navController.navigate(RutasNavegacion.CestaCompra.route + "/${busquedaCreacionRecetasAPIState.value.id_cestaCompra}")
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
                                BusquedaRecetasAPI.onAddUserReceta(
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
                    navController.navigate(RutasNavegacion.CestaCompra.route + "/${busquedaCreacionRecetasAPIState.value.id_cestaCompra}")
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
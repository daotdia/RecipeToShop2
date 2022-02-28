package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.alimentos.ListaAlimentosCestaCompra
import com.otero.recipetoshop.android.presentation.components.despensa.NewAlimentoPopUp
import com.otero.recipetoshop.android.presentation.theme.*
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.cestacompra.CestaCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
/*
Este es el componente que implementa el contenido de cada cesta de recetas y alimentos.
 */
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ContenidoCestaCompra (
    stateCestaCompra: MutableState<CestaCompraState>,
    onTriggeEventCestaCompra: (CestaCompraEventos) -> Unit,
    navController: NavController
) {
    val newAlimentoDialog = remember { mutableStateOf(false)}

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
                onClick = {
                    //Que hacen cuando se de el OK para calcular cesta de la compra.
                },
                content = {
                    Icon(Icons.Default.Summarize, null)
                }
            )
        },
    ) {
        //En el caso de que se haya clicado la adición de nuevo alimento; el dialogo está por encima,.
        if(newAlimentoDialog.value){
            NewAlimentoPopUp(
                onAddAlimento = { nombre, tipoUnidad, cantidad  ->
                    onTriggeEventCestaCompra(
                        CestaCompraEventos.onAddAlimento(
                            nombre = nombre,
                            cantidad = cantidad.toInt(),
                            tipoUnidad = TipoUnidad.valueOf(tipoUnidad)
                        )
                    )
                },
                onNewAlimento = newAlimentoDialog
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            //Aquí se emplazan las recetas de la API.
            Column(modifier = Modifier.weight(5f)) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Recetas Buscadas",
                        style = TextStyle(
                            color = primaryDarkColor,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaRecetasCestaCompra(
                        stateCestaCompra = stateCestaCompra,
                        onTriggeEvent = onTriggeEventCestaCompra,
                        recetasOfUser = false,
                        navController = navController
                    )
                }
            }
            //Aquí se emplazan las recetas del usuario.
            Column(modifier = Modifier.weight(5f)) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Recetas Propias",
                        style = TextStyle(
                            color = primaryDarkColor,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaRecetasCestaCompra(
                        stateCestaCompra = stateCestaCompra,
                        onTriggeEvent = onTriggeEventCestaCompra,
                        recetasOfUser = true,
                        navController = navController
                    )
                }
            }
            //Aquí se emplazan los alimentos sueltos a tener en cuenta.
            Column(modifier = Modifier.weight(4f)) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Alimentos Sueltos",
                        style = TextStyle(
                            color = primaryDarkColor,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaAlimentosCestaCompra(
                        stateListaRecetas = stateCestaCompra,
                        onTriggeEvent = onTriggeEventCestaCompra,
                        onClickAddAlimento = {
                            newAlimentoDialog.value = true
                        }
                    )
                }
            }
        }
    }
}

package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.components.cestacompra.contenidocesta.recetas.contenidoreceta.ListaIngredientesReceta
import com.otero.recipetoshop.android.presentation.components.despensa.AlimentoPopUp
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.cestacompra.receta.RecetaEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetaState
/*
Este es el componeente que se encarga de implementar la pantalla de contenido de cada receta.
 */
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun ContenidoReceta(
    recetaState: MutableState<RecetaState>,
    onTriggeEventReceta: (RecetaEventos) -> Unit,
    navController: NavController
) {
    val newAlimentoDialog = remember { mutableStateOf(false)}
    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                  newAlimentoDialog.value = true
                },
            ) {
                Icon(Icons.Filled.Add, "Añadir receta")
            }
        }
    ) {
        //En el caso de que se de a añadir alimento salta el dialogo.
        if(newAlimentoDialog.value){
          AlimentoPopUp(
              onAddAlimento = { nombre, tipoUnidad, cantidad,  ->
                onTriggeEventReceta(RecetaEventos.onaAddIngrediente(
                    nombre = nombre,
                    cantidad = cantidad.toInt(),
                    tipoUnidad = TipoUnidad.valueOf(tipoUnidad)
                ))
              },
              onNewAlimento = newAlimentoDialog,
              onAutoCompleteClick = {
                  onTriggeEventReceta(
                      RecetaEventos.onClickAutocompleteReceta()
                  )
              },
              onAutoCompleteChange = { nombre ->
                  onTriggeEventReceta(
                      RecetaEventos.onAutocompleteRecetaChange(
                          query = nombre
                      )
                  )
              },
              autocompleteResults = recetaState.value.resultadoAutoComplete
          )
        }
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Porción donde está la imagen de la receta si la hubiera.
            Column(Modifier
                .weight(3f)
            ) {
                RecetaImagen(
                    url = recetaState.value.imagen,
                    contentDescription = recetaState.value.nombre
                )
            }
            //Porción donde está el nombre y lista de ingredientes.
            Column(Modifier.weight(6f)){
                //Primero el título
                Column(modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = recetaState.value.nombre,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5.copy(color = primaryDarkColor),
                    )
                }
                //Aquí la lista de los ingredientes.
                Column(Modifier.fillMaxSize()) {
                    ListaIngredientesReceta(
                        recetaState = recetaState,
                        onTriggerEvent = onTriggeEventReceta
                    )
                }
            }
        }
    }
}

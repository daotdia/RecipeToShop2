package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.util.GenericForm
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.NegativeAction
import com.otero.recipetoshop.domain.model.PositiveAction
import com.otero.recipetoshop.events.recetas.ListOfRecetasListEvents
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetasListState

@ExperimentalComposeUiApi
@Composable
fun NewListaRecetaPopUp(
    onTriggerEvent: (ListOfRecetasListEvents) -> Any,
    onNewListaReceta: MutableState<Boolean>,
    navController: NavController
){
    val newListaRecetaState = remember { mutableStateOf(RecetasListState()) }
    val nombreError = remember { mutableStateOf(false) }
    val isAceptable = remember { mutableStateOf(false) }

    //Solo en el caso de que no hayan errores en nombre y cantidad y se haya inicializado se puede dar OK.
    if(
        !nombreError.value &&
        !newListaRecetaState.value.nombre.isBlank()
    ){
        isAceptable.value = true
    }

    GenericForm(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
        ,
        onDismiss = {
            onNewListaReceta.value = false
        },
        title = {},
        negativeAction = NegativeAction(
            negativeBtnTxt = "Cancelar",
            onNegativeAction = {
                onNewListaReceta.value = false
            }
        ),
        positiveAction = PositiveAction(
            positiveBtnTxt = "Añadir",
            onPositiveAction = {
                onNewListaReceta.value = false
                val listarecetasid = onTriggerEvent(
                    ListOfRecetasListEvents.onAddListaReceta(
                        nombre = newListaRecetaState.value.nombre
                    )
                )
                //Hay que navegar a la pantalla de creación de nueva lista de recetas.
                navController.navigate(RutasNavegacion.ListaRecetas.route + "/$listarecetasid")
            },
        ),
        positiveEnabled = isAceptable,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 24.dp)
                ,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 8.dp),
                        textAlign = TextAlign.Center,
                        text = "Pon nombre a tu nuevo recetario",
                        style = MaterialTheme.typography.subtitle1,
                        color = primaryDarkColor
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    //Campo para el nombre del alimento.
                    OutlinedTextField(
                        value = newListaRecetaState.value.nombre,
                        onValueChange = {
                            newListaRecetaState.value = newListaRecetaState.value.copy(nombre = it)
                            //En el caso de que el campo de nombre esté vacío lo indico como error.
                            if(
                                newListaRecetaState.value.nombre.equals("") ||
                                newListaRecetaState.value.nombre.isBlank()
                            ){
                                nombreError.value = true
                            }else{
                                nombreError.value = false
                            }},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.Black,
                            backgroundColor = secondaryLightColor,
                        ),
                        textStyle = MaterialTheme.typography.body1,
                        label = { Text(text = "Nombre") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        isError = nombreError.value
                    )
                }
            }
        }
    )
}
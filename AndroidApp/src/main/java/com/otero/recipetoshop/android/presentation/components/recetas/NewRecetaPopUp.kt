package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.otero.recipetoshop.android.presentation.components.util.GenericForm
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.NegativeAction
import com.otero.recipetoshop.domain.model.PositiveAction
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetaState

@ExperimentalComposeUiApi
@Composable
fun NewRecetaPopUp(
    onAddReceta: (String,String) -> Unit,
    onNewReceta: MutableState<Boolean>,
    recetaSeleccionada: MutableState<Boolean> = mutableStateOf(false)
){
    val newRecetaState = remember { mutableStateOf(RecetaState()) }
    val nombreError = remember { mutableStateOf(false) }
    val cantidadError = remember { mutableStateOf(false) }
    val isAceptable = remember { mutableStateOf(false) }

    //Solo en el caso de que no hayan errores en nombre y cantidad y se haya inicializado se puede dar OK.
    if(
        !nombreError.value &&
        !cantidadError.value &&
        !newRecetaState.value.nombre.isBlank() &&
        !newRecetaState.value.cantidad.isBlank()
    ){
        isAceptable.value = true
    }

    GenericForm(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
        ,
        onDismiss = {
            onNewReceta.value = false
        },
        title = {},
        negativeAction = NegativeAction(
            negativeBtnTxt = "Cancelar",
            onNegativeAction = {
                onNewReceta.value = false
            }
        ),
        positiveAction = PositiveAction(
            positiveBtnTxt = "Añadir",
            onPositiveAction = {
                onNewReceta.value = false
                onAddReceta(
                    newRecetaState.value.nombre,
                    newRecetaState.value.cantidad
                )
                recetaSeleccionada.value = true
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
                        text = "Registro Nueva Receta",
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
                        value = newRecetaState.value.nombre,
                        onValueChange = {
                            newRecetaState.value = newRecetaState.value.copy(nombre = it)
                            //En el caso de que el campo de nombre esté vacío lo indico como error.
                            if(
                                newRecetaState.value.nombre.equals("") ||
                                newRecetaState.value.nombre.isBlank()
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
                //El campo de cantidad lo pongo en otra linea de la ventana.
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Campo para añadir la cantidad numérica
                    OutlinedTextField(
                        value = newRecetaState.value.cantidad,
                        onValueChange = {
                            newRecetaState.value = newRecetaState.value.copy(cantidad = it)                    //En el caso de que el campo de cantidad esté vacío lo indico como error.
                            if(
                                newRecetaState.value.cantidad.isBlank() ||
                                newRecetaState.value.cantidad.toInt() <= 0
                            ){
                                cantidadError.value = true
                            }else{
                                cantidadError.value = false
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.Black,
                            backgroundColor = secondaryLightColor,
                        ),
                        textStyle = MaterialTheme.typography.body1,
                        label = { Text(text = "Cantidad") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        isError = cantidadError.value
                    )
                }
            }
        }
    )
}
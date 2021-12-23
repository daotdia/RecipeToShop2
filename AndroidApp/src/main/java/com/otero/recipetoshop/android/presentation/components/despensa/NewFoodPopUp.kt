package com.otero.recipetoshop.android.presentation.components.despensa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
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
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentationlogic.states.despensa.FoodState

@ExperimentalComposeUiApi
@Composable
fun NewFoodPopUp(
    onAddFood: (String,String,String) -> Unit,
    onNewFood: MutableState<Boolean>,
) {
    val newFoodState = remember { mutableStateOf(FoodState()) }
    val nombreError = remember { mutableStateOf(false) }
    val cantidadError = remember { mutableStateOf(false) }
    val isAceptable = remember { mutableStateOf(false) }

    //Solo en el caso de que no hayan errores en nombre y cantidad y se haya inicializado se puede dar OK.
    if(
        !nombreError.value &&
        !cantidadError.value &&
        !newFoodState.value.nombre.isBlank() &&
        !newFoodState.value.cantidad.isBlank()
    ){
        isAceptable.value = true
    }

    GenericForm(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
        ,
        onDismiss = {
            onNewFood.value = false
        },
        title = {},
        negativeAction = NegativeAction(
            negativeBtnTxt = "Cancelar",
            onNegativeAction = {
                onNewFood.value = false
            }
        ),
        positiveAction = PositiveAction(
            positiveBtnTxt = "Añadir",
            onPositiveAction = {
                onNewFood.value = false
                onAddFood(
                    newFoodState.value.nombre,
                    newFoodState.value.tipo,
                    newFoodState.value.cantidad
                )
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
                        text = "Registro Nuevo Alimento",
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
                        value = newFoodState.value.nombre,
                        onValueChange = {
                            newFoodState.value = newFoodState.value.copy(nombre = it)
                            //En el caso de que el campo de nombre esté vacío lo indico como error.
                            if(
                                newFoodState.value.nombre.equals("") ||
                                newFoodState.value.nombre.isBlank()
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
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    //Campo para el tipo de unidades del alimento.
                    val expand = remember { mutableStateOf(false)}
                    val iconMenu = if(expand.value){
                        Icons.Filled.ChevronRight
                    }else{
                        Icons.Filled.ChevronRight
                    }
                    //En este campo hay tanto un primer campo que luego se coinvierte en un desplegable seleccionable con el tipo de alimento de mediad,.
                    OutlinedTextField(
                        modifier = Modifier
                            .clickable { expand.value = !expand.value }
                            .fillMaxWidth()
                        ,
                        readOnly = true,
                        value = newFoodState.value.tipo,
                        onValueChange = {
                            newFoodState.value = newFoodState.value.copy(tipo = it)
                        },
                        label = { Text(text = "Unidad Medición")},
                        textStyle = MaterialTheme.typography.body1,
                        trailingIcon = {
                            Icon(
                                iconMenu,
                                contentDescription = "menu unidad de medida",
                                modifier = Modifier.clickable { expand.value = !expand.value }
                            )
                        },
                    )
                    DropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = expand.value,
                        onDismissRequest = { expand.value = false },
                    ) {
                        TipoUnidad.values().forEach { tipo ->
                            DropdownMenuItem(onClick = {
                                newFoodState.value = newFoodState.value.copy(tipo = tipo.name)
                                expand.value = false
                            }) {
                                Text(text = tipo.name)
                            }
                        }
                    }
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
                        value = newFoodState.value.cantidad,
                        onValueChange = {
                            newFoodState.value = newFoodState.value.copy(cantidad = it)                    //En el caso de que el campo de cantidad esté vacío lo indico como error.
                            if(
                                newFoodState.value.cantidad.isBlank() ||
                                newFoodState.value.cantidad.toInt() <= 0
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


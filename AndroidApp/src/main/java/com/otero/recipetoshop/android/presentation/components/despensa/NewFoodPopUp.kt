package com.otero.recipetoshop.android.presentation.components.despensa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.analogousColorGreen
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentattion.screens.despensa.FoodState

@ExperimentalComposeUiApi
@Composable
fun NewFoodPopUp(
    onAddFood: (String, String, String) -> Unit,
){
    val newFoodState = remember { mutableStateOf(FoodState())}
    val nombreError= remember { mutableStateOf(false)}
    val cantidadError = remember { mutableStateOf(false)}
    val isAceptable = remember { mutableStateOf(false)}
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(24.dp)
        ) {
           //Campo para el nombre del alimento.
            OutlinedTextField(
                value = newFoodState.value.nombre,
                onValueChange = {
                    newFoodState.value = newFoodState.value.copy(nombre = it)
                    //En el caso de que el campo de nombre esté vacío lo indico como error.
                    if(newFoodState.value.nombre.equals("")){
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
            //Campo para el tipo de unidades del alimento.
            val expand = remember { mutableStateOf(false)}
            val selectOption = remember { mutableStateOf(TipoUnidad.values()[0].name) }
            val iconMenu = if(expand.value){
                Icons.Filled.ArrowDropDown
            }else{
                Icons.Filled.ArrowDropDown
            }
            //En este campo hay tanto un primer campo que luego se coinvierte en un desplegable seleccionable con el tipo de alimento de mediad,.
            OutlinedTextField(
                modifier = Modifier.clickable {
                    expand.value = !expand.value
                },
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
                expanded = expand.value,
                onDismissRequest = { expand.value = false },
            ) {
                TipoUnidad.values().forEach { tipo ->
                    DropdownMenuItem(onClick = {
                        selectOption.value = tipo.name

                    }) {
                        Text(text = tipo.name)
                    }
                }
            }
        }
        //El campo de cantidad lo pongo en otra linea de la ventana.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(24.dp)
        ) {
            //Campo para añadir la cantidad numérica
            OutlinedTextField(
                value = newFoodState.value.cantidad,
                onValueChange = {
                    newFoodState.value = newFoodState.value.copy(cantidad = it)                    //En el caso de que el campo de cantidad esté vacío lo indico como error.
                    if(
                        newFoodState.value.cantidad.equals("") ||
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
        //Solo en el caso de que no hayan errores en nombre y cantidad y se haya inicializado se puede dar OK.
        if(
            !nombreError.value &&
            !cantidadError.value &&
            !newFoodState.value.nombre.equals("") &&
            !newFoodState.value.cantidad.equals("0")
        ){
            isAceptable.value = true
        }
        //Boton para añadir el nuevo alimento.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
            ,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        ) {
            TextButton(
                onClick = {
                    onAddFood(
                        newFoodState.value.nombre,
                        newFoodState.value.tipo,
                        newFoodState.value.cantidad)
                },
                colors =  ButtonDefaults.buttonColors(analogousColorGreen),
                enabled = isAceptable.value,
            ) {
                Text(
                    text = "Añadir",
                    color = secondaryLightColor,
                )
            }
        }
    }
}
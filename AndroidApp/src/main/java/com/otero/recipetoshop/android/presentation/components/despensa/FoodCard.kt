package com.otero.recipetoshop.android.presentation.components.despensa

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.otero.recipetoshop.android.presentation.theme.Lora
import com.otero.recipetoshop.android.presentation.theme.appShapes
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.despensa.FoodListEvents


@ExperimentalComposeUiApi
@Composable
fun FoodCard(
    food: Food,
    onCantidadChange: (String) -> Unit,
    elevation: Dp,
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val stateFood = remember { mutableStateOf(food)}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp, bottom = 2.dp)
        ,
        elevation = elevation,
        shape = appShapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ,
        ){
            Row(
                modifier = Modifier.weight(2f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stateFood.value.nombre,
                    style = MaterialTheme.typography.h6
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Remove,
                    contentDescription = "menos",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(2.dp)
                    ,
                    tint = primaryDarkColor
                )
                TextField(
                    value = stateFood.value.cantidad.toString(),
                    onValueChange = {
                        stateFood.value = stateFood.value.copy(cantidad = it.toInt())
                    },
                    modifier = Modifier
                        .width(72.dp)
                        .height(24.dp),
                    label = { Text(text = stateFood.value.cantidad.toString()) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onCantidadChange(stateFood.value.cantidad.toString())
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = Lora,
                        fontWeight = FontWeight.Normal,
                        fontSize = 40.sp
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "más",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(2.dp)
                    ,
                    tint = primaryDarkColor
                )
//                Text(
//                    text = TipoUnidad.valueOf(stateFood.value.tipoUnidad.name).name,
//                    style = MaterialTheme.typography.subtitle1
//                )
            }
        }
    }
}
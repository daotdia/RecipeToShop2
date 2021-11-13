package com.otero.recipetoshop.android.presentation.components.despensa

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.Interactors.despensa.FoodListEvents
import com.otero.recipetoshop.android.R
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.despensa.Food


@ExperimentalComposeUiApi
@Composable
fun FoodCard(
    food: Food,
    onCantidadChange: (String) -> Unit,
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val stateFood = remember { mutableStateOf(food)}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(text = stateFood.value.nombre)
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
            ) {
                Image(
                    modifier = Modifier
                        .width(6.dp)
                        .height(6.dp)
                        .clickable(onClick = {
                            TODO("Disminuir el contenido del texto editable siguiente")
                        }),
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "menos",
                    contentScale = ContentScale.Crop
                )
                TextField(
                    value = stateFood.value.cantidad.toString(),
                    onValueChange = onCantidadChange,
                    modifier = Modifier
                        .width(24.dp)
                        .height(12.dp),
                    label = { Text(text = "0") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = secondaryLightColor
                    )
                )
                Image(
                    modifier = Modifier
                        .width(6.dp)
                        .height(6.dp)
                        .clickable(onClick = {
                            TODO("Disminuir el contenido del texto editable siguiente")
                        }),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "menos",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
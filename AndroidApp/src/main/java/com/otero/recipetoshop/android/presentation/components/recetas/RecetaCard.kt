package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

@ExperimentalComposeUiApi
@Composable
fun RecetaCard(
    nombre: MutableState<String>,
    elevation: Dp,
    readOnly: MutableState<Boolean>,
    onNombreChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp, bottom = 2.dp),
        elevation = elevation,
        shape = appShapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = nombre.value,
                onValueChange = { it ->
                    nombre.value = it
                },
                readOnly = readOnly.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onNombreChange(nombre.value)
                        keyboardController?.hide()
                    }
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = Lora,
                    fontWeight = FontWeight.Normal,
                    fontSize = 46.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                    disabledIndicatorColor = Color.Transparent,
                    disabledLabelColor = Color.Transparent,
                    disabledLeadingIconColor = Color.Transparent,
                    disabledPlaceholderColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    disabledTrailingIconColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    placeholderColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent
                ),
            )
        }
    }
}
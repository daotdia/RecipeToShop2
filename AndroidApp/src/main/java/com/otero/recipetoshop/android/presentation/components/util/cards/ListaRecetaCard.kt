package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.appShapes

@ExperimentalComposeUiApi
@Composable
fun ListaRecetaCard(
    nombre: MutableState<String>,
    elevation: Dp,
    onClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp, bottom = 2.dp)
            .clickable(
                onClick = onClick
            )
        ,
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
            Text(
                text = nombre.value,
                style = MaterialTheme.typography.h3
            )
        }
    }
}
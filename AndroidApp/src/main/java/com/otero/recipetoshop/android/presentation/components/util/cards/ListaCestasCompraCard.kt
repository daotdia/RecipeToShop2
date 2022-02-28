package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.theme.appShapes
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
/*
Este es el componente que implementa la card de una Cesta de recetas y alimentos.
 */
@ExperimentalComposeUiApi
@Composable
fun ListaCestasCompraCard(
    cesta: CestaCompra,
    elevation: Dp,
    onClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable(onClick = onClick)
        ,
        elevation = elevation,
        shape = appShapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    ,
                contentAlignment = Alignment.Center
            ){
                Column(Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                ) {
                    RecetaImagen(
                        url =cesta.imagen,
                        contentDescription = cesta.nombre,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = cesta.nombre,
                        modifier = Modifier.fillMaxWidth(0.85f),
                        style = MaterialTheme.typography.h5.copy(color = Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(top = 2.dp, bottom = 2.dp)
//            .clickable(
//                onClick = onClick
//            )
//        ,
//        elevation = elevation,
//        shape = appShapes.large
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(4.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = nombre.value,
//                style = MaterialTheme.typography.h3
//            )
//        }
//    }
}
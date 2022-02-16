package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.theme.appShapes
import com.otero.recipetoshop.domain.model.CestaCompra.Receta

@ExperimentalComposeUiApi
@Composable
fun RecetaCard (
    modifier: Modifier = Modifier,
    receta: Receta,
    onCantidadChange: (String) -> Unit,
    elevation: Dp,
    onRecetaClick: () -> Unit = {},
    size: Dp = 250.dp
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp)
            .clickable(onClick = onRecetaClick)
        ,
        elevation = elevation,
        shape = appShapes.medium
    ) {
        Column {
            RecetaImagen(
                url = if(receta.imagenSource != null) receta.imagenSource!! else "",
                contentDescription = receta.nombre,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = receta.nombre,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                if(receta.rating != null){
                    Text(
                        text = receta.rating.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}
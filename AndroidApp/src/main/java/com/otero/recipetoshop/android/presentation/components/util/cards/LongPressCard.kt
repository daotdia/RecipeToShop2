package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.theme.appShapes
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.primaryLightColor
import com.otero.recipetoshop.domain.util.TipoUnidad
/*
Este es un componente cuya card funciona no con Swipe, sino con presión larga.
 */
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun LongPressCard(
    nombre: String,
    imagen: String = "",
    cantidad: Int = 0,
    tipoUnidad: TipoUnidad = TipoUnidad.GRAMOS,
    onClickReceta: () -> Unit ={},
    onDelete: () -> Unit = {},
    active: Boolean = true
) {
    val longPressed = remember { mutableStateOf(false) }
    val borderStroke = remember { mutableStateOf(BorderStroke(0.dp, Color.White.copy(alpha = 0f))) }
    //Modifico las características necesarias si la Card está siendo clicada long.
    if (longPressed.value) {
        borderStroke.value = borderStroke.value.copy(
            2.dp,
            Brush.linearGradient(
                colors = listOf(primaryDarkColor, primaryDarkColor)
            )
        )
    } else {
        borderStroke.value = borderStroke.value.copy(
            0.dp,
            Brush.linearGradient(
                colors = listOf(primaryDarkColor, primaryLightColor)
            )
        )
    }
    //Creo una Surface de opacidad dinámica según la Card esté activa o no.
    Card(
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onLongClick = {
                    longPressed.value = true
                },
                onClick = {
                    if(longPressed.value){
                        longPressed.value = false
                    } else {
                        onClickReceta()
                    }
                },
            ),
        elevation = 8.dp,
        shape = appShapes.medium,
        border = borderStroke.value
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize()) {
                if (imagen.equals("")) {
                    Text(
                        text = nombre,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.h6.copy(color = Color.Gray),
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = cantidad.toString() + "  " + tipoUnidad.name,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.BottomCenter),
                        style = MaterialTheme.typography.body1.copy(color = Color.LightGray),
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                } else {
                    RecetaImagen(
                        url = imagen,
                        contentDescription = nombre
                    )
                    Text(
                        text = nombre,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.BottomCenter),
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                }
                //Sino está activa hay una superficie por arriba que la ensombrece
                if(!active){
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray.copy(alpha = 0.5f)
                    ){}
                }
                //Si se presiona long; sale posibilidad de eliminar la card.
                if (longPressed.value) {
                    Icon(
                        Icons.Filled.Delete,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.TopEnd)
                            .clickable(
                                onClick = onDelete
                            ),
                        tint = Color.Red.copy(alpha = 0.6f),
                        contentDescription = "Eliminar Tarjeta"
                    )
                }
            }
        }
    }
}

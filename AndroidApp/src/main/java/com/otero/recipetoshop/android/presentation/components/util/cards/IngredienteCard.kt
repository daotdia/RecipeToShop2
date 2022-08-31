package com.otero.recipetoshop.android.presentation.components.util.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.despensa.AlimentoPopUp
import com.otero.recipetoshop.android.presentation.theme.*
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad
/*
Este es el componente que implementa la Card de un ingrediente.
 */
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun IngredienteCard (
    nombre: String,
    cantidad: Int = 0,
    tipoUnidad: TipoUnidad = TipoUnidad.GRAMOS,
    onClickAlimento: (Int, String, String, String) -> Unit = {_,_,_,_ ->},
    onDelete: () -> Unit,
    active: Boolean = true,
    longpressed: Boolean = true,
    alimento_actual: Alimento? = null
) {
    val clickAlimento = remember { mutableStateOf(false)}
    val longPressed = remember { mutableStateOf(false) }
    val borderStroke = remember { mutableStateOf(BorderStroke(0.dp, Color.White.copy(alpha = 0f))) }

    val cardHeight: Dp = 128.dp
    val cardWidth: Dp = 128.dp

    if (longPressed.value && longpressed) {
        borderStroke.value = borderStroke.value.copy(
            2.dp,
            Brush.linearGradient(
                colors = listOf(Color.Red, Color.Red)
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
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
                    .combinedClickable(
                        onLongClick = {
                            longPressed.value = true
                        },
                        onClick = {
                            if (longPressed.value) {
                                longPressed.value = false
                            } else {
                                //Dialogo con los datos del ingrediente a editar.
                                clickAlimento.value = true
                            }
                        },
                    ),
                elevation = 8.dp,
                shape = CircleShape,
                border = borderStroke.value,
                backgroundColor = primaryDarkColor,
            ) {
                Column(
                    Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = nombre,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        if (longPressed.value && longpressed) {
                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(4.dp)
                                    .align(Alignment.TopEnd),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Icon(
                                    Icons.Filled.Delete,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clickable(
                                            onClick = onDelete
                                        ),
                                    tint = Color.Red.copy(alpha = 0.6f),
                                    contentDescription = "Eliminar Tarjeta"
                                )
                            }
                        }
                        if(clickAlimento.value){
                            AlimentoPopUp(
                                onEditAlimento = clickAlimento,
                                editAlimento = { id_aliemnto, nombre, tipo, cantidad ->
                                    //FUnción para llamar a viewmodel para ediatr alimento.
                                    onClickAlimento(id_aliemnto,nombre,tipo,cantidad)
                                },
                                alimento_actual = alimento_actual
                            )
                        }
                    }
                }
            }
        }
        Column(
            Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = cantidad.toString() + "  " + TipoUnidad.parseAbrev(tipoUnidad),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp),
                    style = MaterialTheme.typography.body1.copy(color = Color.LightGray),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
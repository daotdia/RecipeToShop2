package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.*
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.events.recetas.RecetaListEvents
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetasListState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun RecetasList(
    stateListaRecetas: MutableState<RecetasListState>,
    onTriggeEvent: (RecetaListEvents) -> Unit,
    color: Color = Color.Transparent,
    offset: Dp = 48.dp
) {
    LazyColumn(
        modifier = Modifier
            .offset(y = offset)
            .padding(start = 4.dp, bottom = 4.dp, end = 4.dp, top = 12.dp)
            .background(color = color),
    ) {
        items(
            stateListaRecetas.value.allrecetas,
            { listItem: Receta -> listItem.id_Receta!! }) { item ->
            var active by remember { mutableStateOf(true) }
            //Si no es un alimento, es decir su tipo es null; por tanto es una receta.
            if (!item.active) {
                active = false
            } else{
                active = true
            }
            var delete by remember { mutableStateOf(false) }
            val dismissStateReceta = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) delete =
                        !delete
                    it != DismissValue.DismissedToStart
                }
            )
            if (active) {
                RevealSwipe(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        ,
                    directions = setOf(
                        RevealDirection.EndToStart
                    ),
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clickable(onClick = {
                                    onTriggeEvent(RecetaListEvents.onDeleteReceta(item))
                                }),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    },
                    backgroundCardStartColor = Color.Magenta,
                    backgroundCardEndColor = Color.Red
                ) {
                    RecetaCard(
                        modifier = Modifier
                        .clickable(onClick = {
                        onTriggeEvent(RecetaListEvents.onRecetaClick(receta = item, active = false))
                        }),
                        receta = item,
                        onCantidadChange = {
                            onTriggeEvent(
                                RecetaListEvents.onCantidadRecetaChange(
                                    cantidad = it.toInt(),
                                    receta = item
                                )
                            )
                        },
                        elevation = 2.dp,
                        size = 85.dp
                    )
                }
                //Si se llega al número de recetas activas; se adjunta divisor espaciado.
//                if(primerInactivo == stateListaRecetas.value.alimentosActive.size){
//                    Spacer(Modifier.height(12.dp))
//                    Divider(color = primaryColor,
//                        thickness = 2.dp)
//                    Spacer(Modifier.height(12.dp))
//                }
            } else {
                RevealSwipe(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        ,
                    directions = setOf(
                        RevealDirection.EndToStart
                    ),
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                                .clickable(onClick = {
                                    onTriggeEvent(RecetaListEvents.onDeleteReceta(item))
                                }),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    },
                    backgroundCardStartColor = Color.Magenta,
                    backgroundCardEndColor = Color.Red
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(top = 1.dp, bottom = 1.dp)
                            .clickable(onClick = {
                                onTriggeEvent(RecetaListEvents.onRecetaClick(receta = item, active = true))
                            }),
                        elevation = 0.dp,
                        shape = appShapes.small,
                        backgroundColor = secondaryColor
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(onClick = {
                                    onTriggeEvent(RecetaListEvents.onRecetaClick(receta = item, active = true))
                                })
                                ,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(onClick = {
                                        onTriggeEvent(RecetaListEvents.onRecetaClick(receta = item, active = true))
                                    }),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clickable(onClick = {
                                        onTriggeEvent(RecetaListEvents.onRecetaClick(receta = item, active = true))
                                    }),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = item.nombre,
                                        style = TextStyle(
                                            color = primaryDarkColor,
                                            fontSize = MaterialTheme.typography.h6.fontSize,
                                            fontStyle = MaterialTheme.typography.h6.fontStyle,
                                            fontFamily = MaterialTheme.typography.h6.fontFamily
                                        )
                                    )
                                    //Divider(color = primaryDarkColor.copy(alpha = 0.75f), thickness = 4.dp)
                                }
                            }
                        }
                    }
                }
                Divider(
                    color = primaryDarkColor,
                    thickness = 2.dp
                )
            }
        }
    }
}
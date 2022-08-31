//package com.otero.recipetoshop.android.presentation.components.recetas
//
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//import com.otero.recipetoshop.android.presentation.components.despensa.FoodCard
//import com.otero.recipetoshop.android.presentation.components.util.MenuItemBackLayer
//import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
//import com.otero.recipetoshop.android.presentation.theme.*
//import com.otero.recipetoshop.domain.model.despensa.Alimento
//import com.otero.recipetoshop.domain.util.Constantes
//import com.otero.recipetoshop.presentationlogic.events.recetas.CestaCompraEventos
//import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//
//@ExperimentalMaterialApi
//@ExperimentalComposeUiApi
//@Composable
//fun AlimentosList (
//    stateListaRecetas: MutableState<CestaCompraState>,
//    onTriggeEvent: (CestaCompraEventos) -> Unit,
//    scaffoldState: BackdropScaffoldState,
//    coroutineScope: CoroutineScope,
//    menuItem: MenuItemBackLayer,
//    onMenuItemClick: (String) -> Unit,
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .clickable(onClick = {
//                if (scaffoldState.isRevealed) {
//                    coroutineScope.launch {
//                        scaffoldState.conceal()
//                    }
//                }
//            }),
//    ) {
//        Spacer(Modifier.height(8.dp))
//        Box(){
//            Text(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .fillMaxWidth()
//                    .clickable(
//                        onClick = {
//                            onMenuItemClick(menuItem.ruta)
//                            menuItem.foccused.value = true
//                        }
//                    ),
//                text = menuItem.nombre,
//                color = primaryDarkColor,
//                style = TextStyle(
//                    color = primaryDarkColor,
//                    fontSize = MaterialTheme.typography.h5.fontSize
//                ),
//            )
//            Row(
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .align(Alignment.CenterEnd)
//                    .wrapContentHeight()
//                    .padding(8.dp)
//                    .background(color =  secondaryLightColor),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                NestedDownMenu(
//                    options = listOf("Eliminar alimentos"),
//                    onClickItem = { onTriggeEvent(CestaCompraEventos.onDeleteRecetas(it)) }
//                )
//            }
//        }
//        Spacer(Modifier.height(8.dp))
//        if(scaffoldState.isConcealed){
//            Divider(
//                color = primaryDarkColor.copy(alpha = 0.6f),
//                thickness = 1.dp,
//                modifier = Modifier
//                    .width(256.dp)
//                    .align(Alignment.Start)
//            )
//        }
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth()
//                .padding(start = 4.dp, top = 12.dp, bottom = 4.dp, end = 4.dp),
//        ) {
//            items(stateListaRecetas.value.allAlimentos, { listItem: Alimento -> listItem.id_food!! }) { item ->
//                var delete by remember { mutableStateOf(false) }
//                var active by remember { mutableStateOf(true) }
//                if (!item.active) {
//                    active = false
//                } else{
//                    active = true
//                }
//                val dismissStateAlimento = rememberDismissState(
//                    initialValue = DismissValue.Default,
//                    confirmStateChange = {
//                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) delete = !delete
//                        it != DismissValue.DismissedToStart
//                    }
//                )
//                SwipeToDismiss(
//                    state = dismissStateAlimento,
//                    modifier = Modifier.padding(vertical = 4.dp),
//                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
//                    dismissThresholds = { direction ->
//                        FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
//                    },
//                    background = {
//                        val direction = dismissStateAlimento.dismissDirection ?: return@SwipeToDismiss
//                        val color by animateColorAsState(
//                            when (dismissStateAlimento.targetValue) {
//                                DismissValue.Default -> secondaryDarkColor
//                                DismissValue.DismissedToEnd -> Color.Red
//                                DismissValue.DismissedToStart -> Color.Red
//                            }
//                        )
//                        val alignment = when (direction) {
//                            DismissDirection.StartToEnd -> Alignment.CenterStart
//                            DismissDirection.EndToStart -> Alignment.CenterEnd
//                        }
//                        val icon = when (direction) {
//                            DismissDirection.StartToEnd -> Icons.Default.Delete
//                            DismissDirection.EndToStart -> Icons.Default.Delete
//                        }
//                        val scale by animateFloatAsState(
//                            if (dismissStateAlimento.targetValue == DismissValue.Default) 0.75f else 1f
//                        )
//
//                        Box(
//                            Modifier
//                                .fillMaxSize()
//                                .background(color)
//                                .padding(horizontal = 20.dp),
//                            contentAlignment = alignment
//                        ) {
//                            Icon(
//                                icon,
//                                contentDescription = "Localized description",
//                                modifier = Modifier.scale(scale)
//                            )
//                        }
//                    },
//                    dismissContent = {
//                        if (delete) {
//                            onTriggeEvent(CestaCompraEventos.onDeleteAlimento(item))
//                        }
//                        if(active){
//                            FoodCard(
//                                modifier = Modifier
//                                    .clickable(onClick = {
//                                    onTriggeEvent(CestaCompraEventos.onAlimentoClick(alimento = item, active = false))
//                                }),
//                                alimento = item,
//                                onCantidadChange = {
//                                    onTriggeEvent(
//                                        CestaCompraEventos.onCantidadAlimentoChange(
//                                            cantidad = it.toInt(),
//                                            alimento = item
//                                        )
//                                    )
//                                },
//                                elevation = 4.dp
//                            )
//                        } else {
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(Constantes.CARDSIZE.dp)
//                                    .padding(top = 1.dp, bottom = 1.dp)
//                                    .clickable(onClick = {
//                                        onTriggeEvent(CestaCompraEventos.onAlimentoClick(alimento = item, active = true))
//                                    }),
//                                elevation = 0.dp,
//                                shape = appShapes.small,
//                                backgroundColor = secondaryLightColor
//                            ) {
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .clickable(onClick = {
//                                            onTriggeEvent(
//                                                CestaCompraEventos.onAlimentoClick(
//                                                    alimento = item,
//                                                    active = true
//                                                )
//                                            )
//                                        }),
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .clickable(onClick = {
//                                                onTriggeEvent(
//                                                    CestaCompraEventos.onAlimentoClick(
//                                                        alimento = item,
//                                                        active = true
//                                                    )
//                                                )
//                                            }),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        Text(
//                                            text = item.nombre,
//                                            style = TextStyle(
//                                                color = primaryDarkColor,
//                                                fontSize = MaterialTheme.typography.h6.fontSize,
//                                                fontStyle = MaterialTheme.typography.h6.fontStyle,
//                                                fontFamily = MaterialTheme.typography.h6.fontFamily
//                                            )
//                                        )
//                                    }
//                                    Divider(
//                                        modifier = Modifier
//                                            .padding(top = 18.dp),
//                                        color = primaryDarkColor,
//                                        thickness = 1.dp
//                                    )
//                                }
//                            }
//                        }
//                    }
//                )
//            }
//        }
//    }
//}
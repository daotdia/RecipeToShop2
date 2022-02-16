//package com.otero.recipetoshop.android.presentation.components.util
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//import com.otero.recipetoshop.android.presentation.components.recetas.RecetasList
//import com.otero.recipetoshop.android.presentation.theme.*
//import com.otero.recipetoshop.events.recetas.CestaCompraEventos
//import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//
//@ExperimentalFoundationApi
//@ExperimentalMaterialApi
//@ExperimentalComposeUiApi
//@Composable
//fun BackLayerBackDrop(
//    menuItem: MenuItemBackLayer,
//    onMenuItemClick: (String) -> Unit,
//    stateListaRecetas: MutableState<CestaCompraState>,
//    onTriggeEventReceta: (CestaCompraEventos) -> Unit,
//    scaffoldState: BackdropScaffoldState,
//    coroutineScope: CoroutineScope,
//    updateRuta: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .wrapContentHeight()
//            .fillMaxWidth()
//            .clickable(onClick = {
//                updateRuta()
//                if (scaffoldState.isConcealed) {
//                    coroutineScope.launch { scaffoldState.reveal() }
//                }
//            })
//    ) {
//        Column(
//            modifier = Modifier
//                .wrapContentHeight()
//                .fillMaxWidth()
//                .background(color = secondaryColor)
//        ) {
//            Column(
//                modifier = Modifier
//                    .wrapContentHeight()
//                    .fillMaxWidth()
//            ) {
//                Spacer(Modifier.height(8.dp))
//                Box(){
//                    Text(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .clip(RoundedCornerShape(8.dp))
//                            .fillMaxWidth()
//                            .align(Alignment.CenterStart)
//                            .clickable(
//                                onClick = {
//                                    onMenuItemClick(menuItem.ruta)
//                                    menuItem.foccused.value = true
//                                }
//                            ),
//                        text = menuItem.nombre,
//                        color = primaryDarkColor,
//                        style = TextStyle(
//                            color = primaryDarkColor,
//                            fontSize = MaterialTheme.typography.h5.fontSize
//                        ),
//                    )
//                    Row(
//                        modifier = Modifier
//                            .wrapContentWidth()
//                            .align(Alignment.CenterEnd)
//                            .wrapContentHeight()
//                            .padding(8.dp)
//                            .background(color =  secondaryColor),
//                        horizontalArrangement = Arrangement.End,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        NestedDownMenu(
//                            options = listOf("Eliminar Recetas"),
//                            onClickItem = { onTriggeEventReceta(CestaCompraEventos.onDeleteRecetas(it)) }
//                        )
//                    }
//                }
//                Spacer(Modifier.height(8.dp))
//                if (scaffoldState.isRevealed) {
//                    Divider(
//                        color = primaryDarkColor.copy(alpha = 0.6f),
//                        thickness = 1.dp,
//                        modifier = Modifier
//                            .width(256.dp)
//                            .align(Alignment.Start)
//                    )
//                }
//            }
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth()
//                .background(color = secondaryColor)
//        ) {
//            RecetasList(
//                stateListaRecetas = stateListaRecetas,
//                onTriggeEvent = onTriggeEventReceta,
//                color = secondaryColor,
//                offset = 0.dp
//            )
//        }
//    }
//}
//
//data class MenuItemBackLayer(
//    val nombre: String,
//    val foccused: MutableState<Boolean>,
//    val ruta: String
//)
package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.util.LongPressCard
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.events.cestacompra.CestaCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
/*
Este es el componente que implementa una lista de recetas dentro de la lista de la compra.
 */
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaRecetasCestaCompra(
    stateCestaCompra: MutableState<CestaCompraState>,
    onTriggeEvent: (CestaCompraEventos) -> Unit,
    recetasOfUser: Boolean = true,
    recetasFavoritas: Boolean,
    navController: NavController,
    onNuevaReceta: () -> Unit = {},
    receta_actual: MutableState<Receta?> = mutableStateOf(null),
    addPicture: () -> Unit = {},
) {
    val cardHeight:Dp = 126.dp
    val cardWidth: Dp = 168.dp
    LazyRow(
        modifier = Modifier
            .padding(start = 4.dp, bottom = 4.dp, end = 4.dp, top = 12.dp)
            ,
    ) {
        //Primer artefacto de receta anclado para poder ir añadiendo nuevas recetas, si es receta no favorita .
        if(!recetasFavoritas) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .height(cardHeight)
                        .width(cardWidth.div(2.2f))
                        .clickable(
                            onClick = {
                                onNuevaReceta()
                            }
                        )
                ) {
                    Card(
                        modifier = Modifier
                            .height(cardHeight.div(1.8f))
                            .width(cardWidth.div(2.4f))
                            .align(Alignment.Center),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(48.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize())
                        {
                            Icon(
                                Icons.Filled.Add,
                                modifier = Modifier
                                    .size(46.dp)
                                    .padding(2.dp)
                                    .align(Alignment.Center),
                                tint = Color.LightGray,
                                contentDescription = "más",
                            )
                        }
                    }
                }
            }
        }
        items(
            stateCestaCompra.value.allrecetas,
            { listItem: Receta -> listItem.id_Receta!! }) { item ->
            var recetaIsActive by remember { mutableStateOf(true) }
            //Solo en el caso de que el tipo de receta corresponda con la temática de la lista.
            if(recetasFavoritas == item.isFavorita){
                // Tener en cuenta que las recetas siempre vienen en orden de back; primero las activas y luego las inactivas
                if (!item.active) {
                    recetaIsActive = false
                } else{
                    recetaIsActive = true
                }
                Column(
                    modifier = Modifier
                        .height(cardHeight)
                        .width(cardWidth)
                        .padding(4.dp)
                ) {
                    LongPressCard(
                        nombre = item.nombre,
                        imagen = if (item.imagenSource == null) "" else item.imagenSource!!,
                        onClickReceta = {
                            if(!recetaIsActive){
                                if(item.id_cestaCompra != stateCestaCompra.value.id_cestaCompra_actual) {
                                    val newReceta = item.copy(
                                        id_Receta = -1,
                                        id_cestaCompra = stateCestaCompra.value.id_cestaCompra_actual!!
                                    )
                                    recetaIsActive = true
                                    onTriggeEvent(
                                        CestaCompraEventos.onUpdateRecetaActive(
                                            newReceta,
                                            recetaIsActive
                                        )
                                    )
                                } else {
                                    recetaIsActive = true
                                    onTriggeEvent(
                                        CestaCompraEventos.onUpdateRecetaActive(
                                            item,
                                            recetaIsActive
                                        )
                                    )
                                }
                            } else{
                                if(item.isFavorita){
                                    recetaIsActive = false
                                    onTriggeEvent(
                                        CestaCompraEventos.onUpdateRecetaActive(
                                            item,
                                            recetaIsActive
                                        )
                                    )
                                } else{
                                    //Cuando se clique una receta activa no favorita se va a la pantalla de contenido de receta.
                                    navController.navigate(RutasNavegacion.ContenidoReceta.route + "/${item.id_cestaCompra}" + "/${item.id_Receta}")
                                }
                            }
                        },
                        onDelete = {
                            if(recetaIsActive){
                                recetaIsActive = false
                                onTriggeEvent(CestaCompraEventos.onUpdateRecetaActive(item, recetaIsActive))
                            } else{
                                onTriggeEvent(CestaCompraEventos.onDeleteReceta(item))
                            }
                        },
                        active = recetaIsActive,
                        isReceta = true,
                        onFavoritaClick = {
                                onTriggeEvent(
                                    CestaCompraEventos.onUpdateRecetaFavorita(
                                        receta = item,
                                        isFavorita = !item.isFavorita
                                    )
                                )
                        },
                        favorita = item.isFavorita,
                        onAumentarCantidadReceta = {
                            onTriggeEvent(
                                CestaCompraEventos.onAumentarCantidadReceta(
                                    receta = item
                                )
                            )
                        },
                        onReducirCantidadReceta = {
                            onTriggeEvent(
                                CestaCompraEventos.onReducirCantidadReceta(
                                    receta = item
                                )
                            )
                        },
                        cantidad = item.cantidad,
                        addPicture = {
                            receta_actual.value = item

                            addPicture()
                        }
                    )
                }
            }
        }
    }
}
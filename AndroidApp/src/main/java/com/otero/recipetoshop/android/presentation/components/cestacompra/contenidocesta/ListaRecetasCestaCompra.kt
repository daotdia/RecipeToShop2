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

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaRecetasCestaCompra(
    stateCestaCompra: MutableState<CestaCompraState>,
    onTriggeEvent: (CestaCompraEventos) -> Unit,
    recetasOfUser: Boolean,
    navController: NavController
) {
    val cardHeight:Dp = 126.dp
    val cardWidth: Dp = 168.dp
    LazyRow(
        modifier = Modifier
            .padding(start = 4.dp, bottom = 4.dp, end = 4.dp, top = 12.dp)
            ,
    ) {
        //Primer artefacto de receta anclado para poder ir añadiendo nuevas recetas.
        stickyHeader {
            Box(
                modifier = Modifier
                    .height(cardHeight)
                    .width(cardWidth.div(2.2f))
                    .clickable(
                        onClick = {
                            navController.navigate(RutasNavegacion.BusquedaRecetas.route + "/${stateCestaCompra.value.id_cestaCompra_actual}")
                        }
                    )
            ){
                Card(
                    modifier = Modifier
                        .height(cardHeight.div(1.8f))
                        .width(cardWidth.div(2.4f))
                        .align(Alignment.Center)
                    ,
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
        items(
            stateCestaCompra.value.allrecetas,
            { listItem: Receta -> listItem.id_Receta!! }) { item ->
            var recetaIsActive by remember { mutableStateOf(true) }
            //Solo en el caso de que el tipo de receta corresponda con la temática de la lista.
            if(recetasOfUser == item.user){
                //Guardo el estado activo de la receta en variable auxiliar por comidad de uso.
                    //Tener en cuenta que las recetas siempre vienen en orden de back; primero las activas y luego las inactivas
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
                        imagen = if (item.imagenSource == null) "null" else item.imagenSource!!,
                        onClickReceta = {
                            if(!recetaIsActive){
                                recetaIsActive = true
                                onTriggeEvent(CestaCompraEventos.onUpdateRecetaActive(item,recetaIsActive))
                            } else{
                                //Cuando se clique una receta activa sea la que sea se va a la pantalla de contenido de receta.
                                navController.navigate(RutasNavegacion.ContenidoReceta.route + "/${item.id_cestaCompra}" + "/${item.id_Receta}")
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
                        active = recetaIsActive
                    )
                }
            }
        }
    }
}
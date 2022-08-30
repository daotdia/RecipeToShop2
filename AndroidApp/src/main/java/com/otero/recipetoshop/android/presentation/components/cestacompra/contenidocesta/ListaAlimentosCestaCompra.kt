package com.otero.recipetoshop.android.presentation.components.alimentos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.util.LongPressCard
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.events.cestacompra.CestaCompraEventos
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState
import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
/*
Este es el componente que implementa una lista de alimentos dentro de una cesta de la compra.
 */
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ListaAlimentosCestaCompra(
    stateListaRecetas: MutableState<CestaCompraState> = mutableStateOf(CestaCompraState()),
    onTriggeEvent: (CestaCompraEventos) -> Unit,
    onClickAddAlimento: () -> Unit,
) {
    val cardHeight: Dp = 126.dp
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
                            onClickAddAlimento()
                        }
                    )
            ) {
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
            items = stateListaRecetas.value.allAlimentos,
            { listItem: Alimento -> listItem.id_alimento!! }) { item ->
            var alimentoIsActive by remember { mutableStateOf(true) }
            //Solo en el caso de que el tipo de receta corresponda con la temática de la lista.
            //Guardo el estado activo de la receta en variable auxiliar por comidad de uso.
            //Tener en cuenta que las recetas siempre vienen en orden de back; primero las activas y luego las inactivas
            if (!item.active) {
                alimentoIsActive = false
            } else{
                alimentoIsActive = true
            }
            Column(
                modifier = Modifier
                    .height(cardHeight)
                    .width(cardWidth)
                    .padding(4.dp)
            ) {
                LongPressCard(
                    nombre = item.nombre,
                    onClickReceta = {
                        alimentoIsActive = !item.active
                        onTriggeEvent(CestaCompraEventos.onAlimentoClick(item,alimentoIsActive))
                    },
                    onDelete = {
                        onTriggeEvent(CestaCompraEventos.onDeleteAlimento(item))
                    },
                    active = alimentoIsActive,
                    cantidad = item.cantidad,
                    tipoUnidad = item.tipoUnidad,
                    isAlimento = true
                )
            }
        }
    }
}
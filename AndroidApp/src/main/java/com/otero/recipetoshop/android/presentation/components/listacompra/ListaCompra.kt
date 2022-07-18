package com.otero.recipetoshop.android.presentation.components.listacompra

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.components.alimentos.ListaAlimentosCestaCompra
import com.otero.recipetoshop.android.presentation.theme.*
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.listacompra.ListaCompraEvents
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState
import de.charlex.compose.RevealSwipe


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ListaCompra(
    navController: NavController,
    listaCompraState: MutableState<ListaCompraState>,
    onTriggeEvent: (ListaCompraEvents) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(6f)
        ) {
            items(items = listaCompraState.value.listaProductos) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(6.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 8.dp,
                ) {
                    Column {
                        //Aquí está la imagen del supermercado
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(86.dp),
                        ) {
                            //Se tendrá que cambiar dinamicamente cuando tenga más supermercados
                            RecetaImagen(
                                url = "https://vams-loyalia-storage.s3.eu-west-1.amazonaws.com/images/deals/_720x495/carrefour.jpg",
                                contentDescription = "logo_carrefour"
                            )
                        }
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(312.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            //Aquí está la lista grid de tres columnas
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp),
                                cells = GridCells.Fixed(3)
                            ) {
                                items(listaCompraState.value.listaProductos) {
                                    Column(
                                        Modifier
                                            .fillMaxSize()
                                    ) {
                                        //La imagen del producto junto a la cantidad de unidades abajo.
                                        RevealSwipe(
                                            modifier = Modifier
                                                .padding(2.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            onContentClick = {
                                                //Que hacer cuando el usuario clique el producto seleccionado
                                            },
                                            onBackgroundEndClick = {
                                                //Que hacer cuando el usuario trate de elimnar la carta
                                            },
                                            onBackgroundStartClick = {
                                                //Qué hacer cuando el usuario haga clik en editar el producto
                                            },
                                            contentColor = primaryDarkColor,
                                            backgroundCardStartColor = primaryColor,
                                            backgroundCardEndColor = deleteColor.copy(alpha = 0.3f),
                                            backgroundCardContentColor = secondaryLightColor,
                                            hiddenContentEnd = {
                                                Icon(
                                                    modifier = Modifier.padding(horizontal = 14.dp).size(22.dp),
                                                    imageVector = Icons.Outlined.Delete,
                                                    contentDescription = null
                                                )
                                            },
                                            backgroundCardElevation = 4.dp,
                                            maxRevealDp = 34.dp,
                                            hiddenContentStart = {
                                                Icon(
                                                    modifier = Modifier.padding(horizontal = 14.dp).size(22.dp),
                                                    imageVector = Icons.Outlined.Edit,
                                                    contentDescription = null,
                                                    tint = Color.White
                                                )
                                            }
                                        ) {
                                            Column {
                                                Column() {
                                                    //La imagen del producto, no importa que sea de receta o de producto.
                                                    RecetaImagen(
                                                        url = item.imagen_src,
                                                        contentDescription = item.nombre
                                                    )
                                                }
                                                //La cantidad de unidades del producto.
                                                Column(
                                                    modifier = Modifier
                                                        .background(color = Color.White)
                                                        .fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        modifier = Modifier
                                                            .align(Alignment.CenterHorizontally),
                                                        textAlign = TextAlign.Center,
                                                        style = MaterialTheme.typography.body1.copy(
                                                            color = Color.Black
                                                        ),
                                                        maxLines = 2,
                                                        text = item.cantidad.toString() + " Ud",
                                                    )
                                                }
                                                //La informaación sobre el producto: nombre, precio, peso
                                                Column(
                                                    modifier = Modifier
                                                        .background(color = Color.White)
                                                ) {
                                                    //El nombre del producto justo debajo de la imagen con su cantidad.
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            modifier = Modifier
                                                                .align(Alignment.CenterHorizontally),
                                                            textAlign = TextAlign.Center,
                                                            style = MaterialTheme.typography.body2.copy(
                                                                color = Color.Black
                                                            ),
                                                            maxLines = 2,
                                                            text = item.nombre,
                                                        )
                                                    }
                                                    Column {
                                                        //El peso del producto a la izquierda y el coste total a la derecha debajo del nombre.
                                                        Box(Modifier.fillMaxSize()) {
                                                            //El peso al inicio
                                                            Text(
                                                                modifier = Modifier
                                                                    .align(Alignment.CenterStart)
                                                                    .padding(start = 6.dp),
                                                                textAlign = TextAlign.Center,
                                                                style = MaterialTheme.typography.body1.copy(
                                                                    color = primaryDarkColor
                                                                ),
                                                                maxLines = 1,
                                                                text = (
                                                                        if ((item.cantidad * item.peso) >= 1000) {
                                                                            val peso =
                                                                                (item.cantidad * item.peso) / 1000
                                                                            if (item.tipoUnidad!!.name.equals(
                                                                                    "GRAMOS"
                                                                                )
                                                                            ) {
                                                                                "$peso Kg"
                                                                            } else {
                                                                                "$peso L"
                                                                            }
                                                                        } else {
                                                                            (item.cantidad * item.peso).toString() + " " + TipoUnidad.parseAbrev(
                                                                                item.tipoUnidad!!
                                                                            )
                                                                        }
                                                                        )
                                                            )
                                                            //El precio al final
                                                            Text(
                                                                modifier = Modifier
                                                                    .align(Alignment.CenterEnd)
                                                                    .padding(end = 6.dp),
                                                                textAlign = TextAlign.Center,
                                                                style = MaterialTheme.typography.body1.copy(
                                                                    color = primaryDarkColor
                                                                ),
                                                                maxLines = 1,
                                                                text = (item.cantidad * item.precio_numero).toString() + " €",
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //Aquí está el precio total y el peso total de la lista de la compra de este supermercado.
                        Column{
                            Box (
                                Modifier.fillMaxSize().padding(2.dp)
                            ){
                                //El peso total de la lista de la compra aproximado, no puedo saber el peso de los productos con unidades; estimación optimista.
                                Text(
                                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 42.dp),
                                    style = MaterialTheme.typography.h5.copy(color = primaryDarkColor),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    text =
                                        if(listaCompraState.value.peso_total >= 1000){
                                            "> " + listaCompraState.value.peso_total/1000 + " Kg"
                                        }else {
                                            "> " + listaCompraState.value.peso_total + " Gr"
                                        }
                                )
                                //El precio total de la compra exacto.
                                Text(
                                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 42.dp),
                                    style = MaterialTheme.typography.h5.copy(color = primaryDarkColor),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    text = listaCompraState.value.precio_total.toString() + " €",
                                )
                            }
                        }
                    }
                }
            }
        }
        //Aquí está la lista horizontal de alimentos no encontrados.
        Column(
            modifier = Modifier
                .weight(2f)
                .clip(shape = RoundedCornerShape(4.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Alimentos no encontrados...",
                    style = TextStyle(
                        color = primaryDarkColor,
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                AlimentosNoEncontrados(stateListaCompra = listaCompraState)
            }
        }
    }
}
package com.otero.recipetoshop.android.presentation.components.listacompra

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
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
import com.otero.recipetoshop.android.presentation.components.util.NestedDownMenu
import com.otero.recipetoshop.android.presentation.theme.*
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.util.SupermercadosEnum
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.listacompra.ListaCompraEvents
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState
import de.charlex.compose.RevealSwipe
import java.text.DecimalFormat


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ListaCompra(
    navController: NavController,
    listaCompraState: MutableState<ListaCompraState>,
    onTriggeEvent: (ListaCompraEvents) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //TODO: Finalizar lista de la compra y dar opción al usuario para añadir los productos que le sobrarán a la despensa
                    
                },
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ){
        Column(Modifier.fillMaxSize()) {
            if(
                !listaCompraState.value.listaProductos.isEmpty()
                || !listaCompraState.value.alimentos_no_encontrados.isEmpty()
            ){
                NestedDownMenu(
                    options = listOf(
                        "Más baratos",
                        "Más ligeros",
                        "Más baratos ajustados",
                        "Supermercado más barato",
                        "Supermercado más ligero",
                        "Supermercado más ajustado"
                    ),
                    onClickItem = {
                        onTriggeEvent(ListaCompraEvents.onCLickFilter(it))
                    }
                )
            }
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .weight(6f)
            ){
                items(items = listaCompraState.value.supermercados.toList()){ supermercado ->
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
                                    url = SupermercadosEnum.getImage(supermercado),
                                    contentDescription = "logo " + supermercado.name
                                )
                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .height(254.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                LazyVerticalGrid(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp),
                                    cells = GridCells.Fixed(3)
                                ) {
                                    items(
                                        listaCompraState.value.listaProductos.filter { producto ->
                                            producto.supermercado.equals(supermercado)
                                        }
                                    ) { producto ->
                                        Column(
                                            Modifier
                                                .fillMaxSize()
                                        ) {
                                            Box(Modifier.fillMaxSize()){
                                                //La imagen del producto junto a la cantidad de unidades abajo.
                                                RevealSwipe(
                                                    modifier = Modifier
                                                        .padding(2.dp),
                                                    shape = RoundedCornerShape(8.dp),
                                                    onContentClick = {
                                                        //Cuando clica el producto seleccionado se activa o desactiva
                                                        onTriggeEvent(ListaCompraEvents.onClickProducto(producto))
                                                    },
                                                    onBackgroundEndClick = {
                                                        //Elimina el producto de la lista
                                                    },
                                                    onBackgroundStartClick = {
                                                        //Hace zoom del producto con toda la información.
                                                    },
                                                    contentColor = primaryDarkColor,
                                                    backgroundCardStartColor = primaryColor,
                                                    backgroundCardEndColor = deleteColor.copy(alpha = 0.3f),
                                                    backgroundCardContentColor = secondaryLightColor,
                                                    hiddenContentEnd = {
                                                        Icon(
                                                            modifier = Modifier
                                                                .padding(horizontal = 14.dp)
                                                                .size(22.dp),
                                                            imageVector = Icons.Outlined.Delete,
                                                            contentDescription = null
                                                        )
                                                    },
                                                    backgroundCardElevation = 4.dp,
                                                    maxRevealDp = 34.dp,
                                                    hiddenContentStart = {
                                                        Icon(
                                                            modifier = Modifier
                                                                .padding(horizontal = 14.dp)
                                                                .size(22.dp),
                                                            imageVector = Icons.Outlined.Search,
                                                            contentDescription = null,
                                                            tint = Color.White
                                                        )
                                                    }
                                                ) {
                                                    Column {
                                                        Column() {
                                                            //La imagen del producto, no importa que sea de receta o de producto.
                                                            RecetaImagen(
                                                                url = producto.imagen_src,
                                                                contentDescription = producto.nombre
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
                                                                text = producto.cantidad.toString() + " Ud",
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
                                                                    text = producto.nombre,
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
                                                                                if ((producto.cantidad * producto.peso) >= 1000) {
                                                                                    val peso = (producto.cantidad * producto.peso) / 1000
                                                                                    if (producto.tipoUnidad!!.name.equals(
                                                                                            "GRAMOS"
                                                                                        )
                                                                                    ) {
                                                                                        String.format("%.2f", peso) + " Kg"
                                                                                    } else {
                                                                                        String.format("%.2f", peso) + " L"
                                                                                    }
                                                                                } else {
                                                                                    (
                                                                                            String.format("%.0f", producto.cantidad * producto.peso) + " "
                                                                                                    + TipoUnidad.parseAbrev(producto.tipoUnidad!!)
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
                                                                        text = (producto.cantidad * producto.precio_numero).toString() + " €",
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                //Sombreado con tick de nike en el caso de que se haga click
                                                if(!producto.active){
                                                    Box(
                                                        modifier = Modifier
                                                            .matchParentSize()
                                                            .background(Color.Gray.copy(alpha = 0.90f))
                                                            .align(alignment = Alignment.Center)
                                                    ){
                                                        Column(Modifier.fillMaxSize()) {
                                                            Icon(
                                                                modifier = Modifier.fillMaxSize(),
                                                                imageVector = Icons.Outlined.Done,
                                                                contentDescription = null,
                                                                tint = primaryDarkColor
                                                            )
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
                                    Modifier
                                        .fillMaxSize()
                                        .padding(2.dp)
                                ){
                                    //El peso total de la lista de la compra aproximado, no puedo saber el peso de los productos con unidades; estimación optimista.
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .padding(start = 42.dp),
                                        style = MaterialTheme.typography.h5.copy(color = primaryDarkColor),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        text =
                                        if((listaCompraState.value.peso_total.get(supermercado))!! >= 1000){
                                            "> " + (listaCompraState.value.peso_total.get(supermercado)?.div(1000) ?: 0) + " Kg"
                                        }else {
                                            "> " + (listaCompraState.value.peso_total.get(supermercado)?.div(1000) ?: 0) + " Gr"
                                        }
                                    )
                                    //El precio total de la compra exacto.
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 42.dp),
                                        style = MaterialTheme.typography.h5.copy(color = primaryDarkColor),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        text = listaCompraState.value.precio_total.get(supermercado).toString() + " €",
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
}
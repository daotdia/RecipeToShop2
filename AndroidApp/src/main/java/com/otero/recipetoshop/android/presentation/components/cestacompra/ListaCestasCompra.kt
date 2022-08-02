package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.presentation.components.util.ChoosePicture
import com.otero.recipetoshop.android.presentation.components.util.GenericForm
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.events.cestacompra.ListaCestasCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.ListaCestasCompraState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
/*
Este componente implementa la lista de cestas de recetas y alimentos
 */
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaCestasCompra(
    navController: NavController,
    listaCestaCompraState: MutableState<ListaCestasCompraState>,
    onTriggeEvent: (ListaCestasCompraEventos) -> Any,
) {
    val dialogElement = remember { mutableStateOf(false)}
    val addPicture = remember { mutableStateOf(false)}

    //Launcher de la galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        //Llamo a la base de datos para que guarde la dirección de la imagen
        onTriggeEvent(ListaCestasCompraEventos.onAddPicture(uri.path))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogElement.value = true
                },
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
            ) {
                Icon(Icons.Filled.Add, "Añadir nueva cesta de la compra")
            }
        }
    ) {
        if(addPicture.value){
            if(dialogElement.value){
                addPicture.value = false
            }
            //Dialogo para seleccionar una foto o una camara.
            ChoosePicture(
                onDismiss = addPicture,
                onSelectMedia = { media ->
                    //TODO: según el medio seleccionado tomar una captura o ir a galeria.
                    //En el caso de que sea la galeria
                    if(media.equals("Gallery")){
                        //Lanzo el launcher de la galeria
                        galleryLauncher.launch("image/*")
                    } else if(media.equals("Camera")){

                    }
                    else {
                        println("No se debería de llegar aquí al seleccionar la camará")
                    }
                }
            )
        }
        if(dialogElement.value){
            NewCestaCompraPopUp(
                onTriggerEvent = onTriggeEvent,
//                onAddListaRecetaEventos = { nombre ->
//                    onTriggeEvent(ListaCestasCompraEventos.onAddListaRecetaEventos(nombre = nombre))
//                },
                onNewCestaCompra = dialogElement,
                navController = navController
            )
        }
        val readOnly = remember { mutableStateOf(false) }
        //Crear la lista de items.
        LazyVerticalGrid(
            modifier = Modifier
                .offset(y = 12.dp)
                .padding(8.dp),
            cells = GridCells.Fixed(2)
        ) {
            items(items = listaCestaCompraState.value.listaCestasCompra)
            { item ->
                RevealSwipe(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 5.dp),
                    directions = setOf(
                        RevealDirection.EndToStart
                    ),
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                            ,
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                ) {
                    ListaCestasCompraCard(
                        cesta = item,
                        elevation = 4.dp,
                        onClick = {
                            val idCestaCompraActual: Int = item.id_cestaCompra!!
                            //Posteriormente se navega a la pantalal de lista de recetas clicada.
                            navController.navigate(RutasNavegacion.CestaCompra.route + "/$idCestaCompraActual")
                        },
                        addPicture = addPicture
                    )
                }
            }
        }
    }
}
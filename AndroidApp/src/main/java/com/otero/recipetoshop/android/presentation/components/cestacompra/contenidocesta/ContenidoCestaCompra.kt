package com.otero.recipetoshop.android.presentation.components.cestacompra

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.otero.recipetoshop.android.presentation.components.alimentos.ListaAlimentosCestaCompra
import com.otero.recipetoshop.android.presentation.components.despensa.AlimentoPopUp
import com.otero.recipetoshop.android.presentation.components.util.CameraView
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.*
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad
import com.otero.recipetoshop.presentationlogic.events.cestacompra.CestaCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
/*
Este es el componente que implementa el contenido de cada cesta de recetas y alimentos.
 */
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ContenidoCestaCompra (
    stateCestaCompra: MutableState<CestaCompraState>,
    onTriggeEventCestaCompra: (CestaCompraEventos) -> Unit,
    navController: NavController
) {
    val newAlimentoDialog = remember { mutableStateOf(false)}
    val newRecetaDialog = remember { mutableStateOf(false)}

    //Launcher y registro de permisos
    val permission_states = rememberPermissionState(Manifest.permission.CAMERA)

    val dialogElement = remember { mutableStateOf(false)}
    val addPicture = remember { mutableStateOf(false)}
    val permisos_correctos = remember { mutableStateOf(false) }
    val rationale_camera = remember { mutableStateOf(false) }
    val rationale_external = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
    val activity = context.getActivity()!!
    val receta_actual: MutableState<Receta?> = remember { mutableStateOf(null)}

    Scaffold(
        floatingActionButton = {
            if(!addPicture.value){
                FloatingActionButton(
                    backgroundColor = primaryDarkColor,
                    contentColor = secondaryLightColor,
                    onClick = {
                        //Que hacen cuando se de el OK para calcular cesta de la compra.
                        navController.navigate(RutasNavegacion.ListaCompra.route + "/${stateCestaCompra.value.id_cestaCompra_actual}")
                    },
                    content = {
                        Icon(Icons.Filled.ShoppingCart, null)
                    }
                )
            }
        },
    ) {
        //En el caso de que se haya clicado la adición de nuevo alimento; el dialogo está por encima,.
        if(newAlimentoDialog.value){
            AlimentoPopUp(
                onAddAlimento = { nombre, tipoUnidad, cantidad  ->
                    onTriggeEventCestaCompra(
                        CestaCompraEventos.onAddAlimento(
                            nombre = nombre,
                            cantidad = cantidad.toInt(),
                            tipoUnidad = TipoUnidad.valueOf(tipoUnidad)
                        )
                    )
                },
                onNewAlimento = newAlimentoDialog,
                onAutoCompleteClick = {
                    onTriggeEventCestaCompra(
                        CestaCompraEventos.onClickAutocompleteAlimento()
                    )
                },
                onAutoCompleteChange = { nombre ->
                    onTriggeEventCestaCompra(
                        CestaCompraEventos.onAutocompleteAlimentoChange(
                            query = nombre
                        )
                    )
                },
                autocompleteResults = stateCestaCompra.value.resultadosAutoCompleteAlimentos
            )
        }

        //En el caso de que se haya clicado nueva receta de usuario.
        if(newRecetaDialog.value){
            NewRecetaPopUp(
                onAddReceta = { nombre, cantidad ->
                    onTriggeEventCestaCompra(
                        CestaCompraEventos.onAddReceta(
                            nombre = nombre,
                            cantidad = cantidad.toInt()
                        )
                    )
                    navController.navigate(RutasNavegacion.ContenidoReceta.route + "/${stateCestaCompra.value.id_cestaCompra_actual}" + "/${stateCestaCompra.value.id_receta_actual}")
                },
                onNewReceta = newRecetaDialog
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            //Aquí se emplazan las recetas Favoritas.
            Column(modifier = Modifier.weight(4f)) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Recetas Favoritas",
                        style = TextStyle(
                            color = primaryDarkColor,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaRecetasCestaCompra(
                        stateCestaCompra = stateCestaCompra,
                        onTriggeEvent = onTriggeEventCestaCompra,
                        recetasOfUser = true,
                        navController = navController,
                        recetasFavoritas = true
                    )
                }
            }
            //Aquí se emplazan las recetas del usuario.
            Column(modifier = Modifier.weight(5f)) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Recetas Propias",
                        style = TextStyle(
                            color = primaryDarkColor,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaRecetasCestaCompra(
                        stateCestaCompra = stateCestaCompra,
                        onTriggeEvent = onTriggeEventCestaCompra,
                        recetasOfUser = true,
                        navController = navController,
                        recetasFavoritas = false,
                        onNuevaReceta = {
                            newRecetaDialog.value = true
                        },
                        addPicture = {
                            addPicture.value = true

                            //Determino si ya se ha dado permiso para usar la cámara y el almacenaje externo
                            val isPermissionCamera = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                            if (isPermissionCamera == PackageManager.PERMISSION_GRANTED) {
                                permisos_correctos.value = true
                            } else {
                                when {
                                    ActivityCompat.shouldShowRequestPermissionRationale(
                                        activity,
                                        Manifest.permission.CAMERA
                                    ) -> {
                                        //TODO: Mostrar diálogo de porque es necesario el permiso de cámara
                                        rationale_camera.value = true
                                    }

                                    ActivityCompat.shouldShowRequestPermissionRationale(
                                        activity,
                                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                                    ) -> {
                                        //TODO: Mostrar diálogo de porqué son necesarios los permisos del almacenaje externo.
                                        rationale_external.value = true
                                    }
                                }
                                if (isPermissionCamera == PackageManager.PERMISSION_DENIED) {
                                    //Se deben de solicitar los permisos
                                    permission_states.launchPermissionRequest()
                                }
                            }
                        },
                        receta_actual = receta_actual,
                    )
                }
            }
            //Aquí se emplazan los alimentos sueltos a tener en cuenta.
            Column(modifier = Modifier.weight(4f)) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Alimentos Sueltos",
                        style = TextStyle(
                            color = primaryDarkColor,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaAlimentosCestaCompra(
                        stateListaRecetas = stateCestaCompra,
                        onTriggeEvent = onTriggeEventCestaCompra,
                        onClickAddAlimento = {
                            newAlimentoDialog.value = true
                        }
                    )
                }
            }
        }

        if(addPicture.value){
            if(permisos_correctos.value){
                if(dialogElement.value){
                    addPicture.value = false
                }
                //Se abre la pantalla de la cámara.
                CameraView(onImageCaptured = { uri, fromGallery ->
                    //Todo : use the uri as needed
                    println("La URI es: " + uri.path)
                    addPicture.value = false

                    if(receta_actual.value != null){
                        onTriggeEventCestaCompra(
                            CestaCompraEventos.onUpdatePicture(
                                receta = receta_actual.value!!,
                                picture = uri.path!!
                            ))
                    }

                }, onError = { imageCaptureException ->
                    //Todo: que hacer cuando haya error

                })

            }
        }
    }
}

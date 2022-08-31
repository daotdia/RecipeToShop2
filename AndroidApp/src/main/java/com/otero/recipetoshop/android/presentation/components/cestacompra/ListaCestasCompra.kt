package com.otero.recipetoshop.android.presentation.components.cestacompra

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.otero.recipetoshop.android.presentation.components.util.CameraView
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.presentationlogic.events.cestacompra.ListaCestasCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.ListaCestasCompraState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe


/*
Este componente implementa la lista de cestas de recetas y alimentos
 */
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPermissionsApi::class)
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
    val permisos_correctos = remember { mutableStateOf(false) }
    val rationale_camera = remember { mutableStateOf(false) }
    val rationale_external = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
    val activity = context.getActivity()!!
    val id_cestaCompraActual = remember { mutableStateOf(-1) }

    //Launcher y registro de permisos
    val permission_states = rememberPermissionState(Manifest.permission.CAMERA)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if(!addPicture.value){
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
        }
    ) {
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
                        .padding(vertical = 5.dp, horizontal = 5.dp),
                    directions = setOf(
                        RevealDirection.EndToStart
                    ),
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                                .clickable {
                                    onTriggeEvent(ListaCestasCompraEventos.onDeleteCestaCompra(id_cestaCompra = item.id_cestaCompra!!))
                                }
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
                        addPicture = {
                            addPicture.value = true

                            id_cestaCompraActual.value = item.id_cestaCompra!!

                            //Determino si ya se ha dado permiso para usar la cámara y el almacenaje externo
                            val isPermissionCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                            if(isPermissionCamera == PERMISSION_GRANTED){
                                permisos_correctos.value = true
                            }else {
                                when {
                                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                                        //TODO: Mostrar diálogo de porque es necesario el permiso de cámara
                                        rationale_camera.value = true
                                    }

                                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.MANAGE_EXTERNAL_STORAGE) -> {
                                        //TODO: Mostrar diálogo de porqué son necesarios los permisos del almacenaje externo.
                                        rationale_external.value = true
                                    }
                                }
                                if (isPermissionCamera == PERMISSION_DENIED){
                                    //Se deben de solicitar los permisos
                                    permission_states.launchPermissionRequest()
                                }
                            }
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

                    onTriggeEvent(ListaCestasCompraEventos.onAddPicture(
                        picture = uri.path,
                        id_cestaCompra = id_cestaCompraActual.value
                    ))
                }, onError = { imageCaptureException ->
                    //Todo: que hacer cuando haya error

                })

            }
        }
    }
}

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}


fun getPath(context: Context, uri: Uri?): String {
    var result: String? = null
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri!!, proj, null, null, null)
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(proj[0])
            result = cursor.getString(column_index)
        }
        cursor.close()
    }
    if (result == null) {
        result = "Not found"
    }
    return result
}


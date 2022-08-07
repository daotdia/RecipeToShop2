package com.otero.recipetoshop.android.presentation.components.cestacompra

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.components.errorhandle.GenericDialog
import com.otero.recipetoshop.android.presentation.theme.appShapes
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import org.intellij.lang.annotations.JdkConstants

/*
Este es el componente que implementa la card de una Cesta de recetas y alimentos.
 */
@ExperimentalComposeUiApi
@Composable
fun ListaCestasCompraCard(
    cesta: CestaCompra,
    elevation: Dp,
    onClick: () -> Unit,
    addPicture: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 128.dp)
        ,
        elevation = elevation,
        shape = appShapes.medium
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
            ,
        ){
            Row(modifier = Modifier
                .alpha(alpha = 0.65f)
                .align(Alignment.Center)
            ) {
                RecetaImagen(
                    isCestaCompra = true,
                    url = cesta.imagen,
                    contentDescription = cesta.nombre,
                )
            }

            Icon(
                Icons.Filled.AddAPhoto,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 6.dp)
                    .align(Alignment.TopEnd)
                    .clickable(onClick = {
                        //Abrir dialogo de para seleccionar o bien camera o bien galeria.
                        addPicture()
                    }),
                tint = primaryDarkColor,
                contentDescription = "No Favorito",
            )

            Text(
                text = cesta.nombre,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                ,
                style = MaterialTheme.typography.h5.copy(color = Color.Black),
                textAlign = TextAlign.Center
            )
        }
    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(top = 2.dp, bottom = 2.dp)
//            .clickable(
//                onClick = onClick
//            )
//        ,
//        elevation = elevation,
//        shape = appShapes.large
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(4.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = nombre.value,
//                style = MaterialTheme.typography.h3
//            )
//        }
//    }
}
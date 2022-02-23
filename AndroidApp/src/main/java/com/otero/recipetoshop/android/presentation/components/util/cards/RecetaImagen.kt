package com.otero.recipetoshop.android.presentation.components

import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor

@Composable
fun RecetaImagen (
    url: String,
    contentDescription: String
){
    val painter = rememberCoilPainter(request = url)
    Box(Modifier.fillMaxSize()){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
    when(painter.loadState){
        is ImageLoadState.Error -> {
            //Que quieres que ocurra cuando la imagen no se llegue a cargar por algún error.
            if(url.equals("")){
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                    {
                        Icon(
                            Icons.Filled.AddAPhoto,
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
        is ImageLoadState.Success -> {
            //Que quieras que ocurrra cuando la imagen justo se haya cargado.
        }
        is ImageLoadState.Loading -> {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(size)
//                    .background(color = Color.Red)
//            ){
//                //Que quieres que aparezca cuando la imagen esté leyéndose.
//            }
        }
    }
}
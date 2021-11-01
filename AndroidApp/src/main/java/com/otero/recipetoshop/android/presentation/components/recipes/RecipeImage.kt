package com.otero.recipetoshop.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

const val Recipe_HIGHT = 250

@Composable
fun RecipeImage (
    url: String,
    contentDescription: String
){
    val painter = rememberCoilPainter(request = url)
    Box{
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(Recipe_HIGHT.dp),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
    when(painter.loadState){
        is ImageLoadState.Error -> {
            //Que quieres que ocurra cuando la imagen no se llegue a cargar por algún error.
        }
        is ImageLoadState.Success -> {
            //Que quieras que ocurrra cuando la imagen justo se haya cargado.
        }
        is ImageLoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth().height(Recipe_HIGHT.dp)
            ){
                //Que quieres que aparezca cuando la imagen esté leyéndose.
            }
        }
    }
}
package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.otero.recipetoshop.android.presentation.components.RecetaImagen

@Composable
fun ChoosePicture (
    onDismiss: MutableState<Boolean>,
    onSelectMedia: (String) -> Unit
){
    AlertDialog(
        modifier = Modifier.wrapContentSize(),
        title = { Text("Selecciona aplicación para obtener foto") },
        text = {
            Row(content = {
                Box (
                    Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .clickable(onClick = { onSelectMedia("Camera") })
                ){
                    RecetaImagen(url = "https://lh3.googleusercontent.com/GkhngtFrSb3G9WXkWxJ9IRppbGVbNy7b_xyqa12Xa-Y3My_SXtsLamI5kR6or5zWGA=s360-rw", contentDescription = "Aplicacion Camara")
                }
                Box(
                    Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .clickable(onClick = { onSelectMedia("Gallery") })
                ){
                    RecetaImagen(url = "https://lh3.googleusercontent.com/PR4YNyUGM1GVHd8AF0_QzyPQWUntGWlixfaviDsXVinwoGrwzpaynpNiV6OgQwE8vCM=s180", contentDescription = "Aplicación Galeria")
                }
            })
        },
        buttons = {},
        onDismissRequest = { onDismiss.value = false },
    )
}
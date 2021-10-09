package com.otero.recipetoshop.android.presentation.recipe_detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DescripcionReceta(
    recipeId: Int?,
){
    if(recipeId == null) {
        Text("Error")
    }else{
        Text("Receta ID: ${recipeId}")
    }
}
package com.otero.recipetoshop.android.presentation.recipe_detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.otero.recipetoshop.domain.model.Recipe

@Composable
fun DescripcionReceta(
    recipe: Recipe?,
){
    if(recipe == null) {
        Text("Error")
    }else{
        Text("Detalles de la receta: ${recipe.title}")
    }
}
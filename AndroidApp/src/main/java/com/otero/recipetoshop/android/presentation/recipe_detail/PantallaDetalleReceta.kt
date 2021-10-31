package com.otero.recipetoshop.android.presentation.recipe_detail

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.otero.recipetoshop.android.presentation.theme.AppTheme
import com.otero.recipetoshop.domain.model.Recipe

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun DescripcionReceta(
    recipe: Recipe?,
){
    AppTheme(displayProgressBar = false, onRemoveHeadMessageFromQueue = { /*TODO*/ }) {
        if(recipe == null) {
            Text("Error")
        }else{
            Text("Detalles de la receta: ${recipe.title}")
        }
    }
}
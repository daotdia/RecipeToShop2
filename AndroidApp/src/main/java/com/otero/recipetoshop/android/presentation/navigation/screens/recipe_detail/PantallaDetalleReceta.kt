package com.otero.recipetoshop.android.presentation.navigation.screens.recipe_detail

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.otero.recipetoshop.android.presentation.components.RecipeCard
import com.otero.recipetoshop.android.presentation.components.RecipeImage
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
            Text("No se ha enocntrado la receta, receta null")
        }else{
            RecipeCard(
                recipe = recipe,
                //Implementar que hacer cuando el usuario le da click a la receta.
                onClick = {}
            )
        }
    }
}
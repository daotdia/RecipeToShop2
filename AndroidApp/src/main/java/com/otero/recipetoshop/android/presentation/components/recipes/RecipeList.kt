package com.otero.recipetoshop.android.presentation.components.recipes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.RecipeCard
import com.otero.recipetoshop.android.presentation.components.Recipe_HIGHT
import com.otero.recipetoshop.domain.model.Recipe

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onClickRecipeListItem: (Int) -> Unit
    ) {
    if(loading && recipes.isEmpty()){
        //Leyendo recetas
        LoadingRecipeListShimmer(imageHeight = Recipe_HIGHT.dp)
    }
    else if(recipes.isEmpty()){
        //No se ha encontrado lo buscado.
    }
    else{
        LazyColumn {
            itemsIndexed(
                items = recipes
            ){ index,recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = {
                        onClickRecipeListItem(recipe.id)
                    }
                )
            }
        }
    }
}
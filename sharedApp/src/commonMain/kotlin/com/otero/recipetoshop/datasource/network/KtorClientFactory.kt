package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.datasource.network.model.RecipeDetailsDto
import com.otero.recipetoshop.datasource.network.model.RecipeDto
import com.otero.recipetoshop.datasource.network.model.RecipeSearchDto
import io.ktor.client.*

expect class KtorClientFactory() {
    fun build(): HttpClient
}

fun RecipeDto.toRecipe(): Recipe {
    //Obtengo el objeto json, el cual contiene el objeto json
    //interno con los atributos de la receta.
    return recipe.toRecipe()
}

fun RecipeDetailsDto.toRecipe(): Recipe{
    return Recipe(
        image = image,
        id = id,
        title = title,
        ingredients = ingredients,
        publisher = publisher,
        sourceURL = sourceUrl,
        rating = rating
    )
}

fun RecipeSearchDto.toRecipe(): Recipe{
    return Recipe(
        image = image,
        title = title,
        ingredients = emptyList(),
        id = 0,
        sourceURL = sourceUrl,
        publisher = publisher,
        rating = Double.NaN
    )
}

fun List<RecipeSearchDto>.toRecipeList(): List<Recipe>{
    return map{it.toRecipe()}
}
package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.datasource.network.model.RecipeDetailsDto
import com.otero.recipetoshop.datasource.network.model.RecipeDto
import io.ktor.client.*

expect class KtorClientFactory() {
    fun build(): HttpClient
}

fun RecipeDto.toRecipe(): Recipe {
    //Obtengo el objeto json, el cual contiene el objeto json
    //interno con los atributos de la receta.
    return recipe.toRecipeDetails()
}

fun RecipeDetailsDto.toRecipeDetails(): Recipe{
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

fun List<RecipeDetailsDto>.toRecipeList(): List<Recipe>{
    return map{it.toRecipeDetails()}
}
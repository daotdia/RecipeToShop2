package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.datasource.network.model.Antiguo.RecipeDetailsDto
import com.otero.recipetoshop.datasource.network.model.Antiguo.RecipeDto
import com.otero.recipetoshop.datasource.network.model.Antiguo.RecipeSearchDto
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
        ingredients = emptyList(), //Chapuza por API externa
        id = 0, //Chapuza por API externa
        sourceURL = sourceUrl,
        publisher = publisher,
        rating = Double.NaN // Chapuza por API externa
    )
}

fun List<RecipeSearchDto>.toRecipeList(): List<Recipe>{
    return map{it.toRecipe()}
}
package com.otero.recipetoshop.datasource.cache

import com.otero.recipetoshop.domain.model.Recipe
import com.squareup.sqldelight.db.SqlDriver

class RecipeDataBaseFactory(
    private val driverFactory: DriverFactory,
) {
    fun createDataBase(): RecipeDataBase {
        return RecipeDataBase(driverFactory.createDriver())
    }
}

expect class DriverFactory{
    fun createDriver(): SqlDriver
}

fun Recipe_Entity.toRecipe(): Recipe{
    return Recipe(
        id = id.toInt(),
        title = title,
        publisher = publisher,
        rating = rating.toDouble(),
        image = featured_image,
        sourceURL = source_url,
        ingredients = ingredients.convertIngredientsToList()
    )
}

fun List<Recipe_Entity>.toRecipeList(): List<Recipe>{
    return map{it.toRecipe()}
}

fun List<String>.convertIngredientListToString(): String{
    val ingredientsString = StringBuilder()
    for(ingredient in this){
        ingredientsString.append("$ingredient,")
    }
    return ingredientsString.toString()
}

fun String.convertIngredientsToList(): List<String>{
    val list:ArrayList<String> = ArrayList()
    for (ingredient in split((","))){
        list.add(ingredient)
    }
    return list
}
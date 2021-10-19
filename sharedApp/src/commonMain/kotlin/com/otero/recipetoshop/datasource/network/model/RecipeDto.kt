package com.otero.recipetoshop.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class RecipeDto(
    //Obtengo el objeto Json de la receta que lo instancio como una data class aparte.
    @SerialName("recipe")
    val recipe: RecipeDetailsDto
)

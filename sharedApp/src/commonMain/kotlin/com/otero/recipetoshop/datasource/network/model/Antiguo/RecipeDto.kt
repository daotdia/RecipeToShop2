package com.otero.recipetoshop.datasource.network.model.Antiguo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    //Obtengo el objeto Json de la receta que lo instancio como una data class aparte.
    @SerialName("recipe")
    val recipe: RecipeDetailsDto
)

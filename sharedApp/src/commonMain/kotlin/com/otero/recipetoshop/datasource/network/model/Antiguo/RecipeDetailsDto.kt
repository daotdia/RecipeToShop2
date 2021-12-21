package com.otero.recipetoshop.datasource.network.model.Antiguo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailsDto(
    //Este es el objeto json interno con los atributos que necesito para la receta
    @SerialName("title")
    val title: String,
    @SerialName("recipe_id")
    val id: Int,
    @SerialName("ingredients")
    val ingredients: List<String> = emptyList(),
    @SerialName("image_url")
    val image: String,
    @SerialName("social_rank")
    val rating: Double,
    @SerialName("publisher")
    val publisher: String,
    @SerialName("source_url")
    val sourceUrl: String
) {
}

package com.otero.recipetoshop.datasource.network.model.Antiguo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RecipeSearchDto(
    @SerialName("title")
    val title: String,
    @SerialName("id")
    val id: String,
    @SerialName("imageUrl")
    val image: String,
    @SerialName("socialUrl")
    val rating: Double,
    @SerialName("publisher")
    val publisher: String,
    @SerialName("sourceUrl")
    val sourceUrl: String
) {
}
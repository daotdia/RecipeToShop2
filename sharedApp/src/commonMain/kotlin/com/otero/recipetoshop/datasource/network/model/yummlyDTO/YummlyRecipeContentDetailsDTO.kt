package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentDetailsDTO(
    @SerialName("directionsUrl")
    val url: String?,
    @SerialName("totalTime")
    val cookTime: String?,
    @SerialName("images")
    val detailsImageContenedor: List<YummlyRecipeContentDetailsImageSourceDTO>,
    @SerialName("name")
    val nombre: String?,
    @SerialName("recipeId")
    val recipeId: String?,
    @SerialName("numberOfServings")
    val comensales: Int?,
    @SerialName("globalId")
    val globalId: String?,
)

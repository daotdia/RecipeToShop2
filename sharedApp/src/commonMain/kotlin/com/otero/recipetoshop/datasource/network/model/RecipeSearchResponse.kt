package com.otero.recipetoshop.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeSearchResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("recipes")
    val recipes: List<RecipeSearchDto>,
)
package com.otero.recipetoshop.datasource.network.model.yummly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentDTO(
    @SerialName("description")
    val description: String,
    @SerialName("details")
    val details: YummlyRecipeContentDetailsDTO,
    @SerialName("ingredientLines")
    val ingredientsObject: List<YumlyRecipeContentIngredientsDTO>,
    @SerialName("reviews")
    val reviewsObject: YummlyRecipeContentReviewsDTO
)

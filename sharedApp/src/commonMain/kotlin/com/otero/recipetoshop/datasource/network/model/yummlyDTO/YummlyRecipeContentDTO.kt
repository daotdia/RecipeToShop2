package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentDTO(
    @SerialName("details")
    val details: YummlyRecipeContentDetailsDTO,
    @SerialName("ingredientLines")
    val ingredientsObject: List<YumlyRecipeContentIngredientsDTO>,
    @SerialName("reviews")
    val reviewsObject: YummlyRecipeContentReviewsDTO,
    @SerialName("nutrition")
    val contentNutritionObject: YummlyRecipeContentNutritionDTO,
)

package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentNutritionEstimatesDTO(
    @SerialName("attribute")
    val nameNutritionAtributeRecipe: String?,
    @SerialName("value")
    val valueNutritionAtributeRecipe: Float?,
    @SerialName("unit")
    val unitObjectValueNutritionAtributeRecipe: YummlyRecipeContentNutritionEstimatesMetricDTO
)

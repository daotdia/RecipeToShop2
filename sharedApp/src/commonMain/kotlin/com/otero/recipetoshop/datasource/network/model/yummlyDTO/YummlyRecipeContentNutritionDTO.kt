package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentNutritionDTO(
    @SerialName("nutritionEstimates")
    val nutritionRecipeEstimates: List<YummlyRecipeContentNutritionEstimatesDTO>
)

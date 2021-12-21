package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentNutritionEstimatesMetricDTO(
    @SerialName("plural")
    val unitNameValueNutritionAtributeRecipe: String?,
    @SerialName("abbreviation")
    val unitSiglaValueNutritionAtributeRecipe: String?,

)

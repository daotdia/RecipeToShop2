package com.otero.recipetoshop.datasource.network.model.yummly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YumlyRecipeContentIngredientAmountSIDetailsDTO(
    @SerialName("name")
    val metricName: String,
    @SerialName("kind")
    val metricType: String,
    @SerialName("decimal")
    val isDecimal: Boolean
)

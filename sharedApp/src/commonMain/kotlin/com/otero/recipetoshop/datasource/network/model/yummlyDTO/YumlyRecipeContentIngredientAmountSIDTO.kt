package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YumlyRecipeContentIngredientAmountSIDTO(
    @SerialName("unit")
    val metricSIDetails: YumlyRecipeContentIngredientAmountSIDetailsDTO,
    @SerialName("quantity")
    val metricAmount: Float?
)

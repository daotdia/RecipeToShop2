package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentDetailsImageSourceDTO(
    @SerialName("resizableImageUrl")
    val imageUrl: String?
)

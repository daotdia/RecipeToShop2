package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlySearchResponseDTO(
    @SerialName("feed")
    val feed: List<YummlyRecipeDTO>,
)

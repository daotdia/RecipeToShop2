package com.otero.recipetoshop.datasource.network.model.yummly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeMetaDTO(
    @SerialName("source")
    val sourceRecipeObject: YummlyRecipeMetaSourceDTO,
)

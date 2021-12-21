package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeDTO(
    @SerialName("content")
    val content: YummlyRecipeContentDTO,
    @SerialName("type")
    val recipeType: String?,
    @SerialName("display")
    val metaRecipe: YummlyRecipeMetaDTO,
    @SerialName("searchResult")
    val metaSearchRecipe: YummlyRecipeMetaSearchDTO
)

package com.otero.recipetoshop.datasource.network.model.yummly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeDTO(
    @SerialName("content")
    val content: YummlyRecipeContentDTO,
    @SerialName("nutrition")
    val nutritionObject: YummlyRecipeNutritionDTO,
    @SerialName("type")
    val recipeType: String,
    @SerialName("display")
    val metaRecipe: YummlyRecipeMetaDTO,
    @SerialName("searchResult")
    val metaSearchRecipt: YummlyRecipeMetaSearchDTO
)

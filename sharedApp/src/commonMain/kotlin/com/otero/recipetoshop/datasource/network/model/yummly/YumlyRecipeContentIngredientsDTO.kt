package com.otero.recipetoshop.datasource.network.model.yummly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YumlyRecipeContentIngredientsDTO(
    @SerialName("category")
    val ingredientCategory: String,
    @SerialName("amount")
    val ingredientAmountObject: YumlyRecipeContentIngredientAmountDTO,
    @SerialName("ingredient")
    val ingredientName: String,
    @SerialName("id")
    val ingredientId: String,
    @SerialName("wholeline")
    val ingredientMoreInformation: String
)

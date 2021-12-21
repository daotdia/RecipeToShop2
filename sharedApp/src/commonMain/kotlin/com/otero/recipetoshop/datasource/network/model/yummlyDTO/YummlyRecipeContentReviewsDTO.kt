package com.otero.recipetoshop.datasource.network.model.yummlyDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YummlyRecipeContentReviewsDTO(
    @SerialName("totalReviewCount")
    val numReviews: Int?,
    @SerialName("averageRating")
    val ratingReviews: Float?,
)

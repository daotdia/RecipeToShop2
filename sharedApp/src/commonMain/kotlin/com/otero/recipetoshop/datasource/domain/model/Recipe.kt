package com.otero.recipetoshop.datasource.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json

data class Recipe(
    val id: Int,
    val title: String,
    val publisher: String,
    val image: String,
    val rating: Double,
    val sourceURL: String,
    val ingredients: List<String> = listOf()
)

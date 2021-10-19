package com.otero.recipetoshop.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val publisher: String,
    val image: String,
    val rating: Double,
    val sourceURL: String,
    val ingredients: List<String> = listOf()
)

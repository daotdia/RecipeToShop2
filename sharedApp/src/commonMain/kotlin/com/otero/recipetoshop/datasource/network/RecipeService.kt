package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.Recipe

interface RecipeService {

    suspend fun search(
        page: Int,
        query: String,
    ): List<Recipe>

    suspend fun get(
        id: Int,
    ): Recipe
}
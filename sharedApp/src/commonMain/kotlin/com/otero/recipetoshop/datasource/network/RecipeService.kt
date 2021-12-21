package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.Recipe

interface RecipeService {

    suspend fun search(
        maxItems: Int,
        offset: Int,
        maxSeconds: Int,
        query: String,
    ): List<Recipe>
}
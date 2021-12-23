package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.model.recetas.YummlyRecipe

interface RecipeService {

    suspend fun search(
        maxItems: Int,
        offset: Int,
        maxSeconds: Int,
        query: String,
    ): List<Receta>
}
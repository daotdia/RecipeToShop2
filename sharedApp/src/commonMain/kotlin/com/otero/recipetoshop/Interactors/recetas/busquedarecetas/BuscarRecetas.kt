package com.otero.recipetoshop.Interactors.recetas.busquedarecetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.network.RecipeService
import com.otero.recipetoshop.domain.model.recetas.YummlyRecipe
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BuscarRecetas (
    private val recetaCache: RecetaCache,
    private val recipeService: RecipeService
    ) {
    fun searchRecipes(
        query: String,
        maxItems: Int,
        offset: Int,
        maxSeconds: Int
    ): Flow<DataState<List<YummlyRecipe>>> = flow{
        emit(DataState.loading())

        println("Llego al interactor")

        try{
            val recipeRespone = recipeService.search(
                query = query,
                offset = offset,
                maxItems = maxItems,
                maxSeconds = maxSeconds
            )

            emit(DataState.data(message = null, data = recipeRespone))
        } catch (e: Exception){
            println("Error a la hora de conectar con API: " + e.message)
        }

    }
}
package com.otero.recipetoshop.Interactors.RecipeList

import com.otero.recipetoshop.datasource.cache.RecipeCache
import com.otero.recipetoshop.datasource.network.RecipeService
import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes (
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
        ) {
    fun execute(
        page: Int,
        query: String,
    ): Flow<DataState<List<Recipe>>> = flow{
        //Para emitir loading, es lo que de primeras hace el Flow.
        emit(DataState.loading())

        //Para emitir las recetas coincidentes, una vez comience a emitir el loading es false.
        try {
            val recipes = recipeService.search(
                page = page,
                query = query
            )
            recipeCache.insert(recipes)

            println("Lista de recetas obtenidas de network: " + recipes)
            val cacheResult = if(query.isBlank()){
                recipeCache.getAll(page = page)
            }else{
                recipeCache.search(
                    query = query,
                    page = page
                )
            }
            println("Lista de recetas guardadas en la network " + cacheResult)
            emit(DataState.data(message = null, data = cacheResult))
        } catch (e: Exception){
            //Para emitir error
            emit(DataState.error(message = e.message ?: "Error desconocido"))
        }
    }
}
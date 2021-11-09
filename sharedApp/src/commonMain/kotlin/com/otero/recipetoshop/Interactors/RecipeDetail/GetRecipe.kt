package com.otero.recipetoshop.Interactors.RecipeDetail

import com.otero.recipetoshop.datasource.cache.RecipeCache
import com.otero.recipetoshop.datasource.network.RecipeService
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipe (
    private val recipeCache: RecipeCache
        ){
    fun execute(
        recipeid: Int,
    ): Flow<DataState<Recipe>> = flow{
        emit(DataState.loading())

        try {
            val recipe = recipeCache.get(recipeid)
            emit(DataState.data(message = null, data = recipe))
        } catch (e: Exception){
            emit(DataState.error<Recipe>(
                message = GenericMessageInfo.Builder()
                    .id("GetRecipe Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message?: "Unknown Error")
            ))
        }
    }
}
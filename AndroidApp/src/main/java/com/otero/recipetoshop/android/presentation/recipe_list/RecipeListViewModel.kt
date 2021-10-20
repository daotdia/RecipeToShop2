package com.otero.recipetoshop.android.presentation.recipe_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.RecipeList.SearchRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle, //Para recuperar datos de estados anteriores
    private val searchRecipes: SearchRecipes

):ViewModel()
{
     fun loadRecipes(){
        searchRecipes.execute(
            page= 1,
            query = "chicken"
        ).onEach { dataState ->
            println("RecipeListVM: ${dataState.isLoading}")

            dataState.data?.let { recipes ->
                println("RecipeListVM: ${recipes}")
            }

            dataState.message?.let { message ->
                println("RecipeListVM: ${message}")
            }
        }.launchIn(viewModelScope)
    }
}

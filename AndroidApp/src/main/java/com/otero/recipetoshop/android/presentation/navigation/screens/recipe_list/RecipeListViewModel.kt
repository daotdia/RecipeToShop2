package com.otero.recipetoshop.android.presentation.navigation.screens.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.RecipeList.SearchRecipes
import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.presentattion.screens.recipe_list.RecipeListState
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
    //Creo una clase donde guardo el estado del viewModel mutable.
    val state: MutableState<RecipeListState> = mutableStateOf(RecipeListState())

     fun loadRecipes(){
         //Actualizo el estado del viewModel confoeme llegan daaStates asíncronos.
        searchRecipes.execute(
            page= state.value.page,
            query = state.value.query
        ).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { recipes ->
                appendRecipes(recipes)
            }

            dataState.message?.let { message ->
                println("RecipeListVM: ${message}")
            }
        }.launchIn(viewModelScope)
    }

    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(state.value.recipes)
        current.addAll(recipes)
        state.value = state.value.copy(recipes = current)
    }
}

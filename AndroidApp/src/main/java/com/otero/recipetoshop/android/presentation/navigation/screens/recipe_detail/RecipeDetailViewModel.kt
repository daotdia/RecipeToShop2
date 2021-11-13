package com.otero.recipetoshop.android.presentation.navigation.screens.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

//@HiltViewModel
//class RecipeDetailViewModel
//@Inject
//constructor(
//    private val savedStateHandle: SavedStateHandle,
//    //private val getRecipe: GetRecipe
//):ViewModel()
//{
//    val recipe: MutableState<Recipe?> = mutableStateOf(null)
//    init {
//        try {
//            savedStateHandle.get<Int>("recipeId")?.let{ recipeId ->
//                viewModelScope.launch {
//                   getRecipe(recipeid = 0)// En cacherecipe sólo está la última receta de la búsqueda de pollo porque las id de la API no tienen sentido y se sobrescriben todas como 0
//                }
//            }
//        } catch (e: Exception){
//            println("Algo salió mal")
//        }
//    }
//
//    private fun getRecipe(recipeid: Int){
//        getRecipe.execute(recipeid = recipeid).onEach { dataState ->
//            println("RecipeDetailVM: ${dataState.isLoading}")
//
//            dataState.data?.let { recipe ->
//                println("RecipeDetailVM: ${recipe}")
//                this.recipe.value = recipe
//            }
//
//            dataState.message?.let { message ->
//                println("RecipeDetailVM: ${message}")
//            }
//        }.launchIn(viewModelScope)
//    }
//}

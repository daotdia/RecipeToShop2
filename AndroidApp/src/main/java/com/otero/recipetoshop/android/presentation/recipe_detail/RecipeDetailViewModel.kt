package com.otero.recipetoshop.android.presentation.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.datasource.network.RecipeService
import com.otero.recipetoshop.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val recipeService: RecipeService
):ViewModel()
{
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    init {
        try {
            savedStateHandle.get<Int>("recipeId")?.let{ recipeId ->
                viewModelScope.launch {
                    recipe.value = recipeService.get(recipeId)
                    println("KtorTest ViewModel: ${recipe.value!!.title}")
                }
            }
        } catch (e: Exception){
            println("Algo salió mal")
        }
    }
}

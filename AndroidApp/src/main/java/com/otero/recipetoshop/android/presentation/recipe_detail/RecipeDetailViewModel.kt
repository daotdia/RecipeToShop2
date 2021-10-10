package com.otero.recipetoshop.android.presentation.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.otero.recipetoshop.android.dependenceinjection.Prueba
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val prueba: Prueba,
):ViewModel()
{
    val recipeId: MutableState<Int?> = mutableStateOf(null)
    init {
            savedStateHandle.get<Int>("recipeId")?.let{ recipeId ->
                this.recipeId.value = recipeId
            }
        println("RecipeDetailViewModel: ${prueba.description()}")
    }
}
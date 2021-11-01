package com.otero.recipetoshop.presentattion.screens.recipe_list

import com.otero.recipetoshop.domain.model.Recipe

data class RecipeListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val query: String = "",
    val recipes: List<Recipe> = listOf()
) {

}
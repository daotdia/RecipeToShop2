package com.otero.recipetoshop.presentationlogic.states.recipe_list

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.domain.util.Queue

data class RecipeListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val query: String = "",
    val recipes: List<Recipe> = listOf(),
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
) {

}
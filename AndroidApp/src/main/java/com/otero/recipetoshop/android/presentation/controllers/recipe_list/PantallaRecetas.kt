package com.otero.recipetoshop.android.presentation.controllers.recipe_list

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.otero.recipetoshop.Interactors.RecipeList.RecipeListEvents
import com.otero.recipetoshop.android.presentation.components.recipes.RecipeList
import com.otero.recipetoshop.android.presentation.components.util.SearchBarRecetas
import com.otero.recipetoshop.android.presentation.theme.AppTheme
import com.otero.recipetoshop.presentationlogic.states.recipe_list.RecipeListState

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun ListaReceta(
    state: RecipeListState,
    onTriggerEvent: (RecipeListEvents) -> Unit,
    onClickRecipeListItem: (Int) -> Unit
){
    AppTheme(
        displayProgressBar = state.isLoading,
        onRemoveHeadMessageFromQueue = {
            onTriggerEvent(RecipeListEvents.OnRemoveHeadMessageFromQueu)
        },
        dialogQueue = state.queueError
    ) {
        Scaffold(
            topBar = {
                SearchBarRecetas(
                    query = state.query,
                    onQueryChanged = {
                        //Actualizar estado de barar de búsqueda (su contenido).
                        onTriggerEvent(RecipeListEvents.OnUpdateQuery(it))
                    },
                    onExecuteSearch = {
                        //Realizar la búsqueda de recetas.
                        onTriggerEvent(RecipeListEvents.NewSearch)
                    }
                )
            }
        ){
            RecipeList(
                loading = state.isLoading,
                recipes = state.recipes,
                page = state.page,
                onTriggerNextPage = {
                    onTriggerEvent(RecipeListEvents.NextPage)
                },
                onClickRecipeListItem = onClickRecipeListItem
            )
        }
    }
}
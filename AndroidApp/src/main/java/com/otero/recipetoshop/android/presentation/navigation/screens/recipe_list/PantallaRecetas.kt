package com.otero.recipetoshop.android.presentation.navigation.screens.recipe_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.Interactors.RecipeList.RecipeListEvents
import com.otero.recipetoshop.android.presentation.components.RecipeCard
import com.otero.recipetoshop.android.presentation.components.recipes.RecipeList
import com.otero.recipetoshop.android.presentation.components.util.SearchAppBar
import com.otero.recipetoshop.android.presentation.theme.AppTheme
import com.otero.recipetoshop.presentattion.screens.recipe_list.RecipeListState

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
                SearchAppBar(
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
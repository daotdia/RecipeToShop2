package com.otero.recipetoshop.android.presentation.navigation.screens.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.RecipeList.RecipeListEvents
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

    init{
        onTriggerEvent(RecipeListEvents.LoadRecipes)
    }

    fun onTriggerEvent(event: RecipeListEvents){
        when(event){
            RecipeListEvents.LoadRecipes -> {
                loadRecipes()
            }
            RecipeListEvents.NextPage -> {
                //Pasar a siguiente página
                nextPage()
            }
            RecipeListEvents.NewSearch -> {
                //EJecutar una nueva búsqueda de la query actual en el state.
                newSearch()
            }
            is RecipeListEvents.OnUpdateQuery -> {
                //Actualizo el valor de query del estado con la query actual en pantalla.
                state.value = state.value.copy(query = event.query)
            }
            else -> {
                //Manejar los errores.
                handleError("Evento desconocido")
            }
        }
    }

    private fun newSearch(){
        //Reseteo la lista de recetas actual.
        state.value = state.value.copy(page = 1, recipes = listOf())
        //Realizo la busqueda con la nueva query (ya está en el state actual).
        loadRecipes()
    }

    private fun nextPage(){
        state.value = state.value.copy(page = state.value.page + 1)
        loadRecipes()
    }

    private fun loadRecipes(){
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
                handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(state.value.recipes)
        current.addAll(recipes)
        state.value = state.value.copy(recipes = current)
    }

    private fun handleError(errorMessage: String){
        //Por hacer el manejo de errores
    }
}

package com.otero.recipetoshop.android.presentation.navigation.screens.recetas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.recetas.AddNewListaReceta
import com.otero.recipetoshop.Interactors.recetas.OnEnterListaDeRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecetasListViewModel
@Inject
constructor(
    private val addNewListaReceta: AddNewListaReceta,
    private val onEnterListaDeRecetas: OnEnterListaDeRecetas
): ViewModel(){
    val listadelistarecetasstate: MutableState<RecetasListState> = mutableStateOf(RecetasListState())
    val listaRecetasState = mutableStateOf(RecetasListState())

    init {

    }

    fun onTriggerEvent(event: RecetasListEvents){
        when(event){
            is RecetasListEvents.onAddreceta -> {
                addReceta()
            }
            is RecetasListEvents.onEnterListaDeLisaDeRecetas -> {
                printRecetas(event.nombre)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    //Metodo para imprimir las recetas guardades en la lista de recetas en cache.
    private fun printRecetas(nombre: String) {
        //Obtener las reectas e ingredientes de la lista de recetas clicckada.
        onEnterListaDeRecetas.onEnterReceta(nombre).onEach { dataState ->
            dataState.data?.let { recetas ->
                listaRecetasState.value = listaRecetasState.value.copy(recetas = recetas)
            }
        }.launchIn(viewModelScope)
    }

    fun addReceta() {
    }
}



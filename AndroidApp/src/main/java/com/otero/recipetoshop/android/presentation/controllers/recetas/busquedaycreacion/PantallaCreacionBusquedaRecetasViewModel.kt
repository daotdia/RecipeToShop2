package com.otero.recipetoshop.android.presentation.controllers.recetas.busquedaycreacion

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.recetas.busquedarecetas.BuscarRecetas
import com.otero.recipetoshop.Interactors.recetas.listarecetas.AddRecetaListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.model.recetas.YummlyRecipestoRecipeList
import com.otero.recipetoshop.events.recetas.BusquedaCreacionRecetasEvents
import com.otero.recipetoshop.events.recetas.RecetaListEvents
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetasCreacionBusquedaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PantallaCreacionBusquedaRecetasViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val buscarRecetas: BuscarRecetas,
    private val addRecetaListaRecetas: AddRecetaListaRecetas,
    ): ViewModel(){
    val creacionBusquedarecetas = mutableStateOf(RecetasCreacionBusquedaState())

    init {
        try {
            savedStateHandle.get<Int>("listarecetasid")?.let{ listarecetasid ->
                viewModelScope.launch {
                    creacionBusquedarecetas.value = creacionBusquedarecetas.value.copy(id_listaRecetas = listarecetasid)
                    if (creacionBusquedarecetas.value.id_listaRecetas == -1){
                        throw Exception("ID listarecteaactual == -1")
                    }
                }
            }
        } catch (e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }
    }

    fun onTriggerEvent(event: BusquedaCreacionRecetasEvents){
        when(event){
            is BusquedaCreacionRecetasEvents.onAddUserReceta -> {
                addRecetaUserToListaRecetas(event.nombre, event.cantidad)
            }
            is BusquedaCreacionRecetasEvents.buscarRecetas -> {
                creacionBusquedarecetas.value = creacionBusquedarecetas.value.copy(lisaRecetasBuscadas = emptyList())
                buscarRecetasYummly()
            }
            is BusquedaCreacionRecetasEvents.updateQuery -> {
                updateQueryRecetas(event.newQuery)
            }
            is BusquedaCreacionRecetasEvents.onAddYummlyReceta -> {
                addRecetaYummlyListaRecetas(event.receta)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun addRecetaYummlyListaRecetas(receta: Receta) {
        addRecetaListaRecetas.addRecetaListaRecetas(receta = receta).onEach { dataState ->
            dataState.data?.let { id_receta ->
                println("Añadida receta Yummly con nombre; " + receta.nombre)
                addRecetaListaRecetas.addIngredientesReceta(receta = receta, id_receta = id_receta) .onEach { dataState ->
                    dataState.data?.let { exito ->
                        if(exito) println("Añadidos los ingredientes de receta Yummly con nombre: " + receta.nombre)
                    }
                }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }


    private fun updateQueryRecetas(newQuery: String) {
        creacionBusquedarecetas.value = creacionBusquedarecetas.value.copy(query = newQuery)
    }

    private fun buscarRecetasYummly() {
        println("Llego al ViewModel")
        buscarRecetas.searchRecipes(
            query = creacionBusquedarecetas.value.query,
            maxSeconds = 7000,
            maxItems = 30,
            offset = 0
        ).onEach { dataState ->
            dataState.data?.let { recetasYummly ->
                creacionBusquedarecetas.value = creacionBusquedarecetas.value.copy(lisaRecetasBuscadas = recetasYummly)
            }
        }.launchIn(viewModelScope)
    }

    private fun addRecetaUserToListaRecetas(nombre: String, cantidad: Int) {
        val receta = Receta(
            id_listaRecetas = creacionBusquedarecetas.value.id_listaRecetas,
            nombre = nombre,
            cantidad = cantidad,
            user = true,
            active = true
        )
        addRecetaListaRecetas.addRecetaListaRecetas(receta = receta).onEach { dataState ->
            dataState.data?.let { id_receta ->
                println("Añadida receta Yummly con nombre; " + receta.nombre)
            }
        }.launchIn(viewModelScope)
    }
}
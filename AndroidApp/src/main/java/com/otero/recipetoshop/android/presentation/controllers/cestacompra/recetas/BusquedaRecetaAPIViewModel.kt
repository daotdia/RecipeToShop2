package com.otero.recipetoshop.android.presentation.controllers.cestacompra.recetas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.cestascompra.recetas.busquedarecetas.BuscarRecetasAPI
import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.AddRecetaCestaCompra
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.events.cestacompra.BusquedaRecetasAPIEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.BusquedaRecetasAPIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusquedaRecetaAPIViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val buscarRecetasAPI: BuscarRecetasAPI,
    private val addRecetaCestaCompra: AddRecetaCestaCompra,
    ): ViewModel(){
    val busquedaRecetasAPIState = mutableStateOf(BusquedaRecetasAPIState())

    init {
        try {
            savedStateHandle.get<Int>("cestacompraid")?.let{ cestaCompraID ->
                viewModelScope.launch {
                    busquedaRecetasAPIState.value = busquedaRecetasAPIState.value.copy(id_cestaCompra = cestaCompraID)
                    if (busquedaRecetasAPIState.value.id_cestaCompra == -1){
                        throw Exception("ID listarecteaactual == -1")
                    }
                }
            }
        } catch (e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }
    }

    fun onTriggerEvent(event: BusquedaRecetasAPIEventos){
        when(event){
            is BusquedaRecetasAPIEventos.onAddUserReceta -> {
                addRecetaUserToCestaCompra(event.nombre, event.cantidad)
            }
            is BusquedaRecetasAPIEventos.buscarRecetasEventos -> {
                busquedaRecetasAPIState.value = busquedaRecetasAPIState.value.copy(lisaRecetasBuscadas = emptyList())
                buscarRecetasAPI()
            }
            is BusquedaRecetasAPIEventos.updateQuery -> {
                updateQueryRecetas(event.newQuery)
            }
            is BusquedaRecetasAPIEventos.onAddYummlyReceta -> {
                addRecetaAPIToCestaCompra(event.receta)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun addRecetaAPIToCestaCompra(receta: Receta) {
        addRecetaCestaCompra.addRecetaCestaCompra(receta = receta).onEach { dataState ->
            dataState.data?.let { id_receta ->
                println("Añadida receta Yummly con nombre; " + receta.nombre)
                addRecetaCestaCompra.addIngredientesReceta(receta = receta, id_receta = id_receta) .onEach { dataState ->
                    dataState.data?.let {
                    }
                }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }


    private fun updateQueryRecetas(newQuery: String) {
        busquedaRecetasAPIState.value = busquedaRecetasAPIState.value.copy(query = newQuery)
    }

    private fun buscarRecetasAPI() {
        println("Llego al ViewModel")
        buscarRecetasAPI.buscarRecetasAPI(
            query = busquedaRecetasAPIState.value.query,
            maxSeconds = 7000,
            maxItems = 30,
            offset = 0
        ).onEach { dataState ->
            dataState.data?.let { recetasYummly ->
                busquedaRecetasAPIState.value = busquedaRecetasAPIState.value.copy(lisaRecetasBuscadas = recetasYummly)
            }
        }.launchIn(viewModelScope)
    }

    private fun addRecetaUserToCestaCompra(nombre: String, cantidad: Int) {
        val receta = Receta(
            id_cestaCompra = busquedaRecetasAPIState.value.id_cestaCompra,
            nombre = nombre,
            cantidad = cantidad,
            user = true,
            active = true
        )
        addRecetaCestaCompra.addRecetaCestaCompra(receta = receta).onEach { dataState ->
            dataState.data?.let { id_receta ->
                println("Añadida receta Yummly con nombre; " + receta.nombre)
            }
        }.launchIn(viewModelScope)
    }
}
package com.otero.recipetoshop.android.presentation.navigation.screens.recetas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.recetas.AddElementoListaRecetas
import com.otero.recipetoshop.Interactors.recetas.GetListaRecetas
import com.otero.recipetoshop.Interactors.recetas.OnEnterListaDeRecetas
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.recetas.RecetaListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecetaListViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val onEnterListaDeRecetas: OnEnterListaDeRecetas,
    private val addElementoListaRecetas: AddElementoListaRecetas,
    private val getListaRecetas: GetListaRecetas
): ViewModel() {
    val listaRecetasState = mutableStateOf(RecetasListState())
    init {
        //Obtengo en primer lugar el id de la lista de recetas actual desde navegacion.
        try {
            savedStateHandle.get<Int>("listarecetasid")?.let{ listarecetasid ->
                viewModelScope.launch {
                    listaRecetasState.value = listaRecetasState.value.copy(id_listaReceta_actual = listarecetasid)
                    if (listaRecetasState.value.id_listaReceta_actual == null){
                        throw Exception("ID listarecteaactual == null")
                    }
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
                }
            }
        } catch (e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }
    }

    fun onTriggerEvent(event: RecetaListEvents){
        when(event){
            is RecetaListEvents.onAddReceta -> {
                addRecetaToListaRecetas(event.nombre, event.cantidad)
            }
            is RecetaListEvents.onAddAlimento -> {
                addAlimentoListaReceta(event.nombre, event.cantidad, event.tipoUnidad)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun addAlimentoListaReceta(nombre: String, cantidad: Int, tipoUnidad: TipoUnidad) {
        val alimento = Food(
            id_listaRecetas = listaRecetasState.value.id_listaReceta_actual!!,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad
        )
        addElementoListaRecetas.insertAlimentosListaRecetas(alimento = alimento).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addRecetaToListaRecetas(nombre: String, cantidad: Int) {
        val receta = Receta(
            id_listaRecetas = listaRecetasState.value.id_listaReceta_actual!!,
            nombre = nombre,
            cantidad = cantidad
        )
        addElementoListaRecetas.addRecetaListaRecetas(receta = receta).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    //Metodo para imprimir los elementos guardados en la lista de recetas en cache.
    private fun rePrintElementosDeListaRecetas(id_listaRecetas: Int) {
        //Reseteo el estado actual de recetas y alimentos.
        listaRecetasState.value = listaRecetasState.value.copy(recetas = listOf())
        //Obtener las recetas de la lista de recetas clicckada.
        onEnterListaDeRecetas.getRecetasListaRecetas(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let { recetas ->
                listaRecetasState.value = listaRecetasState.value.copy(recetas = recetas)
            }
        }.launchIn(viewModelScope)

        listaRecetasState.value = listaRecetasState.value.copy(alimentos = listOf())
        //Obtener los alimentos de la lista de recetas clicada.
        onEnterListaDeRecetas.getAlimentosListaRecetas(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let { alimentos ->
                listaRecetasState.value = listaRecetasState.value.copy(alimentos = alimentos)
            }
        }.launchIn(viewModelScope)
    }
}
package com.otero.recipetoshop.android.presentation.navigation.screens.recetas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.recetas.AddElementoListaRecetas
import com.otero.recipetoshop.Interactors.recetas.AddNewListaRecetas
import com.otero.recipetoshop.Interactors.recetas.OnEnterListaDeRecetas
import com.otero.recipetoshop.Interactors.recetas.PrintListaDeListasRecetas
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad
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
    private val addNewListaRecetas: AddNewListaRecetas,
    private val onEnterListaDeRecetas: OnEnterListaDeRecetas,
    private  val printListaDeListasRecetas: PrintListaDeListasRecetas,
    private val addElementoListaRecetas: AddElementoListaRecetas

): ViewModel(){
    val listadelistarecetasstate: MutableState<RecetasListState> = mutableStateOf(RecetasListState())
    val listaRecetasState = mutableStateOf(RecetasListState())

    init {
        rePrintListaDeListasRecetas()
    }

    fun onTriggerEvent(event: RecetasListEvents){
        when(event){
            is RecetasListEvents.onAddListaReceta -> {
                addListaReceta(event.nombre)
            }
            is RecetasListEvents.onEnterListaDeLisaDeRecetas -> {
                listaRecetasState.value = listaRecetasState.value.copy(id_listaReceta = event.id_listaRecetas)
                rePrintElementosDeListaRecetas(event.id_listaRecetas)
            }
            is RecetasListEvents.onAddReceta -> {
                addRecetaToListaRecetas(event.nombre, event.cantidad)
            }
            is RecetasListEvents.onAddAlimento -> {
                addAlimentoListaReceta(event.nombre, event.cantidad, event.tipoUnidad)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun addAlimentoListaReceta(nombre: String, cantidad: Int, tipoUnidad: TipoUnidad) {
        val alimento = Food(
            id_listaRecetas = listaRecetasState.value.id_listaReceta!!,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad
        )
        addElementoListaRecetas.insertAlimentosListaRecetas(alimento = alimento).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addRecetaToListaRecetas(nombre: String, cantidad: Int) {
        val receta = Receta(
            id_listaRecetas = listaRecetasState.value.id_listaReceta!!,
            nombre = nombre,
            cantidad = cantidad
        )
        addElementoListaRecetas.addRecetaListaRecetas(receta = receta).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addListaReceta(nombre: String) {
        val listaRecetas = ListaRecetas(nombre = nombre)
        addNewListaRecetas.addListaRecetas(listareceta = listaRecetas).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintListaDeListasRecetas()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun rePrintListaDeListasRecetas() {
        //Obtengo la lsita de listas de recetas de cache.
        printListaDeListasRecetas.printListaDeListasrecetas().onEach { dataState ->
            dataState.data?.let { listasRecetas ->
                listaRecetasState.value = listaRecetasState.value.copy(listasRecetas = listasRecetas)
            }
        }.launchIn(viewModelScope)

    }

    //Metodo para imprimir los elementos guardados en la lista de recetas en cache.
    private fun rePrintElementosDeListaRecetas(id_listaRecetas: Int) {
        //Reseteo el estado actual de recetas y alimentos.
        listaRecetasState.value = listaRecetasState.value.copy(recetas = listOf())
        listaRecetasState.value = listaRecetasState.value.copy(alimentos = listOf())
        //Obtener las recetas de la lista de recetas clicckada.
        onEnterListaDeRecetas.getRecetasListaRecetas(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let { recetas ->
                listaRecetasState.value = listaRecetasState.value.copy(recetas = recetas)
            }
        }.launchIn(viewModelScope)
        //Obtener los alimentos de la lista de recetas clicada.
        onEnterListaDeRecetas.getAlimentosListaRecetas(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let { alimentos ->
                listaRecetasState.value = listaRecetasState.value.copy(alimentos = alimentos)
            }
        }.launchIn(viewModelScope)
    }
}



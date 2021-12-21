package com.otero.recipetoshop.android.presentation.navigation.screens.recetas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.recetas.busquedarecetas.BuscarRecetas
import com.otero.recipetoshop.Interactors.recetas.listaldelistasrecetas.GetListaRecetas
import com.otero.recipetoshop.Interactors.recetas.listarecetas.*
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
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
    private val addRecetaListaRecetas: AddRecetaListaRecetas,
    private val addAlimentoListaRecetas: AddAlimentoListaRecetas,
    private val deleteAlimentoListaRecetas: DeleteAlimentoListaRecetas,
    private val deleteRecetaListaRecetas: DeleteRecetaListaRecetas,
    private val updateReceta: UpdateReceta,
    private val updateAlimentoListaRecetas: UpdateAlimentoListaRecetas,
    private val buscarRecetas: BuscarRecetas,
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
            is RecetaListEvents.onAddUserReceta -> {
                addRecetaUserToListaRecetas(event.nombre, event.cantidad)
            }
            is RecetaListEvents.onAddAlimento -> {
                addAlimentoListaReceta(event.nombre, event.cantidad, event.tipoUnidad)
            }
            is RecetaListEvents.onDeleteAlimento -> {
                deleteAlimento(event.alimento)
            }
            is RecetaListEvents.onDeleteReceta -> {
                deleteReceta(event.receta)
            }
            is RecetaListEvents.onRecetaClick ->  {
                updateReceta(receta = event.receta, active = event.active)
            }
            is RecetaListEvents.onAlimentoClick -> {
                updateAlimento(alimento = event.alimento, active = event.active)
            }
            is RecetaListEvents.buscarRecetas -> {
                buscarRecetasYummly(query = event.query)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun buscarRecetasYummly(query: String) {
        println("Llego al ViewModel")
        buscarRecetas.searchRecipes(
            query = query,
            maxSeconds = 7000,
            maxItems = 100,
            offset = 0
        ).onEach { dataState ->
            dataState.data?.let { recetasYummly ->
                println("Estas es la primera receta incontrada con nombre: " + recetasYummly.first().content.details.nombre)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateAlimento(alimento: Food, active: Boolean, cantidad: Int? = null) {
        updateAlimentoListaRecetas.updateAlimentoListaRecetas(
            alimento = alimento,
            active = active,
            cantidad = cantidad).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateReceta(receta: Receta, active: Boolean = true, cantidad: Int? = null) {
        updateReceta.updateReceta(receta = receta, active = active, cantidad = cantidad).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteReceta(receta: Receta) {
        deleteRecetaListaRecetas.deleteRecetaListaRecetas(receta = receta).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAlimento(alimento: Food) {
        deleteAlimentoListaRecetas.deleteAlimentoListaRecetas(alimento = alimento).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun addAlimentoListaReceta(nombre: String, cantidad: Int, tipoUnidad: TipoUnidad) {
        val alimento = Food(
            id_listaRecetas = listaRecetasState.value.id_listaReceta_actual!!,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad,
            active = true
        )
        addAlimentoListaRecetas.insertAlimentosListaRecetas(alimento = alimento).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addRecetaUserToListaRecetas(nombre: String, cantidad: Int) {
        val receta = Receta(
            id_listaRecetas = listaRecetasState.value.id_listaReceta_actual!!,
            nombre = nombre,
            cantidad = cantidad,
            user = true,
            active = true
        )
        addRecetaListaRecetas.addRecetaListaRecetas(receta = receta).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(listaRecetasState.value.id_listaReceta_actual!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    //Metodo para imprimir los elementos guardados en la lista de recetas en cache.
    private fun rePrintElementosDeListaRecetas(id_listaRecetas: Int) {
        //Reseteo el estado actual de recetas y alimentos (tantos los activados omo los inactivados).
        listaRecetasState.value = listaRecetasState.value.copy(recetasActive = listOf())
        listaRecetasState.value = listaRecetasState.value.copy(recetasInactive = listOf())
        //Obtener las recetas de la lista de recetas clicckada.
        onEnterListaDeRecetas.getRecetasListaRecetas(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let { recetas: Pair<List<Receta>?, List<Receta>?> ->
                if(recetas.first != null){
                    listaRecetasState.value = listaRecetasState.value.copy(recetasActive = recetas.first!!)
                }
                if(recetas.second != null){
                    listaRecetasState.value = listaRecetasState.value.copy(recetasInactive = recetas.second!!)
                }
            }
            //Actualizo las recetas activas e inactivas actuales para poder pintarlo.
            val recetas_actuales: ArrayList<Receta> = arrayListOf()
            recetas_actuales.addAll(listaRecetasState.value.recetasActive)
            recetas_actuales.addAll(listaRecetasState.value.recetasInactive)
            listaRecetasState.value = listaRecetasState.value.copy(allrecetas = recetas_actuales)
        }.launchIn(viewModelScope)

        listaRecetasState.value = listaRecetasState.value.copy(alimentosActive = listOf())
        listaRecetasState.value = listaRecetasState.value.copy(alimentosInactive = listOf())
        //Obtener los alimentos de la lista de recetas clicada.
        onEnterListaDeRecetas.getAlimentosListaRecetas(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let {  alimentos: Pair<List<Food>?, List<Food>?> ->
                println("Numero de alimentos inactivos: " + alimentos.second?.size)
                if(alimentos.first != null){
                    listaRecetasState.value = listaRecetasState.value.copy(alimentosActive = alimentos.first!!)
                }
                if(alimentos.second != null){
                    listaRecetasState.value = listaRecetasState.value.copy(alimentosInactive = alimentos.second!!)
                }
            }
            //Actualizo los alimentos activos e inactivos para poder pintarlos.
            val alimentos_actuales: ArrayList<Food> = arrayListOf()
            alimentos_actuales.addAll(listaRecetasState.value.alimentosActive)
            alimentos_actuales.addAll(listaRecetasState.value.alimentosInactive)
            println("Numero de alimentos inactivos guardados en esstado: " + listaRecetasState.value.alimentosInactive)
            listaRecetasState.value = listaRecetasState.value.copy(allAlimentos = alimentos_actuales)
        }.launchIn(viewModelScope)
    }
}
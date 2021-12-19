package com.otero.recipetoshop.android.presentation.navigation.screens.recetas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.recetas.*
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.recetas.ListOfRecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.ListOfRecetasListState
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListOfRecetasListViewModel
@Inject
constructor(
    private val addNewListaRecetas: AddNewListaRecetas,
    private  val printListaDeListasRecetas: PrintListaDeListasRecetas,

): ViewModel(){
    val listadelistarecetasstate: MutableState<ListOfRecetasListState> = mutableStateOf(ListOfRecetasListState())

    init {
        rePrintListaDeListasRecetas()
    }

    fun onTriggerEvent(event: ListOfRecetasListEvents):Any {
        when(event){
            is ListOfRecetasListEvents.onAddListaReceta -> {
                val id = addListaReceta(event.nombre)
                return id
            }
//            is ListOfRecetasListEvents.onEnterListaDeLisaDeRecetas -> {
//                //Guardo el id actual de la lista de recetas, es importante para guardar futuros nuevos elementos de la lista.
////                listaRecetasActualState.value = listaRecetasActualState.value.copy(id_listaReceta_actual = event.id_listaRecetas)
//                rePrintListaDeListasRecetas(event.id_listaRecetas)
//            }
            else -> {
                throw Exception("ERROR")
            }
        }
        return Unit
    }

    private fun addListaReceta(nombre: String):Int {
        val listaRecetas = ListaRecetas(nombre = nombre)
        var idRecetaActual: Int? = null
        addNewListaRecetas.addListaRecetas(listareceta = listaRecetas).onEach { dataState ->
            dataState.data?.let { id ->
                idRecetaActual = id
                val listaRecetaActual = ListaRecetas(
                    id_listaRecetas = id,
                    nombre = nombre
                )
                addListaRecetasToListasRecetas(listaRecetaActual)
            }
        }.launchIn(viewModelScope)
        return idRecetaActual!!
    }

    private fun rePrintListaDeListasRecetas() {
        //Obtengo la lsita de listas de recetas de cache.
        printListaDeListasRecetas.printListaDeListasrecetas().onEach { dataState ->
            dataState.data?.let { listasRecetas ->
                listadelistarecetasstate.value = listadelistarecetasstate.value.copy(listaDeListasRecetas = listasRecetas)
            }
        }.launchIn(viewModelScope)
    }
    private fun addListaRecetasToListasRecetas(listaRecetas: ListaRecetas){
        val listasRecetasActuales: ArrayList<ListaRecetas> = ArrayList(listadelistarecetasstate.value.listaDeListasRecetas)
        listasRecetasActuales.add(listaRecetas)
        listadelistarecetasstate.value = listadelistarecetasstate.value.copy(listaDeListasRecetas = emptyList())
        listadelistarecetasstate.value = listadelistarecetasstate.value.copy(listaDeListasRecetas = listasRecetasActuales)
    }


}



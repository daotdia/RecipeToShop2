package com.otero.recipetoshop.android.presentation.controllers.cestacompra

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.cestascompra.AddNewCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.PrintListaCestasCompra
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.events.cestacompra.ListaCestasCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.ListaCestasCompraState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListaCestasCompraListViewModel
@Inject
constructor(
    private val addNewCestaCompra: AddNewCestaCompra,
    private  val printListaCestasCompra: PrintListaCestasCompra,

    ): ViewModel(){
    val listadelistarecetasstate: MutableState<ListaCestasCompraState> = mutableStateOf(ListaCestasCompraState())

    init {
        rePrintListaDeListasRecetas()
    }

    fun onTriggerEvent(event: ListaCestasCompraEventos):Any {
        when(event){
            is ListaCestasCompraEventos.onAddListaRecetaEventos -> {
                val id = addListaReceta(event.nombre)
                return id
            }
//            is ListaCestasCompraEventos.onEnterCestaCompraEventos -> {
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
        val listaRecetas = CestaCompra(nombre = nombre)
        var idRecetaActual: Int? = null
        addNewCestaCompra.addCestaCompra(cestaCompra = listaRecetas).onEach { dataState ->
            dataState.data?.let { id ->
                idRecetaActual = id
                val listaRecetaActual = CestaCompra(
                    id_cestaCompra = id,
                    nombre = nombre
                )
                addListaRecetasToListasRecetas(listaRecetaActual)
            }
        }.launchIn(viewModelScope)
        return idRecetaActual!!
    }

    private fun rePrintListaDeListasRecetas() {
        //Obtengo la lsita de listas de recetas de cache.
        printListaCestasCompra.printListaCestasCompra().onEach { dataState ->
            dataState.data?.let { listasRecetas ->
                listadelistarecetasstate.value = listadelistarecetasstate.value.copy(listaCestasCompra = listasRecetas)
            }
        }.launchIn(viewModelScope)
    }
    private fun addListaRecetasToListasRecetas(cestaCompra: CestaCompra){
        val listasRecetasActuales: ArrayList<CestaCompra> = ArrayList(listadelistarecetasstate.value.listaCestasCompra)
        listasRecetasActuales.add(cestaCompra)
        listadelistarecetasstate.value = listadelistarecetasstate.value.copy(listaCestasCompra = emptyList())
        listadelistarecetasstate.value = listadelistarecetasstate.value.copy(listaCestasCompra = listasRecetasActuales)
    }


}


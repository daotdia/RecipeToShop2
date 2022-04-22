package com.otero.recipetoshop.android.presentation.controllers.listacompra

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.otero.recipetoshop.Interactors.listacompra.CalcularProductos
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.events.listacompra.ListaCompraEvents
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@HiltViewModel
class ListaCompraViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val calcularProductos: CalcularProductos
):ViewModel(){
    val listaCompraState: MutableState<ListaCompraState> = mutableStateOf(ListaCompraState())
    init {
        try {
            //Obtengo el id de la cesta de la compra a tarnsformar
            savedStateHandle.get<Int>("cestacompraid")?.let { cestacompraid ->
                viewModelScope.launch {
                    listaCompraState.value =
                        listaCompraState.value.copy(id_cestaCompra = cestacompraid)
                    if (listaCompraState.value.id_cestaCompra == -1) {
                        throw Exception("ID cestacompraid == -1")
                    }
                }
            }
        } catch(e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }

        //Actualizo la lista de la compra con los productos calculados.
        calcularProductos(id_cestaCompra = listaCompraState.value.id_cestaCompra)
    }

    fun onTriggerEvent(event: ListaCompraEvents){

    }

    private fun calcularProductos(id_cestaCompra: Int) {
        //Calculo los productos.
        calcularProductos.calcularProductos(
            id_cestaCompra = listaCompraState.value.id_cestaCompra
        ).onEach { dataState ->
            dataState.data?.let { productos ->
                //Actualizo el estado con los productos ya calculados
                listaCompraState.value = listaCompraState.value.copy(listaProductos = productos)
            }
        }.launchIn(viewModelScope)
    }
}
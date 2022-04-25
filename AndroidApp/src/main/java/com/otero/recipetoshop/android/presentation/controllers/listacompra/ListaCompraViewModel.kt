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
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.events.listacompra.ListaCompraEvents
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@HiltViewModel
class ListaCompraViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val calcularProductos: CalcularProductos,
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

        //Si hemos llegado directos del menú de navegación, entonces compruebo si hay lista calculada, sino folio en blanco de momento.
        if(listaCompraState.value.id_cestaCompra == -1){
            val productos: Productos = getProductos()
            if(!productos.productos.isNullOrEmpty()){
                //Obtengo el id de la cesta de la compra de los productos del primer producto.
                listaCompraState.value = listaCompraState.value.copy(id_cestaCompra = productos.id_cestaCompra)
                //Actualizo la lista de la compra con los productos en cache.
                actualizarLista(productos = productos.productos_cache)
                //Actualizo los productos no encontrados a partir del id de la cesta de la compra
                TODO("Tras guardar los alimentos no encontrados junto a los productos de la lisa, obtnerlos aquí")
            }
        } else {
            //Actualizo la lista de la compra con los productos calculados.
            calcularProductos()
        }
    }

    private fun getProductos(): Productos {
        TODO("Obtener los productos guardados haciendo un interactor más")
    }

    fun onTriggerEvent(event: ListaCompraEvents){

    }

    private fun calcularProductos() {
        //Calculo los productos.
        calcularProductos.calcularProductos(
            id_cestaCompra = listaCompraState.value.id_cestaCompra
        ).onEach { dataState ->
            dataState.data?.let { alimentos_productos ->
                //Actualizo el precio total y el peso total
                actualizarLista(productos = alimentos_productos.second)
                //Actualizo los alimentos no encontrados.
                actualizarAlimentosNoEncontrados(alimentosNecesarios = alimentos_productos.first)
            }
        }.launchIn(viewModelScope)
    }

    private fun actualizarLista(productos: List<Productos.Producto>) {
        //Actualizo los productos.
        listaCompraState.value = listaCompraState.value.copy(listaProductos = productos)
        //Actualizo el precio total y atualizo el peso total de los productos (considero que los ml pesan igual que los Kg).
        var precio_total = 0f
        var peso_total = 0f
        for(producto in productos){
            precio_total += producto.precio_numero * producto.cantidad
            //En el caso del peso desecho los productos que sean unidades o sea null, por lo que siempre es una estimación optimista.
            if((producto.tipoUnidad != null && !producto.tipoUnidad!!.name.equals("UNIDADES"))){
                peso_total += producto.peso*producto.cantidad
            }
        }
        //Los actualizo.
        listaCompraState.value = listaCompraState.value.copy(
            precio_total = precio_total,
            peso_total = peso_total
        )
    }

    //Metodo para actualizar los alimentos que no han sido encontrados o que ha habido problemas en calcular las cantidades.
    private fun actualizarAlimentosNoEncontrados(alimentosNecesarios: ArrayList<Alimento>) {
        val alimentosNoEncontrados: ArrayList<Alimento> = arrayListOf()
        for(alimento in alimentosNecesarios){
            var match: Boolean = false
            for(producto in listaCompraState.value.listaProductos){
                //Si encuentra al menos un producto de su tipo; sí ha sido encontrado.
                if(producto.query.trim().lowercase().equals(alimento.nombre.trim().lowercase())){
                    match = true
                }
            }
            //Si no se ha encontrado ningún producto con dicho alimento, este se añade a los alimentos np encontrados a la lista de la compra.
            if(!match){
                alimentosNoEncontrados.add(alimento)
            }
        }
        //Actualizo la lista de alimentos no encontrados en el state de la lista de la compra.
        listaCompraState.value = listaCompraState.value.copy(alimentos_no_encontrados = alimentosNoEncontrados)
    }
}
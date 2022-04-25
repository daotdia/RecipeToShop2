package com.otero.recipetoshop.android.presentation.controllers.listacompra

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.listacompra.*
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.ListaCompra.toAlimentos
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.despensa.toProductos
import com.otero.recipetoshop.domain.util.DataState
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
    private val getProductos: ObtenerProductos,
    private val deleteProductos: DeleteProductos,
    private val getAlimentosNoEncontradosCache: GetAlimentosNoEncontradosCache,
    private val saveListaCompra: SaveListaCompra
):ViewModel(){
    val listaCompraState: MutableState<ListaCompraState> = mutableStateOf(ListaCompraState())
    init {
        try {
            //Obtengo el id de la cesta de la compra a tarnsformar
            savedStateHandle.get<Int>("cestacompraid")?.let { cestacompraid ->
                viewModelScope.launch {
                    listaCompraState.value = listaCompraState.value.copy(id_cestaCompra = cestacompraid)
                }
            }
        } catch(e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }

        //Si hemos llegado directos del menú de navegación, entonces compruebo si hay lista calculada, sino folio en blanco de momento.
        if(listaCompraState.value.id_cestaCompra == -1){
            actualizaLista()
        }
        //En caso contrario estamos llegando desde el cálculo de una cesta de la compra, hay que calcularlos.
        else {
            //Elimino de cache la lista anterior si la hubiese.
            deleteProductosCache()
            //Actualizo la lista de la compra con los productos calculados.
            calcularProductos()
        }
    }

    fun onTriggerEvent(event: ListaCompraEvents){
    }

    private fun deleteProductosCache() {
        deleteProductos.deleteProductos().onEach { DataState ->
            DataState.data?.let {
                //Productos eliminados
            }
        }.launchIn(viewModelScope)
    }

    private fun actualizaLista() {
        //Obtengo y actualizo productos encontrados.
        getProductosEncontrados()
        //Obtengo y actualizo productos no encontrados.
        getProductosNoEncontrados()
        //Actualizo el precio y peso totales de la lista de la compra-
        actualizarListaProductosEncontrados(listaCompraState.value.listaProductos)
    }

    private fun getProductosNoEncontrados(){
        getAlimentosNoEncontradosCache.getAlimentosNoEncontradosCache().onEach {DataState ->
            DataState.data?.let { productos ->
               if(!productos.isNullOrEmpty()){
                   //Actualizo la lista de la compra con los productos encontrados en cache.
                   listaCompraState.value = listaCompraState.value.copy(
                       listaProductos = productos,
                   )
               }
            }
        }.launchIn(viewModelScope)
    }

    private fun getProductosEncontrados() {
        getProductos.obtenerProductos().onEach {DataState ->
            DataState.data?.let { productosNoEncontrados ->
                if(!productosNoEncontrados.productos_cache.isNullOrEmpty()){
                    //Actualizo la lista de la compra con los productos no encontrados en cache.
                    listaCompraState.value = listaCompraState.value.copy(
                        alimentos_no_encontrados = productosNoEncontrados.productos_cache.toAlimentos(),
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun calcularProductos() {
        //Calculo los productos.
        calcularProductos.calcularProductos(
            id_cestaCompra = listaCompraState.value.id_cestaCompra
        ).onEach { dataState ->
            dataState.data?.let { alimentos_productosEncontrados ->
                //Actualizo el precio total y el peso total
                actualizarListaProductosEncontrados(productosEncontrados = alimentos_productosEncontrados.second)
                //Calculo los alimentos no encontrados.
                calcularAlimentosNoEncontrados(alimentosNecesarios = alimentos_productosEncontrados.first)
                //Guardo los productos encontrados en cache.
                saveProductosEncontrados()
                //Guardo los productos no encontrados en cache.
                saveProductosNoEncontrados()
            }
        }.launchIn(viewModelScope)
    }

    private fun saveProductosNoEncontrados() {
        //Tranformo los productos no encontrados en alimentos.
        val productosNoEncontrados = listaCompraState.value.alimentos_no_encontrados.toProductos()
        //Guardo en cache los alimentos no enocntrados
        saveListaCompra.saveAlimentosNoEncontrados(
            id_cestaCompra = listaCompraState.value.id_cestaCompra,
            productosNoEncontrados = productosNoEncontrados
        ).onEach { dataState ->
            dataState.data?.let {
                //Productos no encontrados guardados
            }
        }.launchIn(viewModelScope)
    }

    private fun saveProductosEncontrados() {
        //Guardo en cache los productos encontrados de la cesta de la compra actual.
        saveListaCompra.saveProductos(
            id_cestaCompra = listaCompraState.value.id_cestaCompra,
            productos = listaCompraState.value.listaProductos
        ).onEach { dataState ->
            dataState.data?.let {
                //Productos encontrados guardados
            }
        }.launchIn(viewModelScope)
    }

    private fun actualizarListaProductosEncontrados(productosEncontrados: List<Productos.Producto>) {
        //Actualizo los productos.
        listaCompraState.value = listaCompraState.value.copy(listaProductos = productosEncontrados)
        //Actualizo el precio total y atualizo el peso total de los productos (considero que los ml pesan igual que los Kg).
        var precio_total = 0f
        var peso_total = 0f
        for(producto in productosEncontrados){
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

    //Metodo para calcular los alimentos que no han sido encontrados o que ha habido problemas en calcular las cantidades.
    private fun calcularAlimentosNoEncontrados(alimentosNecesarios: ArrayList<Alimento>) {
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
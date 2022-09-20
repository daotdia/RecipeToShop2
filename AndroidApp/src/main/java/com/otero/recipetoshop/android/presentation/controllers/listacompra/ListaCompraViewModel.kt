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
import com.otero.recipetoshop.domain.model.ListaCompra.toAlimento
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.despensa.toProductos
import com.otero.recipetoshop.domain.dataEstructres.FilterEnum
import com.otero.recipetoshop.domain.dataEstructres.SupermercadosEnum
import com.otero.recipetoshop.presentationlogic.events.listacompra.ListaCompraEvents
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
    private val saveListaCompra: SaveListaCompra,
    private val updateProducto: UpdateProducto,
    private val finalizarCompra: FinalizarCompra
):ViewModel(){
    //Estado de la lista de la compra
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
            //Lo sé porque al navegar hasta aquí guardo en savedStateHandle -1 como id de lista de la compra.
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
        when(event){
            is ListaCompraEvents.onCLickFilter -> {
                //Actualizo la lsita de la compra según el filter aplicado
                aplicarFlitro(FilterEnum.parseString(event.filter_nombre))
            }

            is ListaCompraEvents.onClickProducto -> {
                actualizarProducto(active = !event.producto.active, producto = event.producto)
            }

            is ListaCompraEvents.onFinishCompra -> {
                finalizarCompra()
            }
            else -> {
                print("Evento de la lista de la compra no esperado.")
            }
        }
    }

    private fun finalizarCompra() {
        finalizarCompra.finalizarCompra(
            id_cestaCompra = listaCompraState.value.id_cestaCompra,
            productos = ArrayList(listaCompraState.value.listaProductos)
        ).onEach { dataState ->
        }.launchIn(viewModelScope)
    }

    private fun actualizarProducto(active: Boolean, producto: Productos.Producto) {
        updateProducto.updateProducto(active = active, producto = producto).onEach { dataState ->
            //Actualizo la lista de la compra
            actualizaLista()
        }.launchIn(viewModelScope)
    }

    private fun aplicarFlitro(filter: FilterEnum) {
        //Seteo la lista de la compra a los productos anteriores.
        var id_anterior = -1
        if(listaCompraState.value.listaProductos.isNotEmpty()){
            id_anterior = listaCompraState.value.listaProductos.first().id_cestaCompra
        } else if(listaCompraState.value.alimentos_no_encontrados.isNotEmpty()) {
            id_anterior = listaCompraState.value.alimentos_no_encontrados.first().id_cestaCompra!!
        }
        listaCompraState.value = listaCompraState.value.copy(id_cestaCompra = id_anterior)
        //Elimino los productos calculados hasta le momento
        deleteProductosCache()
        //Calculo los productos con el filtro seleccionado
        calcularProductos(filter = filter)
    }

    private fun deleteProductosCache() {
        deleteProductos.deleteProductos().onEach { DataState ->
            DataState.data?.let {
                //Productos eliminados
            }
        }.launchIn(viewModelScope)
    }

    private fun actualizaLista() {
        //TODO: Falta que los supermercados se puedan elegir dinámicamente
        listaCompraState.value = listaCompraState.value.copy(
            supermercados = mutableSetOf(
                SupermercadosEnum.CARREFOUR,
                SupermercadosEnum.MERCADONA,
                SupermercadosEnum.DIA
            )
        )
        //Obtengo y actualizo productos encontrados.
        getProductosEncontrados()
        //Obtengo y actualizo productos no encontrados.
        getProductosNoEncontrados()
    }

    private fun getProductosNoEncontrados(){
        getAlimentosNoEncontradosCache.getAlimentosNoEncontradosCache().onEach {DataState ->
            DataState.data?.let { productosNoEncontrados ->
               if(!productosNoEncontrados.isEmpty()){
                   //Actualizo la lista de la compra con los productos encontrados en cache.
                   listaCompraState.value = listaCompraState.value.copy(
                       //Devuelvo la lista de los productos no encontrados como alimentos con ID -1.
                       alimentos_no_encontrados = productosNoEncontrados.map{ producto ->
                           producto.toAlimento().copy(id_alimento = -1)
                       },
                   )
               }
            }
        }.launchIn(viewModelScope)
    }

    private fun getProductosEncontrados() {
        getProductos.obtenerProductos().onEach {DataState ->
            DataState.data?.let { productosEncontrados ->
                if(!productosEncontrados.productos_cache.isEmpty()){
                    //Actualizo la lista de la compra con los productos no encontrados en cache.
                    listaCompraState.value = listaCompraState.value.copy(
                        listaProductos = productosEncontrados.productos_cache,
                        id_cestaCompra = productosEncontrados.id_cestaCompra
                    )
                    //Actualizo el precio y peso totales de la lista de la compra-
                    actualizarListaProductosEncontrados(listaCompraState.value.listaProductos)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun calcularProductos(filter: FilterEnum = FilterEnum.BARATOS, supermercadosEnum: SupermercadosEnum = SupermercadosEnum.CARREFOUR) {
        //TODO: Falta que los supermercados se puedan elegir dinámicamente
        listaCompraState.value = listaCompraState.value.copy(
            supermercados = mutableSetOf(
                SupermercadosEnum.CARREFOUR,
                SupermercadosEnum.MERCADONA,
                SupermercadosEnum.DIA
            )
        )

        //Calculo los productos.
        calcularProductos.calcularProductos(
            id_cestaCompra = listaCompraState.value.id_cestaCompra,
            supermercados = listaCompraState.value.supermercados,
            filter = filter
        ).onEach { dataState ->
            dataState.data?.let { alimentos_productosEncontrados ->
                //Actualizo el precio total y el peso total
                actualizarListaProductosEncontrados(productosEncontrados = alimentos_productosEncontrados.second)
                //Calculo los alimentos no encontrados.
                calcularAlimentosNoEncontrados(
                    alimentosNecesarios = alimentos_productosEncontrados.first,
                    productos_encontrados = alimentos_productosEncontrados.second
                )
                //Guardo los productos encontrados en cache.
                saveProductosEncontrados(productos_encontrados = alimentos_productosEncontrados.second)
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
                //Actualizo la lista.
                actualizaLista()
            }
        }.launchIn(viewModelScope)
    }

    private fun saveProductosEncontrados(productos_encontrados: List<Productos.Producto>) {
        //Guardo en cache los productos encontrados de la cesta de la compra actual.
        saveListaCompra.saveProductos(
            id_cestaCompra = listaCompraState.value.id_cestaCompra,
            productos = productos_encontrados
        ).onEach { dataState ->
            dataState.data?.let {
                //Productos encontrados guardados
            }
        }.launchIn(viewModelScope)
    }

    private fun actualizarListaProductosEncontrados(productosEncontrados: List<Productos.Producto>) {

        //Actualizo los supermercados seleccionados y con productos encontrados.
        actualziarSupermercados(productosEncontrados)

        //Actualizo el precio total y atualizo el peso total de los productos (considero que los ml pesan igual que los Kg).
        var precio_total_carrefour = 0f
        var precio_total_dia = 0f
        var precio_total_mercadona = 0f

        var peso_total_carrefour = 0f
        var peso_total_dia = 0f
        var peso_total_mercadona = 0f

        for(producto in productosEncontrados){
            when(producto.supermercado){
                SupermercadosEnum.CARREFOUR -> {
                    precio_total_carrefour += producto.cantidad * producto.precio_numero
                    if((producto.tipoUnidad != null && !producto.tipoUnidad!!.name.equals("UNIDADES"))){
                        peso_total_carrefour += producto.cantidad * producto.peso
                    }
                }
                SupermercadosEnum.MERCADONA -> {
                    precio_total_mercadona += producto.cantidad * producto.precio_numero
                    if((producto.tipoUnidad != null && !producto.tipoUnidad!!.name.equals("UNIDADES"))){
                        peso_total_mercadona += producto.cantidad * producto.peso
                    }
                }
                SupermercadosEnum.DIA -> {
                    precio_total_dia += producto.cantidad * producto.precio_numero
                    if((producto.tipoUnidad != null && !producto.tipoUnidad!!.name.equals("UNIDADES"))){
                        peso_total_dia += producto.cantidad * producto.peso
                    }
                }
            }
        }

        //Los actualizo.
        listaCompraState.value = listaCompraState.value.copy(
            precio_total = hashMapOf(
                SupermercadosEnum.CARREFOUR to precio_total_carrefour,
                SupermercadosEnum.DIA to precio_total_dia,
                SupermercadosEnum.MERCADONA to precio_total_mercadona
            ),
            peso_total = hashMapOf(
                SupermercadosEnum.CARREFOUR to peso_total_carrefour,
                SupermercadosEnum.DIA to peso_total_dia,
                SupermercadosEnum.MERCADONA to peso_total_mercadona
            )
        )
    }

    //Metodo para calcular los alimentos que no han sido encontrados o que ha habido problemas en calcular las cantidades.
    private fun calcularAlimentosNoEncontrados(alimentosNecesarios: ArrayList<Alimento>, productos_encontrados: List<Productos.Producto>) {
        val alimentosNoEncontrados: ArrayList<Alimento> = arrayListOf()
        for(alimento in alimentosNecesarios){
            var match: Boolean = false
            for(producto in productos_encontrados){
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

    private fun actualziarSupermercados(productos: List<Productos.Producto>){
        val supermercados_actuales: MutableSet<SupermercadosEnum> = mutableSetOf()
        for(supermercado in SupermercadosEnum.values()){
            if (
                productos.any{ producto ->
                    producto.supermercado.equals(supermercado)
                }
            ){
                supermercados_actuales.add(supermercado)
            }
        }
        listaCompraState.value = listaCompraState.value.copy(supermercados = supermercados_actuales)
    }
}
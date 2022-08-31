package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class SaveListaCompra (
    private val recetaCache: RecetaCache
){
    fun saveProductos(
        id_cestaCompra: Int,
        productos: List<Productos.Producto>
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())

        //Inserto los productos encontrados.
        for(producto in productos){
            recetaCache.insertProducto(
                id_cestaCompra = id_cestaCompra,
                producto = producto
            )
        }

        emit(DataState(data = Unit, message = null))
    }.asCommonFlow()

    fun saveAlimentosNoEncontrados(
        id_cestaCompra: Int,
        productosNoEncontrados: List<Productos.Producto>
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())

        //Inserto los alimentos no encontrados en la cache para esta cesta de la compra.
        for(producto in productosNoEncontrados){
            recetaCache.insertProducto(
                id_cestaCompra = id_cestaCompra,
                //Seteo el campo de no encontrado a True.
                producto = producto.copy(noEncontrado = true)
            )
        }

        emit(DataState.data(data = Unit, message = null))
    }.asCommonFlow()
}
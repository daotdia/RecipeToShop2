package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCacheImpl
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateProducto (
    private val  recetaCache: RecetaCache
){
    fun updateProducto(
        active: Boolean,
        producto: Productos.Producto
    ): CommonFLow<DataState<Unit>> = flow{
        emit(DataState.loading())

        val updated_producto = producto.copy(active = active)
        recetaCache.insertProducto(id_cestaCompra = producto.id_cestaCompra, producto = updated_producto)

        emit(DataState.data(data = Unit))
    }.asCommonFlow()
}
package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
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

        println("Producto actualizado: " + updated_producto.id_producto)
        recetaCache.insertProducto(id_cestaCompra = producto.id_cestaCompra, producto = updated_producto)

        emit(DataState.data(data = Unit))
    }.asCommonFlow()
}
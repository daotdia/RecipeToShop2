package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObtenerProductos(
    private val recetaCache: RecetaCache
) {
    fun obtenerProductos(): Flow<DataState<Productos>> = flow {
        emit(DataState.loading())

        //Obtengo los productos de cache
        val productos_cache = recetaCache.getProductosEncontrados()
        //Emito los productos de cache obtenidos.
        emit(DataState.data(data = productos_cache, message = null))
    }
}
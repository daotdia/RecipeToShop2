package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteProductos(
    private val recetaCache: RecetaCache
) {
    fun deleteProductos(): Flow<DataState<Boolean>> = flow{
        emit(DataState.loading())

        //Elimino los productos
        val exito = recetaCache.deleteProductos()

        //Devuelvo si ha habido éxito al eliminar los productos útlimos de cache.
        emit(DataState.data(data = exito, message = null))
    }
}
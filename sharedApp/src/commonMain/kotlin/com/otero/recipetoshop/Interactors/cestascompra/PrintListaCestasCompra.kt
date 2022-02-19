package com.otero.recipetoshop.Interactors.cestascompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PrintListaCestasCompra(
    private val recetaCache: RecetaCache
) {
    fun printListaCestasCompra(): Flow<DataState<List<CestaCompra>>> = flow {
        emit(DataState.loading())

        val listasCestasCompra = recetaCache.getAllCestasCompra()
        if(listasCestasCompra != null){
            emit(DataState.data(message = null, data = listasCestasCompra))
        }
    }
}
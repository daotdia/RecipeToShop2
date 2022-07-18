package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow

class DeleteCestaCompra (
    private val recetaCache: RecetaCache
){
    fun deleteCestaCompra(
        id_cestaCompra: Int
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())

        recetaCache.removeCestaCompraById(id_cestaCompra)

        emit(DataState.data(message = null, data = Unit))
    }.asCommonFlow()
}
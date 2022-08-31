package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
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
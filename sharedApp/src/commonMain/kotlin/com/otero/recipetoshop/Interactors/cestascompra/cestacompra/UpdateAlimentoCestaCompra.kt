package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class UpdateAlimentoCestaCompra  (
    private val recetaCache: RecetaCache
) {
    fun updateAlimentoCestaCompra(
        alimento: Alimento,
        active: Boolean,
        cantidad: Int? = null
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())

        if(cantidad != null){
            val nuevo_alimento = alimento.copy(cantidad = cantidad)
            recetaCache.insertAlimentoToCestaCompra(nuevo_alimento)
            emit(DataState.data(message = null, data = Unit))
        } else {
            val nuevo_alimento = alimento.copy(active = active)
            recetaCache.insertAlimentoToCestaCompra(nuevo_alimento)
            emit(DataState.data(message = null, data = Unit))
        }
    }.asCommonFlow()
}
package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class DeleteAlimentoCestaCompra (
    private val recetaCache: RecetaCache
    ){
    fun deleteAlimentoCestaCompra(
        alimento: Alimento
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())
        println("Alimento id: " + alimento.id_alimento)
        if(alimento.id_alimento != null){
            recetaCache.removeAlimentoByIdInCestaCompra(alimento.id_alimento)
            emit(DataState.data(message = null, data = Unit))
        } else{
            emit(DataState.data(message = null, data = Unit))
        }
    }.asCommonFlow()
}
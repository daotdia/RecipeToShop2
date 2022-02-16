package com.otero.recipetoshop.Interactors.recetas.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAlimentoCestaCompra (
    private val recetaCache: RecetaCache
    ){
    fun deleteAlimentoCestaCompra(
        alimento: Alimento
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.loading())
        println("Alimento id: " + alimento.id_alimento)
        if(alimento.id_alimento != null){
            recetaCache.removeAlimentoByIdInCestaCompra(alimento.id_alimento)
            emit(DataState.data(message = null, data = Unit))
        } else{
            emit(DataState.data(message = null, data = Unit))
        }
    }
}
package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteRecetaCestaCompra (
    private val recetaCache: RecetaCache
){
    fun deleteRecetaCestaCompra(
        receta: Receta
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.loading())
        println("Alimento id: " + receta.id_Receta)
        if(receta.id_Receta != null){
            recetaCache.removeRecetaByIdInCestaCompra(receta.id_Receta)
            emit(DataState.data(message = null, data = Unit))
        } else{
            emit(DataState.data(message = null, data = Unit))
        }
    }
}
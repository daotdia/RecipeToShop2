package com.otero.recipetoshop.Interactors.recetas.listarecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAlimentoListaRecetas (
    private val recetaCache: RecetaCache
    ){
    fun deleteAlimentoListaRecetas(
        alimento: Food
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.loading())
        println("Alimento id: " + alimento.id_food)
        if(alimento.id_food != null){
            recetaCache.removeAlimentoByIdInListaRecetas(alimento.id_food)
            emit(DataState.data(message = null, data = Unit))
        } else{
            emit(DataState.data(message = null, data = Unit))
        }
    }
}
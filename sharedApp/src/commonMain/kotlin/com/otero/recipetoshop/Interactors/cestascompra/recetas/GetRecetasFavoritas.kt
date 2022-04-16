package com.otero.recipetoshop.Interactors.cestascompra.recetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecetasFavoritas(
    private  val recetaCache: RecetaCache
){
    fun getRecetasFavoritas(): Flow<DataState<List<Receta>>> = flow {
        emit(DataState.loading())

        val recetasFavoritas = recetaCache.getAllRecetasFavoritas()

        emit(DataState.data(data = recetasFavoritas, message = null))
    }
}
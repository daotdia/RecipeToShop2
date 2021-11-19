package com.otero.recipetoshop.Interactors.recetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnEnterListaDeRecetas (
    private val recetaCache: RecetaCache
    ){
    fun onEnterReceta(
        id_listaReceta: Int
    ): Flow<DataState<List<Receta>>> = flow {
        emit(DataState.loading())

        val itemsListaRecetas = recetaCache.getRecetasByListaRecetasInListaRecetas(id_listaReceta = id_listaReceta)
        if(itemsListaRecetas != null){
            emit(DataState.data(message = null, data = itemsListaRecetas))
        }
    }
}
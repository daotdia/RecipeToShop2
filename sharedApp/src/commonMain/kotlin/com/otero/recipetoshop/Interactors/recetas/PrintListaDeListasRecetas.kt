package com.otero.recipetoshop.Interactors.recetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PrintListaDeListasRecetas(
    private val recetaCache: RecetaCache
) {
    fun printListaDeListasrecetas(): Flow<DataState<List<ListaRecetas>>> = flow {
        emit(DataState.loading())

        val listasDeListasRecetas = recetaCache.getAllListaRecetas()
        if(listasDeListasRecetas != null){
            emit(DataState.data(message = null, data = listasDeListasRecetas))
        }
    }
}
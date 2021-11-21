package com.otero.recipetoshop.Interactors.recetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnEnterListaDeRecetas (
    private val recetaCache: RecetaCache
    ) {
    fun getRecetasListaRecetas(
        id_listaReceta: Int
    ): Flow<DataState<List<Receta>>> = flow {
        emit(DataState.loading())

        val recetasListaRecetas =
            recetaCache.getRecetasByListaRecetasInListaRecetas(id_listaReceta = id_listaReceta)
        if (recetasListaRecetas != null) {
            emit(
                DataState.data(
                    message = null,
                    data = recetasListaRecetas
                )
            )
        }
    }

    fun getAlimentosListaRecetas(
        id_listaReceta: Int
    ): Flow<DataState<List<Food>>> = flow {
        emit(DataState.loading())

        val alimentosListaRecetas = recetaCache.getAlimentosByListaRecetas(id_listaReceta = id_listaReceta)
        if(alimentosListaRecetas != null){
            emit(DataState.data(
                message = null,
                data = alimentosListaRecetas
            ))
        }
    }
}
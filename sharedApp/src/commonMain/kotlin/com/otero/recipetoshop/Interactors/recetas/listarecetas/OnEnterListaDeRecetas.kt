package com.otero.recipetoshop.Interactors.recetas.listarecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnEnterListaDeRecetas (
    private val recetaCache: RecetaCache
    ) {
    fun getRecetasListaRecetas(
        id_listaReceta: Int
    ): Flow<DataState<Pair<List<Receta>?, List<Receta>?>>> = flow {
        emit(DataState.loading())

        val recetasActiveListaRecetas: List<Receta>? =
            recetaCache.getRecetasByActiveInListaRecetas(active = true, id_listaReceta = id_listaReceta)
        val recetasInactiveListaRecetas =
            recetaCache.getRecetasByActiveInListaRecetas(active = false, id_listaReceta = id_listaReceta)

        emit(DataState.data(
            message = null,
            data = Pair(recetasActiveListaRecetas, recetasInactiveListaRecetas),
        ))
    }

    fun getAlimentosListaRecetas(
        id_listaReceta: Int
    ): Flow<DataState<Pair<List<Food>?, List<Food>?>>> = flow {
        emit(DataState.loading())

        val alimentosActivosListaRecetas : List<Food>? =
            recetaCache.getAlimentosByActiveInListaRecetas(active = true, id_listaReceta = id_listaReceta)
        val alimentosInActivosListaRecetas : List<Food>? =
            recetaCache.getAlimentosByActiveInListaRecetas(active = false, id_listaReceta = id_listaReceta)

        emit(DataState.data(
            message = null,
            data = Pair(alimentosActivosListaRecetas, alimentosInActivosListaRecetas),
        ))
    }
}
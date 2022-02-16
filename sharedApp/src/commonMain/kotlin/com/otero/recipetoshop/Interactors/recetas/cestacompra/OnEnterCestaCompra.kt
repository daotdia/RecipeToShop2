package com.otero.recipetoshop.Interactors.recetas.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnEnterCestaCompra (
    private val recetaCache: RecetaCache
    ) {
    fun getRecetasCestaCompra(
        id_listaReceta: Int
    ): Flow<DataState<Pair<List<Receta>?, List<Receta>?>>> = flow {
        emit(DataState.loading())

        val recetasActivasCestaCompra: List<Receta>? =
            recetaCache.getRecetasByActiveInCestaCompra(active = true, id_cestaCompra = id_listaReceta)
        val recetasInactivasCestaCompra =
            recetaCache.getRecetasByActiveInCestaCompra(active = false, id_cestaCompra = id_listaReceta)

        emit(DataState.data(
            message = null,
            data = Pair(recetasActivasCestaCompra, recetasInactivasCestaCompra),
        ))
    }

    fun getAlimentosCestaCompra(
        id_listaReceta: Int
    ): Flow<DataState<Pair<List<Alimento>?, List<Alimento>?>>> = flow {
        emit(DataState.loading())

        val alimentosActivosCestaCompra : List<Alimento>? =
            recetaCache.getAlimentosByActiveInCestaCompra(active = true, id_cestaCompra = id_listaReceta)
        val alimentosInActivosCestaCompra : List<Alimento>? =
            recetaCache.getAlimentosByActiveInCestaCompra(active = false, id_cestaCompra = id_listaReceta)

        emit(DataState.data(
            message = null,
            data = Pair(alimentosActivosCestaCompra, alimentosInActivosCestaCompra),
        ))
    }
}
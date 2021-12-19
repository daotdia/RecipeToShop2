package com.otero.recipetoshop.Interactors.recetas.listarecetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateAlimentoListaRecetas  (
    private val recetaCache: RecetaCache
) {
    fun updateAlimentoListaRecetas(
        alimento: Food,
        active: Boolean,
        cantidad: Int? = null
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.loading())

        if(cantidad != null){
            val nuevo_alimento = alimento.copy(cantidad = cantidad)
            recetaCache.insertAlimentoToListaRecetas(nuevo_alimento)
            emit(DataState.data(message = null, data = Unit))
        } else {
            val nuevo_alimento = alimento.copy(active = active)
            recetaCache.insertAlimentoToListaRecetas(nuevo_alimento)
            emit(DataState.data(message = null, data = Unit))
        }
    }
}
package com.otero.recipetoshop.Interactors.cestascompra.recetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteIngredienteReceta(
    private val recetaCache: RecetaCache
) {
    fun deleteIngredientereceta(
        id_ingrediente: Int
    ): CommonFLow<DataState<Unit>> = flow{
        emit(DataState.loading())

        recetaCache.removeIngredienteByIdInReceta(id_ingrediente)

        emit(DataState.data(data = Unit, message = null))
    }.asCommonFlow()
}
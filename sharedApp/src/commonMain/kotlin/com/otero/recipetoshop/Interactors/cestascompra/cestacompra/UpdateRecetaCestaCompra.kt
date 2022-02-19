package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateRecetaCestaCompra (
    private val recetaCache: RecetaCache
) {
    fun updateRecetaCestaCompra(
        receta: Receta,
        active: Boolean,
        cantidad: Int? = null
    ): Flow<DataState<Unit>> = flow  {
        emit(DataState.loading())

        if(cantidad != null){
            val nueva_receta = receta.copy(cantidad = cantidad)
            recetaCache.insertRecetaToCestaCompra(nueva_receta)
            emit(DataState.data(message = null, data = Unit))
        } else {
            val nueva_receta = receta.copy(active = active)
            recetaCache.insertRecetaToCestaCompra(nueva_receta)
            emit(DataState.data(message = null, data = Unit))
        }
    }
}
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
    ): Flow<DataState<Int>> = flow  {
        emit(DataState.loading())

        val recetasFavoritas = recetaCache.getAllRecetasFavoritas()
        for (recetaFavorita in recetasFavoritas){
            if(recetaFavorita.nombre.equals(receta.nombre) && recetaFavorita.id_Receta != receta.id_Receta){
                recetaCache.insertRecetaToCestaCompra(recetaFavorita.copy(isFavorita = receta.isFavorita))
            }
        }

        if(cantidad != null){
            val nueva_receta = receta.copy(cantidad = cantidad)
            val id = recetaCache.insertRecetaToCestaCompra(nueva_receta)
            emit(DataState.data(message = null, data = id))
        } else {
            val nueva_receta = receta.copy(active = active)
            val id = recetaCache.insertRecetaToCestaCompra(nueva_receta)
            emit(DataState.data(message = null, data = id))
        }
    }
}
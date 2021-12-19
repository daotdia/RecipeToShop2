package com.otero.recipetoshop.Interactors.recetas.listarecetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddRecetaListaRecetas (
    private val recetaCache: RecetaCache
) {
    fun addRecetaListaRecetas(
        receta: Receta
    ): Flow<DataState<Boolean>> = flow {
        emit(DataState.loading())

        var igual: Boolean = false
        val recetasListaRecetas = recetaCache.getRecetasByListaRecetasInListaRecetas(id_listaReceta = receta.id_listaRecetas)
        if (recetasListaRecetas != null) {
            recetasListaRecetas.forEach { item ->
                if(item.nombre.equals(receta.nombre)){
                    igual = true
                }
            }
            if(!igual){
                recetaCache.insertRecetaToListaRecetas(receta)
            }
            emit(
                DataState.data(
                    message = null,
                    data = true
                )
            )
        }
    }
}
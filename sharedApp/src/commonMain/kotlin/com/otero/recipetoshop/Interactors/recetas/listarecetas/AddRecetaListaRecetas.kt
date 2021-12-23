package com.otero.recipetoshop.Interactors.recetas.listarecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddRecetaListaRecetas (
    private val recetaCache: RecetaCache
) {
    fun addRecetaListaRecetas(
        receta: Receta
    ): Flow<DataState<Int>> = flow {
        emit(DataState.loading())

        var exito: Int = -1
        var igual: Boolean = false
        val recetasListaRecetas = recetaCache.getRecetasByListaRecetasInListaRecetas(id_listaReceta = receta.id_listaRecetas)
        if (recetasListaRecetas != null) {
            recetasListaRecetas.forEach { item ->
                if(item.nombre.equals(receta.nombre)){
                    igual = true
                }
            }
            if(!igual){
                exito = recetaCache.insertRecetaToListaRecetas(receta)
            }
            if(exito != -1){
                emit(
                    DataState.data(
                        message = null,
                        data = exito
                    )
                )
            }
        }
    }

    fun addIngredientesReceta(
        receta: Receta,
        id_receta: Int
    ): Flow<DataState<Boolean>> = flow {
        emit(DataState.loading())
        if(receta.id_Receta != null){
            if (receta.ingredientes.isNotEmpty()){
                val ingredientes = receta.ingredientes
                for (ingrediente in ingredientes){
                    recetaCache.insertIngredienteToReceta(ingrediente.copy(id_listaRecetas = receta.id_listaRecetas, id_receta = receta.id_Receta))
                }
            }
        } else{
            if (receta.ingredientes.isNotEmpty()){
                val ingredientes = receta.ingredientes
                for (ingrediente in ingredientes){
                    recetaCache.insertIngredienteToReceta(ingrediente.copy(id_listaRecetas = receta.id_listaRecetas, id_receta = id_receta))
                }
            }
        }
    }
}
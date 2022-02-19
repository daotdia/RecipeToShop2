package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddRecetaCestaCompra (
    private val recetaCache: RecetaCache
) {
    fun addRecetaCestaCompra(
        receta: Receta
    ): Flow<DataState<Int>> = flow {
        emit(DataState.loading())

        var exito: Int = -1
        var igual: Boolean = false
        val recetasCestaCompra = recetaCache.getRecetasByCestaCompra(id_cestaCompra = receta.id_cestaCompra)
        if (recetasCestaCompra != null) {
            recetasCestaCompra.forEach { item ->
                if(item.nombre.equals(receta.nombre)){
                    igual = true
                }
            }
            if(!igual){
                exito = recetaCache.insertRecetaToCestaCompra(receta)
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
                    recetaCache.insertIngredienteToReceta(ingrediente.copy(id_cestaCompra = receta.id_cestaCompra, id_receta = receta.id_Receta))
                }
            }
        } else{
            if (receta.ingredientes.isNotEmpty()){
                val ingredientes = receta.ingredientes
                for (ingrediente in ingredientes){
                    recetaCache.insertIngredienteToReceta(ingrediente.copy(id_cestaCompra = receta.id_cestaCompra, id_receta = id_receta))
                }
            }
        }
    }
}
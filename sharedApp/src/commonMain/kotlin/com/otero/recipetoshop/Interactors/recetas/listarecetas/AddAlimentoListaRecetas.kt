package com.otero.recipetoshop.Interactors.recetas.listarecetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddAlimentoListaRecetas (
    private val recetaCache: RecetaCache
    ) {
    fun insertAlimentosListaRecetas(
        alimento: Food
    ): Flow<DataState<Boolean>> = flow {
        emit(DataState.loading())

        var igual: Boolean = false
        val alimentosListaRecetas = recetaCache.getAlimentosByListaRecetas(id_listaReceta = alimento.id_listaRecetas!!)
        if (alimentosListaRecetas != null) {
            alimentosListaRecetas.forEach { item ->
                if(item.nombre.equals(alimento.nombre)){
                    igual = true
                }
            }
            if(!igual){
                recetaCache.insertAlimentoToListaRecetas(alimento)
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
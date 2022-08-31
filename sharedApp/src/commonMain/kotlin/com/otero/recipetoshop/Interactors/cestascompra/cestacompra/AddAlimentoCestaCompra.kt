package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class AddAlimentoCestaCompra (
    private val recetaCache: RecetaCache
    ) {
    fun insertAlimentosCestaCompra(
        alimento: Alimento
    ): CommonFLow<DataState<Boolean>> = flow {
        emit(DataState.loading())

        var igual: Boolean = false
        val alimentosCestaCompra = recetaCache.getAlimentosByCestaCompra(id_cestaCompra = alimento.id_cestaCompra!!)
        if (alimentosCestaCompra != null) {
            alimentosCestaCompra.forEach { item ->
                if(item.nombre.equals(alimento.nombre)){
                    igual = true
                }
            }
            if(!igual){
                recetaCache.insertAlimentoToCestaCompra(alimento)
            }
            emit(
                DataState.data(
                    message = null,
                    data = true
                )
            )
        }
    }.asCommonFlow()
}
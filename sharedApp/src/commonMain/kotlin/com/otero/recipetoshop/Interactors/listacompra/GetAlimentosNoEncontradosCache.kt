package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAlimentosNoEncontradosCache (
    private val recetaCache: RecetaCache
    ){
    fun getAlimentosNoEncontradosCache(): CommonFLow<DataState<List<Productos.Producto>>> = flow {
        emit(DataState.loading())

        //Obtengo los productos con campo enocntrado a true.
        val alimentosNoEnocntrados = recetaCache.getProductosNoEncontrados()

        emit(DataState.data(data = alimentosNoEnocntrados, message = null))
    }.asCommonFlow()
}
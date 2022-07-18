package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow

class SearchRecetasCache (
    private val recetaCache: RecetaCache
){
    fun searchRecetasCache(
        query: String
    ): CommonFLow<DataState<List<Receta>>> = flow {
        emit(DataState.loading())

        var result: List<Receta> = listOf()

        //Obtengo todas las recetas del cache.
        val allRecetas = recetaCache.getAllRecetasInCestasCompra()

        //Filtro las recetas cuyo nombre contenga la cadena query.
        if (allRecetas != null) {
                result = allRecetas.filter { receta ->
                receta.nombre.contains(query)
            }
        }

        emit(DataState.data(message = null, data = result))

    }.asCommonFlow()
}
package com.otero.recipetoshop.Interactors.cestascompra.recetas.busquedarecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.network.RecetasServicio
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class BuscarRecetasAPI (
    private val recetaCache: RecetaCache,
    private val recetasServicio: RecetasServicio
    ) {
    fun buscarRecetasAPI(
        query: String,
        maxItems: Int,
        offset: Int,
        maxSeconds: Int
    ): CommonFLow<DataState<List<Receta>>> = flow{
        emit(DataState.loading())

        println("Llego al interactor")

        try{
            val respuestaBusqueda = recetasServicio.search(
                query = query,
                offset = offset,
                maxItems = maxItems,
                maxSeconds = maxSeconds
            )

            emit(DataState.data(message = null, data = respuestaBusqueda))
        } catch (e: Exception){
            println("Error a la hora de conectar con API: " + e.message)
        }

    }.asCommonFlow()
}
package com.otero.recipetoshop.Interactors.cestascompra.recetas.busquedarecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.network.RecetasServicio
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
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
    ): Flow<DataState<List<Receta>>> = flow{
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

    }
}
package com.otero.recipetoshop.Interactors.recetas.listaldelistasrecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetListaRecetas(
    private val recetaCache: RecetaCache
) {
    fun getListaRecetas(
       nombre: String
    ): Flow<DataState<ListaRecetas>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        val listaRecetas = recetaCache.getAllListaRecetas()
        var listaReceta: ListaRecetas? = null
        if (listaRecetas != null) {
            for (item in listaRecetas) {
                if(item.nombre.equals(nombre)){
                    listaReceta = item
                }
            }
        }
        if(listaReceta != null){
            emit(DataState.data(message = null, data = listaReceta))
        } else {
            println("Lista no encontrada")
        }
    }
}
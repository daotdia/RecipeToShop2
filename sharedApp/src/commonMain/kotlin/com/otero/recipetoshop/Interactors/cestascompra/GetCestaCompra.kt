package com.otero.recipetoshop.Interactors.cestascompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class GetCestaCompra(
    private val recetaCache: RecetaCache
) {
    fun getCestaCompra(
       nombre: String
    ): CommonFLow<DataState<CestaCompra>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        val cestaCompra = recetaCache.getAllCestasCompra()
        var listaReceta: CestaCompra? = null
        if (cestaCompra != null) {
            for (item in cestaCompra) {
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
    }.asCommonFlow()
}
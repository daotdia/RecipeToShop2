package com.otero.recipetoshop.Interactors.cestascompra.recetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class GetDatosReceta(
    private  val recetaCache: RecetaCache
){
    fun getDatosReceta(
        id_receta: Int
    ) : CommonFLow<DataState<Receta>> = flow {
        emit(DataState.loading())

        val receta = recetaCache.getRecetaByIdInCestaCompra(id_receta = id_receta)

        var ingredientes = recetaCache.getIngredientesByReceta(id_receta = id_receta)
        if(ingredientes == null){
            ingredientes = listOf()
        }

        emit(DataState.data(data = receta!!.copy(ingredientes = ingredientes), message = null))
    }.asCommonFlow()
}
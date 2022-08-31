package com.otero.recipetoshop.Interactors.cestascompra

import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.UpdateAlimentoCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.UpdateRecetaCestaCompra
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow

class DeleteCestaCompra (
    private val recetaCache: RecetaCache
    ){
    fun deleteCestaCompra(
        id_cestaCompra: Int
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())

        //Obtengo las recetas de la cesta
        val recetas = recetaCache.getRecetasByCestaCompra(id_cestaCompra = id_cestaCompra)
        //Elimino los ingredientes de las recetas
        if (recetas != null) {
            for(receta in recetas){
                recetaCache.removeIngredientesByRecetaInReceta(receta.id_Receta!!)
            }
        }

        //Elimino las recetas de la lista de la compra
        recetaCache.removeRecetasByCestaCompra(id_cestaCompra = id_cestaCompra)

        //Elimino los alimentos de las recetas
        recetaCache.removeAlimentosByCestaCompra(id_cestaCompra = id_cestaCompra)

        //Elimino la cesta de la compra.
        recetaCache.removeCestaCompraById(id_cestaCompra)

        emit(DataState.data(message = null, data = Unit))
    }.asCommonFlow()
}
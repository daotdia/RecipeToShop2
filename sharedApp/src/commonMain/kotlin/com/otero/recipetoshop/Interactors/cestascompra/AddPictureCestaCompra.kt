package com.otero.recipetoshop.Interactors.cestascompra

import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.UpdateAlimentoCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.UpdateRecetaCestaCompra
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class AddPictureCestaCompra(
    private val recetaCache: RecetaCache
) {
    fun addPictureCestaCompra(
        id_cestaCompra: Int,
        picture: String
    ): CommonFLow<DataState<Int>> = flow {
        emit(DataState.loading())

        //Inserto la cesta de la compra con la imagen.
        val old_cestaCompra = recetaCache.getCestaCompraById(id_cestaCompra)
        val new_id = recetaCache.insertCestaCompra(old_cestaCompra!!.copy(id_cestaCompra = null, imagen = picture))

        //Obtengo las recetas de la lista de la compra y las actualizo
        val recetas = recetaCache.getRecetasByCestaCompra(id_cestaCompra)
        if (recetas != null) {
            for(receta in recetas){
                UpdateRecetaCestaCompra(recetaCache = recetaCache).updateRecetaCestaCompra(
                    receta = receta.copy(id_cestaCompra = new_id),
                    active = receta.active,
                    cantidad = receta.cantidad
                )
            }
        }

        //Obtengo los alimentos y los actualizo
        val alimentos = recetaCache.getAlimentosByCestaCompra(id_cestaCompra)
        if (alimentos != null){
            for (alimento in alimentos){
                UpdateAlimentoCestaCompra(recetaCache = recetaCache).updateAlimentoCestaCompra(
                    alimento = alimento.copy(id_cestaCompra = new_id),
                    active = alimento.active,
                    cantidad = alimento.cantidad
                )
            }
        }

        recetaCache.removeCestaCompraById(id_cestaCompra)

        emit(DataState.data(message = null, data = new_id))

    }.asCommonFlow()
}
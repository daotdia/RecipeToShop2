package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class AddPicture(
    private val recetaCache: RecetaCache
){
    fun addPicture (
        picture: String?,
        id_cestaCompra: Int
    ): CommonFLow<DataState<Int>> = flow {
        emit(DataState.loading())

        if(picture != null){
            //Obtengo la cesta de recetas antigua y la elimino.
            val old_cestaCompra = recetaCache.getCestaCompraById(id_cestaCompra)
            recetaCache.removeCestaCompraById(id_cestaCompra)

            //Creo una nueva cesta de recetas con la imagen actualizada.
            val new_cestaCompra = old_cestaCompra!!.copy(
                imagen = picture,
                id_cestaCompra = null,
            )
            val new_id = recetaCache.insertCestaCompra(new_cestaCompra)

            //Añado los alimentos de la cesta de la compra antigua a la nueva.
            val old_alimentos = recetaCache.getAlimentosByCestaCompra(id_cestaCompra)
            if(old_alimentos != null){
                for(alimento in old_alimentos){
                    val new_alimento = alimento.copy(id_cestaCompra = new_id)
                    recetaCache.removeAlimentoByIdInCestaCompra(alimento.id_alimento!!)
                    recetaCache.insertAlimentoToCestaCompra(new_alimento)
                }
            }

            //Añado las recetas y los ingredientes de cada receta de la cesta de recetas antigua a la nueva.
            val old_recetas = recetaCache.getRecetasByCestaCompra(id_cestaCompra)
            if(old_recetas != null){
                for(receta in old_recetas){
                    //Obtengo sus ingredientes antiguos
                    val old_ingredientes = recetaCache.getIngredientesByReceta(receta.id_Receta!!)
                    //Inserto la nueva receta
                    val new_id_receta = recetaCache.insertRecetaToCestaCompra(receta.copy(id_Receta = null))
                    //Elimino la receta antigua
                    recetaCache.removeRecetaByIdInCestaCompra(receta.id_Receta)
                    //Añado los ingredientes a la nueva receta
                    if(old_ingredientes != null){
                        for(old_ingrediente in old_ingredientes){
                            val new_ingrediente = old_ingrediente.copy(id_receta = new_id_receta)
                            //Elimino el viejo ingrediente
                            recetaCache.removeIngredienteByIdInReceta(old_ingrediente.id_alimento!!)
                            //Inserto el nuevo ingrediente
                            recetaCache.insertIngredienteToReceta(new_ingrediente)
                        }
                    }
                }
            }

            emit(DataState.data(data = new_id))
        } else {
            println("Ha habido un problemas carhando la imagen seleccionada")
            emit(DataState.data(data = id_cestaCompra))
        }
    }.asCommonFlow()
}
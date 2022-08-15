package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateRecetaCestaCompra (
    private val recetaCache: RecetaCache
) {
    fun updateRecetaCestaCompra(
        receta: Receta,
        active: Boolean,
        cantidad: Int? = null
    ): CommonFLow<DataState<Int>> = flow  {
        emit(DataState.loading())

        val recetasFavoritas = recetaCache.getAllRecetasFavoritas()
        for (recetaFavorita in recetasFavoritas){
            if(recetaFavorita.nombre.equals(receta.nombre) && recetaFavorita.id_Receta != receta.id_Receta){
                recetaCache.insertRecetaToCestaCompra(recetaFavorita.copy(isFavorita = receta.isFavorita))
            }
        }

        println("La cantidad a actualizar es: " + cantidad)

        if(cantidad != null){

            println("La cantidad a actualizar es: " + cantidad)
            val nueva_receta = receta.copy(
                id_cestaCompra = receta.id_cestaCompra,
                id_Receta = receta.id_Receta,
                nombre = receta.nombre,
                cantidad = cantidad,
                user = receta.user,
                active = receta.active,
                imagenSource = receta.imagenSource,
                ingredientes = receta.ingredientes,
                rating = receta.rating,
                isFavorita = receta.isFavorita
            )

            val ingredientes_antiguos = recetaCache.getIngredientesByReceta(id_receta = nueva_receta.id_Receta!!)

            //Obtengo el nuevo id de la receta insertada.
            val id = recetaCache.insertRecetaToCestaCompra(nueva_receta)

            //Elimino los ingredientes antiguos.
            recetaCache.removeIngredientesByRecetaInReceta(id_receta = nueva_receta.id_Receta)
            //Por cada ingrediente le modifico su id de receta a la nueva receta si hay.
            if(!ingredientes_antiguos.isNullOrEmpty()){
                ingredientes_antiguos.forEach {
                    it.id_receta = id
                }
                recetaCache.insertIngredientesToReceta(ingredientes_antiguos)
            }

            recetaCache.removeRecetaByIdInCestaCompra(nueva_receta.id_Receta)

            emit(DataState.data(message = null, data = id))
        } else {
            //Primero modifico actve en recera y la inserto en caché.
            val nueva_receta = receta.copy(active = active)
            val id = recetaCache.insertRecetaToCestaCompra(nueva_receta)

            val ingredientes_antiguos = recetaCache.getIngredientesByReceta(id_receta = nueva_receta.id_Receta!!)

            //Elimino los ingredientes antiguos.
            recetaCache.removeIngredientesByRecetaInReceta(id_receta = nueva_receta.id_Receta)
            //Por cada ingrediente le modifico su id de receta a la nueva receta si hay.
            if(!ingredientes_antiguos.isNullOrEmpty()){
                ingredientes_antiguos.forEach {
                    it.id_receta = id
                }
                recetaCache.insertIngredientesToReceta(ingredientes_antiguos)
            }

            recetaCache.removeRecetaByIdInCestaCompra(nueva_receta.id_Receta)

            //Devuelvo el id de la receta modificada.
            emit(DataState.data(message = null, data = id))
        }
    }.asCommonFlow()
}
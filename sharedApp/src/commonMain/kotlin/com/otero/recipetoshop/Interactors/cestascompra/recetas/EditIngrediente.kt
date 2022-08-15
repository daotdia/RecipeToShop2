package com.otero.recipetoshop.Interactors.cestascompra.recetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.domain.util.asCommonFlow
import com.otero.recipetoshop.events.cestacompra.receta.RecetaEventos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditIngrediente(
    private  val recetaCache: RecetaCache
) {
    fun editIngrediente(
        id_receta: Int,
        id_cestaCompra: Int,
        id_alimento: Int,
        nombre: String,
        cantidad: Int,
        tipoUnidad: TipoUnidad
    ): CommonFLow<DataState<Unit>> = flow {
        emit(DataState.loading())

        //Creo el nuevo ingrediente.
        val new_ingrediente = Alimento(
            id_receta = id_receta,
            id_cestaCompra = id_cestaCompra,
            id_alimento = id_alimento,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad,
            active = true
        )

        //Elimno el ingrediente anteiuor.
        recetaCache.removeIngredienteByIdInReceta(id_ingrediente = id_alimento)

        //Inserto e ingrediente en la reveta edityado.
        recetaCache.insertIngredienteToReceta(ingrediente = new_ingrediente)

        emit(DataState(data = Unit))
    }.asCommonFlow()
}
package com.otero.recipetoshop.Interactors.cestascompra.recetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.TipoUnidad
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddIngredienteReceta(
    private val recetaCache: RecetaCache,
    ) {
    fun addIngredienteReceta(
        id_cestaCompra: Int,
        id_receta: Int,
        nombre: String,
        cantidad: Int,
        tipoUnidad: TipoUnidad
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.loading())

        val ingrediente = Alimento(
            id_cestaCompra = id_cestaCompra,
            id_receta = id_receta,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad,
            active = true
        )

        emit(DataState.data(data = Unit, message = null))

        recetaCache.insertIngredienteToReceta(ingrediente)
    }
}
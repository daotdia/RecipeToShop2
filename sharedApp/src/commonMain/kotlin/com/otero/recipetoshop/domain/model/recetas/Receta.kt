package com.otero.recipetoshop.domain.model.recetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad

data class Receta(
    val nombre: String,
    val ingredientes: List<Food>,
    val cantidad: Int,
    val tipo: TipoUnidad?
)

fun Receta.toFood(): Food{
    return Food(
        nombre = nombre,
        cantidad = cantidad,
        tipoUnidad = tipo!!
    )
}
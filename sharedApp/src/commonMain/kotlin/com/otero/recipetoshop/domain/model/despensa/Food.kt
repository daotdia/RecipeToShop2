package com.otero.recipetoshop.domain.model.despensa

import com.otero.recipetoshop.domain.util.TipoUnidad

data class Food(
    val id_listaRecetas: Int? = null,
    val id_receta: Int? = null,
    val id_food: Int? = null,
    val nombre: String,
    var cantidad: Int = 0,
    val tipoUnidad: TipoUnidad,
    var active: Boolean
)
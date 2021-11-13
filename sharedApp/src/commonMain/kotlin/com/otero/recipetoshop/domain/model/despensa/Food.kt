package com.otero.recipetoshop.domain.model.despensa

import com.otero.recipetoshop.domain.util.TipoUnidad

data class Food(
    val nombre: String,
    var cantidad: Int,
    val tipoUnidad: TipoUnidad
)
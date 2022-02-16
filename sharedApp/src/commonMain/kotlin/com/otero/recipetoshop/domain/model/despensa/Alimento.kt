package com.otero.recipetoshop.domain.model.despensa

import com.otero.recipetoshop.domain.util.TipoUnidad

data class Alimento(
    val id_cestaCompra: Int? = null,
    val id_receta: Int? = null,
    val id_alimento: Int? = null,
    val nombre: String,
    var cantidad: Int = 0,
    val tipoUnidad: TipoUnidad,
    var active: Boolean
)
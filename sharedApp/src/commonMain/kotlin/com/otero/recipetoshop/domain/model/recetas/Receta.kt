package com.otero.recipetoshop.domain.model.recetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad

data class Receta(
    val id_listaRecetas: Int,
    val id_Receta: Int? = null,
    val nombre: String,
    val cantidad: Int,
    var user: Boolean,
    var active: Boolean,
)
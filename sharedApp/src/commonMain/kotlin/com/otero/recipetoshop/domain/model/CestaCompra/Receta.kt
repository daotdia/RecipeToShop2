package com.otero.recipetoshop.domain.model.CestaCompra

import com.otero.recipetoshop.domain.model.despensa.Alimento

data class Receta(
    val id_cestaCompra: Int,
    val id_Receta: Int? = null,
    val nombre: String,
    val cantidad: Int,
    var user: Boolean,
    var active: Boolean,
    val imagenSource: String? = "",
    val ingredientes: List<Alimento> = emptyList(),
    val rating: Float? = null,
    val isFavorita: Boolean = false
)
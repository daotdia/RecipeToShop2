package com.otero.recipetoshop.domain.model.recetas

import com.otero.recipetoshop.domain.model.despensa.Food

data class Receta(
    val nombre: String,
    val ingredientes: List<Food>,
    val cantidad: Int
)
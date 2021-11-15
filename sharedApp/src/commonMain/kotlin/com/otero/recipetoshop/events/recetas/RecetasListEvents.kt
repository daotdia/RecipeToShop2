package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.despensa.Food

sealed class RecetasListEvents {
    //data class onCantidadChange(val cantidad: String, val food: Food) : FoodListEvents()
    data class onNombreListaChange(val nombre: String): RecetasListEvents()
}
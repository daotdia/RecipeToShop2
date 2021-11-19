package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta

sealed class RecetasListEvents {
    //data class onCantidadChange(val cantidad: String, val food: Food) : FoodListEvents()
    data class onNombreListaChange(val nombre: String): RecetasListEvents()

    data class onEnterListaDeLisaDeRecetas(val id_listaRecetas: Int): RecetasListEvents()

    data class onAddreceta(val nombre: String, val cantidad: Int, val tipo: String): RecetasListEvents()

    data class onDeleteRecetas(val menuItem: String): RecetasListEvents()

    data class onDeleteReceta(val receta: Receta): RecetasListEvents()

    data class onCantidadRecetaChange(val cantidad: Int, val receta: Receta): RecetasListEvents()
}
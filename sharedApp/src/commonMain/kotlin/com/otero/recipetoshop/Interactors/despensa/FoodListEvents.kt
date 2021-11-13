package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.Interactors.RecipeList.RecipeListEvents
import com.otero.recipetoshop.domain.model.despensa.Food

sealed class FoodListEvents {

    data class onCantidadChange(val cantidad: String, val food: Food) : FoodListEvents()

    data class onAddFood(val nombre: String, val tipo: String, val cantidad: String): FoodListEvents()

    data class onNombreAdd(val nombre: String): FoodListEvents()

    data class onTipoAdd(val tipo: String): FoodListEvents()

    data class onCantidadAdd(val nombre: String): FoodListEvents()
}
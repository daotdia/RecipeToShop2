package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad

sealed class RecetasListEvents {
    //data class onCantidadChange(val cantidad: String, val food: Food) : FoodListEvents()
    data class onNombreListaChange(val nombre: String): RecetasListEvents()

    data class onEnterListaDeLisaDeRecetas(val id_listaRecetas: Int): RecetasListEvents()

    data class onAddAlimento(val nombre: String, val cantidad: Int, val tipoUnidad: TipoUnidad): RecetasListEvents()

    data class onAddReceta(val nombre: String, val cantidad: Int): RecetasListEvents()

    data class onDeleteRecetas(val menuItem: String): RecetasListEvents()

    data class onDeleteReceta(val receta: Receta): RecetasListEvents()

    data class onDeleteAlimento(val alimento: Food): RecetasListEvents()

    data class onCantidadRecetaChange(val cantidad: Int, val receta: Receta): RecetasListEvents()

    data class onCantidadAlimentoChange(val cantidad: Int, val alimento: Food): RecetasListEvents()

    data class onAddListaReceta(val nombre: String): RecetasListEvents()
}
package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad

sealed class RecetaListEvents {
    data class onAddAlimento(val nombre: String, val cantidad: Int, val tipoUnidad: TipoUnidad): RecetaListEvents()

    data class onAddUserReceta(val nombre: String, val cantidad: Int): RecetaListEvents()

    data class onDeleteRecetas(val menuItem: String): RecetaListEvents()

    data class onDeleteReceta(val receta: Receta): RecetaListEvents()

    data class onDeleteAlimento(val alimento: Food): RecetaListEvents()

    data class onCantidadRecetaChange(val cantidad: Int, val receta: Receta): RecetaListEvents()

    data class onCantidadAlimentoChange(val cantidad: Int, val alimento: Food): RecetaListEvents()

    data class onRecetaClick(val receta: Receta, val active: Boolean): RecetaListEvents()

    data class onAlimentoClick(val alimento: Food, val active: Boolean): RecetaListEvents()
}
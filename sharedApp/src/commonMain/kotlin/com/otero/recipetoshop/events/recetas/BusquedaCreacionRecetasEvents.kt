package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.recetas.Receta

sealed class BusquedaCreacionRecetasEvents {
    data class onAddUserReceta(val nombre: String, val cantidad: Int): BusquedaCreacionRecetasEvents()

    object buscarRecetas: BusquedaCreacionRecetasEvents()

    data class updateQuery(val newQuery: String): BusquedaCreacionRecetasEvents()

    data class onAddYummlyReceta(val receta: Receta): BusquedaCreacionRecetasEvents()
}
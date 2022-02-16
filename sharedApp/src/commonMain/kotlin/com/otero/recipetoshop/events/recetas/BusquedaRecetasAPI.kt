package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.CestaCompra.Receta

sealed class BusquedaRecetasAPI {
    data class onAddUserReceta(val nombre: String, val cantidad: Int): BusquedaRecetasAPI()

    object buscarRecetas: BusquedaRecetasAPI()

    data class updateQuery(val newQuery: String): BusquedaRecetasAPI()

    data class onAddYummlyReceta(val receta: Receta): BusquedaRecetasAPI()
}
package com.otero.recipetoshop.events.cestacompra

import com.otero.recipetoshop.domain.model.CestaCompra.Receta

sealed class BusquedaRecetasAPIEventos {
    data class onAddUserReceta(val nombre: String, val cantidad: Int): BusquedaRecetasAPIEventos()

    object buscarRecetasEventos: BusquedaRecetasAPIEventos()

    data class updateQuery(val newQuery: String): BusquedaRecetasAPIEventos()

    data class onAddYummlyReceta(val receta: Receta): BusquedaRecetasAPIEventos()
}
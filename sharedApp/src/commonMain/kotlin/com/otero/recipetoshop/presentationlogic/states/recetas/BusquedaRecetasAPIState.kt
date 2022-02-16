package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.CestaCompra.Receta

data class BusquedaRecetasAPIState (
    val id_cestaCompra: Int = -1,
    val lisaRecetasBuscadas: List<Receta> = emptyList(),
    val recetaCreada: Receta? = null,
    val query: String = ""
)
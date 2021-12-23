package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.recetas.Receta

data class RecetasCreacionBusquedaState (
    val id_listaRecetas: Int = -1,
    val lisaRecetasBuscadas: List<Receta> = emptyList(),
    val recetaCreada: Receta? = null,
    val query: String = ""
)
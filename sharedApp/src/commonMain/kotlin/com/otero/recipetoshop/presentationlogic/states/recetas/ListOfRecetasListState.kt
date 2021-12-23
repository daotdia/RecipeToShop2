package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.recetas.ListaRecetas

data class ListOfRecetasListState(
    val listaDeListasRecetas: List<ListaRecetas> = emptyList()
)

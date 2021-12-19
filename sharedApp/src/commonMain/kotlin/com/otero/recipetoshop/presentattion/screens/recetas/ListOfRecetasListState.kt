package com.otero.recipetoshop.presentattion.screens.recetas

import com.otero.recipetoshop.domain.model.recetas.ListaRecetas

data class ListOfRecetasListState(
    val listaDeListasRecetas: List<ListaRecetas> = emptyList()
)

package com.otero.recipetoshop.presentattion.screens.recetas

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.Queue

data class RecetasListState (
    val id_listaReceta: Int? = null,
    val nombre: String = "",
    val alimentos: List<Food> = listOf(),
    val recetas: List<Receta> = listOf(),
    val listasRecetas: List<ListaRecetas> = listOf(),
    var onNewReceta: Boolean = false,
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)
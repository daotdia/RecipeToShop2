package com.otero.recipetoshop.presentattion.screens.recetas

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.Queue

data class RecetasListState (
    val id_listaReceta_actual: Int? = null,
    val nombre: String = "",
    val alimentosActive: List<Food> = listOf(),
    val alimentosInactive: List<Food> = listOf(),
    val allAlimentos: List<Food> = listOf(),
    val recetasActive: List<Receta> = listOf(),
    val recetasInactive: List<Receta> = listOf(),
    val allrecetas: List<Receta> = listOf(),
    val listasRecetas: List<ListaRecetas> = listOf(),
    var onNewReceta: Boolean = false,
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)
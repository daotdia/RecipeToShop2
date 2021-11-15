package com.otero.recipetoshop.presentattion.screens.recetas

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.Queue

data class RecetasListState (
    val recetas: List<Receta> = listOf(),
    var onNewReceta: Boolean = false,
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)
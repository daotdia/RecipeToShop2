package com.otero.recipetoshop.presentationlogic.states.despensa

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.Queue

data class FoodListState(
    val allAlimentos: List<Food> = listOf(),
    val alimentosActivos: List<Food> = listOf(),
    val alimentosInactivos: List<Food> = listOf(),
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)


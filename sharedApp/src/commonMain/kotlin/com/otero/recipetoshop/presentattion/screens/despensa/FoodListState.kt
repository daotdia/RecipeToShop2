package com.otero.recipetoshop.presentattion.screens.despensa

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.Queue

data class FoodListState(
    val allAlimentos: List<Food> = listOf(),
    val alimentosActivos: List<Food> = listOf(),
    val alimentosInactivos: List<Food> = listOf(),
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)


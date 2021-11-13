package com.otero.recipetoshop.datasource.cachedespensa

import com.otero.recipetoshop.domain.model.despensa.Food

interface DespensaCache {
    fun insert(food: Food)

    fun insert(foods: List<Food>)

    fun search(query: String): List<Food>

    fun getAll(): List<Food>

    fun get(foodName: String): Food?
}
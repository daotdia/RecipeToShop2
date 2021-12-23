package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.domain.model.despensa.Food

interface DespensaCache {
    fun insertFoodDespensa(food: Food): Unit

    fun insertFoodsDespensa(foods: List<Food>): Unit

    fun searchFoodDespensa(query: String): List<Food>?

    fun getAllFoodsDespensa(): List<Food>?

    fun getFoodByActive(active: Boolean): List<Food>?

    fun getFoodDespensaById(id_food: Int): Food?

    fun removeAllFoodsDespensa(): Unit

    fun removeFoodDespensaById(id_food: Int): Unit
}
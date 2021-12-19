package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.descriptors.PrimitiveKind

class OnCLickFoodDespensa (
    private val foodCache: DespensaCache
) {
    fun onCLickFoodDespensa(
        food: Food,
        active: Boolean
    ): Flow<DataState<Food>> = flow {
        val new_food = food.copy(active = active)
        foodCache.insertFoodDespensa(food = new_food)
        emit(DataState.data(message = null, data = new_food))
    }
}
package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class OnCLickAlimento (
    private val despensaCache: DespensaCache
) {
    fun onCLickAlimento(
        alimento: Alimento,
        active: Boolean
    ): CommonFLow<DataState<Alimento>> = flow {
        val new_food = alimento.copy(active = active)
        despensaCache.insertarAlimentoDespensa(alimento = new_food)
        emit(DataState.data(message = null, data = new_food))
    }.asCommonFlow()
}
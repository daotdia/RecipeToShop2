package com.otero.recipetoshop.presentattion.screens.despensa

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.util.Queue
import com.otero.recipetoshop.domain.util.TipoUnidad

data class FoodState(
    var nombre: String = "",
    var tipo: String = TipoUnidad.GRAMOS.name,
    var cantidad: String = "0",
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)

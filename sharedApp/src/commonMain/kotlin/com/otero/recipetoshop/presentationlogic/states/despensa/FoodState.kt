package com.otero.recipetoshop.presentationlogic.states.despensa

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.dataEstructres.Queue
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad

data class FoodState(
    var nombre: String = "",
    var tipo: String = TipoUnidad.GRAMOS.name,
    var cantidad: String = "0",
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    var queryAutoComplete: String = ""
){
    constructor(): this(
        nombre = "",
        tipo = TipoUnidad.GRAMOS.name,
        cantidad = "0",
        queueError = Queue(mutableListOf()),
        queryAutoComplete = ""
    )
}

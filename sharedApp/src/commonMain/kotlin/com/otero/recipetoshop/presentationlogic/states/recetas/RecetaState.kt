package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.Queue

data class RecetaState(
    val cestaCompra_id: Int = -1,
    val receta_id: Int = -1,
    val nombre: String = "",
    val cantidad: String = "0",
    val imagen: String = "",
    val ingredientes: List<Alimento> = listOf(),
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    val resultadoAutoComplete: List<String> = listOf()
){
    constructor(): this(
        cestaCompra_id = -1,
        receta_id = -1,
        nombre = "",
        cantidad = "0",
        imagen = "",
        ingredientes = listOf(),
        queueError = Queue(mutableListOf()),
        resultadoAutoComplete = listOf()
    )
}

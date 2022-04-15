package com.otero.recipetoshop.presentationlogic.states.despensa

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.Queue

data class ListaAlimentosState(
    val allAlimentos: List<Alimento> = listOf(),
    val alimentosActivos: List<Alimento> = listOf(),
    val alimentosInactivos: List<Alimento> = listOf(),
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    val resultadoAutoCompletado: List<String> = listOf(),
    val queryAutoComplete: String = ""
)


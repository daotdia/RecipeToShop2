package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.util.Queue

data class RecetaState(
    val nombre: String = "",
    val cantidad: String = "0",
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf())
)

package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.Queue

data class CestaCompraState (
    val id_cestaCompra_actual: Int? = null,
    val id_receta_actual: Int? = null,
    val nombre: String = "",
    val alimentosActive: List<Alimento> = listOf(),
    val alimentosInactive: List<Alimento> = listOf(),
    val allAlimentos: List<Alimento> = listOf(),
    val recetasActive: List<Receta> = listOf(),
    val recetasInactive: List<Receta> = listOf(),
    val allrecetas: List<Receta> = listOf(),
    val listaCestasCompra: List<CestaCompra> = listOf(),
    var onNewReceta: Boolean = false,
    val queueError: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    val resultadosAutoCompleteAlimentos: List<String> = listOf(),
    val recetasFavoritas: List<Receta> = listOf(),
){
    constructor(): this(
        id_cestaCompra_actual = null,
        id_receta_actual = null,
        nombre = "",
        alimentosActive = listOf(),
        alimentosInactive = listOf(),
        allAlimentos = listOf(),
        recetasActive = listOf(),
        recetasInactive = listOf(),
        allrecetas = listOf(),
        listaCestasCompra = listOf(),
        onNewReceta = false,
        queueError = Queue(mutableListOf()),
        resultadosAutoCompleteAlimentos = listOf(),
        recetasFavoritas = listOf(),
    )
}
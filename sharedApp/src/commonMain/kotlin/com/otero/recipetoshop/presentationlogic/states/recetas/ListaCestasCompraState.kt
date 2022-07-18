package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra

data class ListaCestasCompraState(
    val listaCestasCompra: List<CestaCompra> = emptyList(),
    val id_cestaCompraActual: Int = -1
){
    constructor(): this(
        listaCestasCompra = emptyList(),
        id_cestaCompraActual = -1
    )
}

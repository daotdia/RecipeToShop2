package com.otero.recipetoshop.presentationlogic.states.recetas

import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra

data class ListaCestasCompraState(
    val listaCestasCompra: List<CestaCompra> = emptyList()
){
    constructor(): this(
        listaCestasCompra = emptyList()
    )
}

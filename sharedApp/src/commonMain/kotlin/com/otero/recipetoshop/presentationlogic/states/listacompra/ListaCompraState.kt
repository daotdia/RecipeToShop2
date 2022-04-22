package com.otero.recipetoshop.presentationlogic.states.listacompra

import com.otero.recipetoshop.domain.model.ListaCompra.Productos

data class ListaCompraState (
    val listaProductos: List<Productos.Producto> = listOf(),
    val id_cestaCompra: Int = -1
)
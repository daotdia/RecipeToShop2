package com.otero.recipetoshop.events.listacompra

import com.otero.recipetoshop.domain.model.ListaCompra.Productos


sealed class ListaCompraEvents {
    data class onCLickFilter(val filter_nombre: String): ListaCompraEvents()

    data class onClickProducto(val producto: Productos.Producto): ListaCompraEvents()
}
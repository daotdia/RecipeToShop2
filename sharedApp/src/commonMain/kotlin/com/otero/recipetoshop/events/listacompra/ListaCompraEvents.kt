package com.otero.recipetoshop.events.listacompra


sealed class ListaCompraEvents {
    data class onCLickFilter(val filter_nombre: String): ListaCompraEvents()
}
package com.otero.recipetoshop.domain.model.ListaCompra

data class ListaCompra(
    val productos: List<Producto>,
    val precio_total: Float,
    val peso_total: Float,
)
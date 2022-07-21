package com.otero.recipetoshop.presentationlogic.states.listacompra

import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.SupermercadosEnum

data class ListaCompraState (
    val listaProductos: List<Productos.Producto> = listOf(),
    val id_cestaCompra: Int = -1,
    val precio_total: Float = 0f,
    val peso_total: Float = 0f,
    val alimentos_cesta: List<Alimento> = listOf(),
    val alimentos_no_encontrados: List<Alimento> = listOf(),
    val supermercados: MutableSet<SupermercadosEnum> = mutableSetOf()
){
    constructor(): this(
        listaProductos = listOf(),
        id_cestaCompra = -1,
        precio_total = 0f,
        peso_total = 0f,
        alimentos_cesta = listOf(),
        alimentos_no_encontrados = listOf(),
        supermercados = mutableSetOf()
    )
}
package com.otero.recipetoshop.events.recetas

sealed class ListaCestasCompra {
    //data class onCantidadChange(val cantidad: String, val alimento: Alimento) : DespensaEventos()
    data class onNombreCestaChange(val nombre: String): ListaCestasCompra()

    data class onEnterCestaCompra(val id_cestaCompra: Int): ListaCestasCompra()

    data class onAddListaReceta(val nombre: String): ListaCestasCompra()
}
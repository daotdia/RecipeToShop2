package com.otero.recipetoshop.events.cestacompra

sealed class ListaCestasCompraEventos {
    //data class onCantidadChange(val cantidad: String, val alimento: Alimento) : DespensaEventos()
    data class onNombreCestaChange(val nombre: String): ListaCestasCompraEventos()

    data class onEnterCestaCompraEventos(val id_cestaCompra: Int): ListaCestasCompraEventos()

    data class onAddListaRecetaEventos(val nombre: String): ListaCestasCompraEventos()
}
package com.otero.recipetoshop.events.despensa

import com.otero.recipetoshop.domain.model.despensa.Alimento

sealed class DespensaEventos {

    class onClickAutoCompleteElement():DespensaEventos()

    data class onAutoCompleteChange(val nombre: String): DespensaEventos()

    data class onEditaAlimento(val id_alimento: Int, val nombre: String, val tipo: String, val cantidad: String): DespensaEventos()

    data class onCantidadChange(val cantidad: String, val alimento: Alimento) : DespensaEventos()

    data class onAddAlimento(val nombre: String, val tipo: String, val cantidad: String): DespensaEventos()

    data class onClickAlimento(val alimento: Alimento, val active: Boolean): DespensaEventos()

    data class onTipoAdd(val tipo: String): DespensaEventos()

    data class onCantidadAdd(val nombre: String): DespensaEventos()

    data class onSelectedNestedMenuItem(val option: String): DespensaEventos()

    data class onAlimentoDelete(val alimento: Alimento) : DespensaEventos()
}
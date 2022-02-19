package com.otero.recipetoshop.events.cestacompra.receta

import com.otero.recipetoshop.domain.util.TipoUnidad

sealed class RecetaEventos{
    data class onaAddIngrediente(val nombre: String, val cantidad: Int, val tipoUnidad: TipoUnidad): RecetaEventos()

    data class onClickIngrediente(val id_ingrediente: Int): RecetaEventos()

    data class onDeleteIngrediente(val id_ingrediente: Int): RecetaEventos()
}
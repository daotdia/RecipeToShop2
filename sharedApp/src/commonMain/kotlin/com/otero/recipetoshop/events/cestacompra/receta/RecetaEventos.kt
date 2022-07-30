package com.otero.recipetoshop.events.cestacompra.receta

import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.cestacompra.CestaCompraEventos

sealed class RecetaEventos{

    class onClickAutocompleteReceta(): RecetaEventos()

    data class onAutocompleteRecetaChange(val query: String): RecetaEventos()

    data class onEditIngrediente(val id_ingrediente: Int, val nombre: String, val  cantidad: Int, val tipoUnidad: String): RecetaEventos()

    data class onaAddIngrediente(val nombre: String, val cantidad: Int, val tipoUnidad: TipoUnidad): RecetaEventos()

    data class onClickIngrediente(val id_ingrediente: Int): RecetaEventos()

    data class onDeleteIngrediente(val id_ingrediente: Int): RecetaEventos()
}
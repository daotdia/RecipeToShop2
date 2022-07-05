package com.otero.recipetoshop.events.cestacompra

import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState

sealed class CestaCompraEventos {

    data class onReducirCantidadReceta(val receta: Receta): CestaCompraEventos()

    data class onAumentarCantidadReceta(val receta: Receta): CestaCompraEventos()

    class onClickAutocompleteAlimento(): CestaCompraEventos()

    data class onAutocompleteAlimentoChange(val query: String): CestaCompraEventos()

    data class onAddAlimento(val nombre: String, val cantidad: Int, val tipoUnidad: TipoUnidad): CestaCompraEventos()

    data class onDeleteRecetas(val menuItem: String): CestaCompraEventos()

    data class onDeleteReceta(val receta: Receta): CestaCompraEventos()

    data class onDeleteAlimento(val alimento: Alimento): CestaCompraEventos()

    data class onCantidadRecetaChange(val cantidad: Int, val receta: Receta): CestaCompraEventos()

    data class onCantidadAlimentoChange(val cantidad: Int, val alimento: Alimento): CestaCompraEventos()

    data class onRecetaClick(val receta: Receta, val active: Boolean): CestaCompraEventos()

    data class onAlimentoClick(val alimento: Alimento, val active: Boolean): CestaCompraEventos()

    data class onUpdateRecetaActive(val receta: Receta, val active: Boolean): CestaCompraEventos()

    data class onAddReceta(val nombre: String, val cantidad: Int): CestaCompraEventos()

    data class onUpdateRecetaFavorita(val receta: Receta, val isFavorita: Boolean): CestaCompraEventos()

    data class onSearchRecetas(val query: String): CestaCompraEventos()

    data class onAddRecetaExistente(val receta: Receta): CestaCompraEventos()

}
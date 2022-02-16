package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad

sealed class CestaCompraEventos {
    data class onAddAlimento(val nombre: String, val cantidad: Int, val tipoUnidad: TipoUnidad): CestaCompraEventos()

    data class onDeleteRecetas(val menuItem: String): CestaCompraEventos()

    data class onDeleteReceta(val receta: Receta): CestaCompraEventos()

    data class onDeleteAlimento(val alimento: Alimento): CestaCompraEventos()

    data class onCantidadRecetaChange(val cantidad: Int, val receta: Receta): CestaCompraEventos()

    data class onCantidadAlimentoChange(val cantidad: Int, val alimento: Alimento): CestaCompraEventos()

    data class onRecetaClick(val receta: Receta, val active: Boolean): CestaCompraEventos()

    data class onAlimentoClick(val alimento: Alimento, val active: Boolean): CestaCompraEventos()

    data class onUpdateRecetaActive(val receta: Receta, val active: Boolean): CestaCompraEventos()
}
package com.otero.recipetoshop.events.recetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad

sealed class ListOfRecetasListEvents {
    //data class onCantidadChange(val cantidad: String, val food: Food) : FoodListEvents()
    data class onNombreListaChange(val nombre: String): ListOfRecetasListEvents()

    data class onEnterListaDeLisaDeRecetas(val id_listaRecetas: Int): ListOfRecetasListEvents()

    data class onAddListaReceta(val nombre: String): ListOfRecetasListEvents()
}
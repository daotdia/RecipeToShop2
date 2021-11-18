package com.otero.recipetoshop.datasource.cacherecetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta

interface RecetaCache {
    fun insertListaRecetas(listaReceta: ListaRecetas)

    fun insertListaDeLIstarecetas(listaDeListaRecetas: List<ListaRecetas>)

    fun getAllListaRecetas(): List<ListaRecetas>?

    fun getRecetasByIdOfListaDeRecetas(nombreListaDeLista: String): List<Receta>?

    fun getListaRecetas(nombrelistaReceta: String): ListaRecetas?

    fun removeAllListaRecetas(): Unit

    fun removeListaReceta(listaRecetas: ListaRecetas): Unit
}
package com.otero.recipetoshop.datasource.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.Listarecetas_Entity
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas



fun String.toListaRecetas(nombreListaRecetas: String): ListaRecetas{
    return ListaRecetas(
        nombre = nombreListaRecetas,
        recetas = listOf()
    )
}

fun List<String>.toListaDeListaRecetas(): List<ListaRecetas>{
    return map{it.toListaRecetas(it)}
}
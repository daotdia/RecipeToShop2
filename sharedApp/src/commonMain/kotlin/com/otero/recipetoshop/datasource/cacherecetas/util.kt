package com.otero.recipetoshop.datasource.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.Listarecetas_Entity
import com.otero.recipetoshop.datasource.cachedespensa.Receta_Entity
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad


fun String.toListaRecetas(nombreListaRecetas: String): ListaRecetas{
    return ListaRecetas(
        nombre = nombreListaRecetas,
        recetas = listOf()
    )
}

fun List<String>.toListaDeListaRecetas(): List<ListaRecetas>{
    return map{it.toListaRecetas(it)}
}

fun Receta_Entity.toReceta(): Receta {
    var tipoParsed: TipoUnidad?
    try{
        if (tipo == null) {
            tipoParsed = null
        } else {
            tipoParsed = TipoUnidad.valueOf(tipo)
        }
    } catch (e: Exception){
        println("Este alimento no tiene un tipo correcto")
        tipoParsed = null
    }
    return Receta(
        nombre = nombre,
        cantidad = cantidad.toInt(),
        ingredientes = listOf(),
        tipo = tipoParsed
    )
}

fun List<Receta_Entity>.toListaRecetas(): List<Receta>{
    return map { it.toReceta() }
}
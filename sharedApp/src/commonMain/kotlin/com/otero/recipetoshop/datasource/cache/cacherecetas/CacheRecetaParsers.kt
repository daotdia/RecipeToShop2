package com.otero.recipetoshop.datasource.cache.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.Alimentos_Entity
import com.otero.recipetoshop.datasource.cachedespensa.Ingredients_Entity
import com.otero.recipetoshop.datasource.cachedespensa.Listarecetas_Entity
import com.otero.recipetoshop.datasource.cachedespensa.Receta_Entity
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad

//Parsers ListaRecetas.
fun Listarecetas_Entity.toListaRecetas(): ListaRecetas{
    return ListaRecetas(
        id_listaRecetas = id_listarceras.toInt(),
        nombre = nombre,
    )
}

fun List<Listarecetas_Entity>.toListaDeListaRecetas(): List<ListaRecetas>{
    return map{it.toListaRecetas()}
}

//Parsers Receta
fun Receta_Entity.toReceta(): Receta {
    return Receta(
        id_listaRecetas = id_listarecetas.toInt(),
        id_Receta = id_receta.toInt(),
        nombre = nombre,
        cantidad = cantidad.toInt(),
        user = user,
        active = active
    )
}

fun List<Receta_Entity>.toListaRecetas(): List<Receta>{
    return map { it.toReceta() }
}


//Parsers Alimentos
fun Alimentos_Entity.toFood(): Food{
    return Food(
        id_listaRecetas = id_listaReceta.toInt(),
        id_food = id_alimento.toInt(),
        nombre = nombre,
        cantidad = cantidad.toInt(),
        tipoUnidad = TipoUnidad.valueOf(tipo),
        active = active
    )
}

fun List<Alimentos_Entity>.toListaFood(): List<Food>{
    return map{it.toFood()}
}

//Parser Ingredientes
fun Ingredients_Entity.toFood(): Food{
    return Food(
        id_listaRecetas = id_listaRecetas.toInt(),
        id_receta = id_receta.toInt(),
        id_food = id_ingrediente.toInt(),
        nombre = nombre,
        cantidad = cantidad.toInt(),
        tipoUnidad = TipoUnidad.valueOf(tipo),
        active = active
    )
}

fun List<Ingredients_Entity>.toListFood(): List<Food>{
    return map { it.toFood() }
}
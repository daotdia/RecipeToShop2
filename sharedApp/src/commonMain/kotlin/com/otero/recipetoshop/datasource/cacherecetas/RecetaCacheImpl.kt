package com.otero.recipetoshop.datasource.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.FoodDBQueries
import com.otero.recipetoshop.datasource.cachedespensa.FoodDataBase
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas

class RecetaCacheImpl(
    private val recetaDataBase: FoodDataBase
) :RecetaCache
{
    private val queries: FoodDBQueries = recetaDataBase.foodDBQueries

    override fun insertListaRecetas(listaReceta: ListaRecetas) {
         queries.insertListaRecetas(
            nombre = listaReceta.nombre
            //Inserto las recetas y alimentos
                //Inserto los alimentos de cada receta
        )
    }

    override fun insertListaDeLIstarecetas(listaDeListaRecetas: List<ListaRecetas>) {
        for(listaRecetas in listaDeListaRecetas){
            insertListaRecetas(listaRecetas)
        }
    }

    //Lista de lista de recetas con ingredientes nulos.
    override fun getAllListaRecetas(): List<ListaRecetas> {
        return queries.getAllListaRecetas().executeAsList().toListaDeListaRecetas()
    }

    //Devuelve lista de receta con ingredientes nulos, hay que llamar otra vez con su nombre por si se quieren los ingredientes.
    override fun getListaRecetas(nombrelistaReceta: String): ListaRecetas? {
        return try {
            val nombre = queries.getListaRecetaById(nombrelistaReceta).executeAsOne()
            nombre.toListaRecetas(nombre)
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con nombre: ${nombrelistaReceta}")
            null
        }
    }

    override fun removeAllListaRecetas() {
        return queries.removeAllListaRecetas()
    }

    override fun removeListaReceta(listaRecetas: ListaRecetas) {
        return try {
            queries.removeListaReceta(nombre = listaRecetas.nombre)
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido encontrar la lista de recetas con nombre: ${listaRecetas.nombre}")
        }
    }
}
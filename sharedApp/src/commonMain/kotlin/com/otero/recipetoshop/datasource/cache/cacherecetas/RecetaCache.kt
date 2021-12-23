package com.otero.recipetoshop.datasource.cache.cacherecetas

import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta

interface RecetaCache {
    //ListaRecetas
    fun insertListaRecetas(listaReceta: ListaRecetas): Int

    fun insertListasRecetas(listasRecetas: List<ListaRecetas>): List<Int>?

    fun getAllListaRecetas(): List<ListaRecetas>?

    fun getListaRecetasById(id_listaReceta: Int): ListaRecetas?

    fun removeAllListaRecetas(): Unit

    fun removeListaRecetaById(id_listaReceta: Int): Unit

    fun lastIdInserted(): Int

    //Recetas
    fun insertRecetaToListaRecetas(receta: Receta): Int

    fun insertRecetasToListaRecetas(recetas: List<Receta>): Unit

    fun getAllRecetasInListasReceta(): List<Receta>?

    fun getRecetasByUserInListaRecetas(user: Boolean, id_listaReceta: Int): List<Receta>?

    fun getRecetasByActiveInListaRecetas(active: Boolean, id_listaReceta: Int): List<Receta>?

    fun getRecetasByListaRecetasInListaRecetas(id_listaReceta: Int): List<Receta>?

    fun getRecetaByIdInListaReceta(id_receta: Int): Receta?

    fun removeAllRecetasInListasRecetas(): Unit

    fun removeRecetasByListaRecetas(id_receta: Int): Unit

    fun removeRecetaByIdInListaRecetas(id_receta: Int): Unit

    //Alimentos
    fun insertAlimentoToListaRecetas(alimento: Food): Unit

    fun insertAlimentosToListaRecetas(alimentos: List<Food>): Unit

    fun getAlimentosByListaRecetas(id_listaReceta: Int): List<Food>?

    fun getAlimentosByActiveInListaRecetas(active: Boolean, id_listaReceta: Int): List<Food>?

    fun getAllAlimentosInListasRecetas(): List<Food>?

    fun getAlimentoByIdInListaRecetas(id_alimento: Int): Food?

    fun removeAllAlimentosInListasReceta(): Unit

    fun removeAlimentosByListaRecetasInListaRecetas(id_listaReceta: Int): Unit

    fun removeAlimentoByIdInListaRecetas(id_alimento: Int): Unit

    //Ingredientes
    fun insertIngredienteToReceta(ingrediente: Food): Unit

    fun insertIngredientesToReceta(ingredientes: List<Food>): Unit

    fun getAllIngredientesInListasRecetas(): List<Food>?

    fun getIngredientsByActiveInRecta(active: Boolean, id_receta: Int): List<Food>?

    fun getIngredientesByReceta(id_receta: Int): List<Food>?

    fun getIngredienteByIdInReceta(id_ingrediente: Int): Food?

    fun removeAllIngredientesInListasRecetas(): Unit

    fun removeIngredientesByRecetaInReceta(id_receta: Int): Unit

    fun removeIngredienteByIdInReceta(id_ingrediente: Int): Unit
}
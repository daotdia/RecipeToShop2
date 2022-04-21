package com.otero.recipetoshop.datasource.cache.cacherecetas

import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.domain.model.CestaCompra.Receta

interface RecetaCache {
    //CestaCompra
    fun insertCestaCompra(cestaCompra: CestaCompra): Int

    fun insertCestasCompra(cestasCompra: List<CestaCompra>): List<Int>?

    fun getAllCestasCompra(): List<CestaCompra>?

    fun getCestaCompraById(id_cestaCompra: Int): CestaCompra?

    fun removeAllCestasCompra(): Unit

    fun removeCestaCompraById(id_cestaCompra: Int): Unit

    fun lastIdInserted(): Int

    //Recetas
    fun insertRecetaToCestaCompra(receta: Receta): Int

    fun insertRecetasToCestaCompra(recetas: List<Receta>): Unit

    fun getAllRecetasInCestasCompra(): List<Receta>?

    fun getRecetasByUserInCestaCompra(user: Boolean, id_cestaCompra: Int): List<Receta>?

    fun getRecetasByActiveInCestaCompra(active: Boolean, id_cestaCompra: Int): List<Receta>?

    fun getRecetasByCestaCompra(id_cestaCompra: Int): List<Receta>?

    fun getRecetaByIdInCestaCompra(id_receta: Int): Receta?

    fun removeAllRecetasInCestasCompra(): Unit

    fun removeRecetasByCestaCompra(id_cestaCompra: Int): Unit

    fun removeRecetaByIdInCestaCompra(id_receta: Int): Unit

    fun getAllRecetasFavoritas(): List<Receta>

    //Alimentos
    fun insertAlimentoToCestaCompra(alimento: Alimento): Unit

    fun insertAlimentosToCestaCompra(alimentos: List<Alimento>): Unit

    fun getAlimentosByCestaCompra(id_cestaCompra: Int): List<Alimento>?

    fun getAlimentosByActiveInCestaCompra(active: Boolean, id_cestaCompra: Int): List<Alimento>?

    fun getAllAlimentosInCestasCompra(): List<Alimento>?

    fun getAlimentoByIdInCestaCompra(id_alimento: Int): Alimento?

    fun removeAllAlimentosInCestasCompra(): Unit

    fun removeAlimentosByCestaCompra(id_cestaCompra: Int): Unit

    fun removeAlimentoByIdInCestaCompra(id_alimento: Int): Unit

    //Ingredientes
    fun insertIngredienteToReceta(ingrediente: Alimento): Unit

    fun insertIngredientesToReceta(ingredientes: List<Alimento>): Unit

    fun getAllIngredientesInCestasCompra(): List<Alimento>?

    fun getIngredientsByActiveInRecta(active: Boolean, id_receta: Int): List<Alimento>?

    fun getIngredientesByActiveByIdCestaCompra(active: Boolean, id_cestaCompra: Int): List<Alimento>?

    fun getIngredientesByReceta(id_receta: Int): List<Alimento>?

    fun getIngredienteByIdInReceta(id_ingrediente: Int): Alimento?

    fun removeAllIngredientesInCestasCompra(): Unit

    fun removeIngredientesByRecetaInReceta(id_receta: Int): Unit

    fun removeIngredienteByIdInReceta(id_ingrediente: Int): Unit
}
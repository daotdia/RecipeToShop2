package com.otero.recipetoshop.datasource.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.FoodDBQueries
import com.otero.recipetoshop.datasource.cachedespensa.FoodDataBase
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.model.recetas.Receta

class RecetaCacheImpl(
    private val recetaDataBase: FoodDataBase
) :RecetaCache
{
    //Lista Recetas
    private val queries: FoodDBQueries = recetaDataBase.foodDBQueries

    override fun insertListaRecetas(listaReceta: ListaRecetas): Int {
        var id: Int = -1
        recetaDataBase.transaction {
            queries.insertListaRecetas(
                nombre = listaReceta.nombre
            )
            id = lastIdInserted()
        }
        return id
    }

    override fun insertListasRecetas(listasRecetas: List<ListaRecetas>): List<Int> {
        val idList: ArrayList<Int> = ArrayList()
        for(listaRecetas in listasRecetas){
            idList.add(insertListaRecetas(listaRecetas))
        }
        return idList
    }

    //Lista de lista de recetas con ingredientes nulos.
    override fun getAllListaRecetas(): List<ListaRecetas> {
        return queries.getAllListaRecetas().executeAsList().toListaDeListaRecetas()
    }

    //Devuelve lista de receta con ingredientes nulos, hay que llamar otra vez con su nombre por si se quieren los ingredientes.
    override fun getListaRecetasById(id_listaReceta: Int): ListaRecetas? {
        return try {
            queries
                .getListaRecetaById(id_listarceras = id_listaReceta.toLong())
                .executeAsOne()
                .toListaRecetas()
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_listaReceta}")
            null
        }
    }
    override fun removeAllListaRecetas() {
        return queries.removeAllListaRecetas()
    }

    override fun removeListaRecetaById(id_listaReceta: Int) {
        return try {
            queries.removeListaReceta(id_listarceras = id_listaReceta.toLong())
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido encontrar la lista de recetas con id: ${id_listaReceta}")
        }
    }

    override fun lastIdInserted(): Int {
        return queries.lastInsertRowId().executeAsOne().toInt()
    }


    //Recetas
    override fun insertRecetaToListaRecetas(receta: Receta) {
        if(receta.id_Receta != null){
            queries.removeRecetaByIdInListaReceta(receta.id_Receta.toLong())
        }
        queries.insertRecetaToListaRecetas(
            id_listarecetas = receta.id_listaRecetas.toLong(),
            nombre = receta.nombre,
            cantidad = receta.cantidad.toLong(),
            user = receta.user,
            active = receta.active
        )
    }

    override fun insertRecetasToListaRecetas(recetas: List<Receta>) {
        for(receta in recetas){
            insertRecetaToListaRecetas(receta)
        }
    }

    override fun getAllRecetasInListasReceta(): List<Receta> {
        return queries
            .getAllRecetasInListasRecetas()
            .executeAsList()
            .toListaRecetas()
    }

    override fun getRecetasByUserInListaRecetas(user: Boolean, id_listaReceta: Int): List<Receta> {
        return queries
            .getRecetasByUserInListaRecetas(user = user, id_listarecetas = id_listaReceta.toLong())
            .executeAsList()
            .toListaRecetas()
    }

    override fun getRecetasByActiveInListaRecetas(active: Boolean, id_listaReceta: Int): List<Receta> {
        return queries
            .getRecetasByActiveInListaRecetas(active = active, id_listarecetas = id_listaReceta.toLong())
            .executeAsList()
            .toListaRecetas()
    }

    override fun getRecetasByListaRecetasInListaRecetas(id_listaReceta: Int): List<Receta>? {
        return try{
             queries
                .getRecetasByListaRecetasInListaRecetas(id_listarecetas = id_listaReceta.toLong())
                .executeAsList()
                .toListaRecetas()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_listaReceta}")
            null
        }
    }

    override fun getRecetaByIdInListaReceta(id_receta: Int): Receta? {
        return try{
            queries
                .getRecetaByIdInListaReceta(id_receta = id_receta.toLong())
                .executeAsOne()
                .toReceta()
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
            null
        }
    }

    override fun removeAllRecetasInListasRecetas() {
        queries.removeAllRecetasInListasRecetas()
    }

    override fun removeRecetasByListaRecetas(id_receta: Int) {
        try{
            queries.removeRecetaByListaRecetasInListaReceta(id_listaRecetas = id_receta.toLong())
        } catch (e:Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
        }
    }

    override fun removeRecetaByIdInListaRecetas(id_receta: Int) {
        try{
            queries.removeRecetaByIdInListaReceta(id_receta = id_receta.toLong())
        } catch (e:Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
        }
    }


    //Alimentos
    override fun insertAlimentoToListaRecetas(alimento: Food) {
        if(alimento.id_food != null){
            queries.removeAlimentoByIdInListaRecetas(alimento.id_food.toLong())
        }
        queries.insertAlimentoToListaRecetas(
            id_listaReceta = alimento.id_listaRecetas!!.toLong(),
            nombre = alimento.nombre,
            cantidad = alimento.cantidad.toLong(),
            tipo = alimento.tipoUnidad.name,
            active = alimento.active
        )
    }

    override fun insertAlimentosToListaRecetas(alimentos: List<Food>) {
        for(alimento in alimentos){
            insertAlimentoToListaRecetas(alimento)
        }
    }

    override fun getAlimentosByListaRecetas(id_listaReceta: Int): List<Food>? {
        return try{
            queries
                .getAlimentosByListaRecetaInListaReceta(id_listaReceta = id_listaReceta.toLong())
                .executeAsList()
                .toListaFood()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_listaReceta}")
            null
        }
    }

    override fun getAlimentosByActiveInListaRecetas(active: Boolean, id_listaReceta: Int): List<Food> {
        return queries
            .getAlimentosByActiveInListaRecetas(active =active, id_listaReceta = id_listaReceta.toLong())
            .executeAsList()
            .toListaFood()
    }

    override fun getAllAlimentosInListasRecetas(): List<Food> {
        return queries
            .getAllAlimentosInListaRecetas()
            .executeAsList()
            .toListaFood()
    }

    override fun getAlimentoByIdInListaRecetas(id_alimento: Int): Food? {
        return try {
            queries
                .getAlimentosByIdInListaRecetas(id_alimento = id_alimento.toLong())
                .executeAsOne()
                .toFood()
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_alimento}")
            null
        }
    }

    override fun removeAllAlimentosInListasReceta() {
        queries.removeAllAlimentosInListasRecetas()
    }

    override fun removeAlimentosByListaRecetasInListaRecetas(id_listaReceta: Int) {
        try{
            queries.removeAlimentosByListaRecetasInListaRecetas(id_listaReceta = id_listaReceta.toLong())
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_listaReceta}")
        }
    }

    override fun removeAlimentoByIdInListaRecetas(id_alimento: Int) {
        try{
            queries.removeAlimentoByIdInListaRecetas(id_alimento = id_alimento.toLong())
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_alimento}")
        }
    }


    //Ingredientes
    override fun insertIngredienteToReceta(ingrediente: Food) {
        queries.insertIngredienteToReceta(
            id_receta = ingrediente.id_receta!!.toLong(),
            id_listaRecetas = ingrediente.id_listaRecetas!!.toLong(),
            nombre = ingrediente.nombre,
            cantidad = ingrediente.cantidad.toLong(),
            tipo = ingrediente.tipoUnidad.name,
            active = ingrediente.active
        )
    }

    override fun insertIngredientesToReceta(ingredientes: List<Food>) {
        for(ingrediente in ingredientes){
            insertIngredienteToReceta(ingrediente)
        }
    }

    override fun getIngredientesByReceta(id_receta: Int): List<Food>? {
        return try{
            queries
                .getIngredientesByRecetaInReceta(id_receta = id_receta.toLong())
                .executeAsList()
                .toListFood()
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
            null
        }
    }

    override fun getAllIngredientesInListasRecetas(): List<Food> {
        return queries
                .getAllIngredientesInListasRecetas()
                .executeAsList()
                .toListFood()

    }

    override fun getIngredientsByActiveInRecta(active: Boolean, id_receta: Int): List<Food> {
        return queries
            .getIngredientsByActiveInReceta(active = active, id_receta = id_receta.toLong())
            .executeAsList()
            .toListFood()
    }

    override fun getIngredienteByIdInReceta(id_ingrediente: Int): Food? {
        return try{
            queries
                .getIngredienteByIdInReceta(id_alimento = id_ingrediente.toLong())
                .executeAsOne()
                .toFood()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_ingrediente}")
            null
        }
    }

    override fun removeAllIngredientesInListasRecetas() {
        queries.removeAllIngredientesInListasRecetas()
    }

    override fun removeIngredientesByRecetaInReceta(id_receta: Int) {
        try{
            queries.removeIngredientesByRecetaInReceta(id_receta = id_receta.toLong())
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
        }
    }

    override fun removeIngredienteByIdInReceta(id_ingrediente: Int) {
        try {
            queries.removeIngredienteByIdInReceta(id_alimento = id_ingrediente.toLong())
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_ingrediente}")

        }
    }
}
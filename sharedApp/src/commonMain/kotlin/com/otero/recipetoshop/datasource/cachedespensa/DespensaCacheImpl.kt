package com.otero.recipetoshop.datasource.cachedespensa

import com.otero.recipetoshop.domain.model.despensa.Food


class DespensaCacheImpl(
    private val foodDataBase: FoodDataBase
) :DespensaCache{
    private val queries: FoodDBQueries = foodDataBase.foodDBQueries

    override fun insert(food: Food) {
        queries.insertFood(
            nombre = food.nombre,
            tipo = food.tipoUnidad.name,
            cantidad = food.cantidad.toLong()
        )
    }

    override fun insert(foods: List<Food>) {
        for(food in foods){
            insert(food)
        }
    }

    override fun search(query: String): List<Food> {
        return queries.searchFoods(
            query = query,
        ).executeAsList().toFoodList() // Faltan convertir las entidades de recetas a recetas.
    }

    override fun getAll(): List<Food> {
        return queries.getAllFoods()
            .executeAsList().toFoodList() // Faltan convertir las entidades de recetas a recetas.
    }

    override fun get(foodName: String): Food? {
        return try {
            queries.getFoodById(nombre = foodName).executeAsOne().toFood() // Faltan convertir las entidades de recetas a recetas.
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtener el alimento con nombre: ${foodName}")
            null
        }
    }

    override fun removeAll() {
        return queries.removeAll()
    }

    override fun removeFood(food: Food) {
        return try {
            queries.removeFood(nombre = food.nombre)
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtener el alimento con nombre: ${food.nombre}")
        }
    }
}
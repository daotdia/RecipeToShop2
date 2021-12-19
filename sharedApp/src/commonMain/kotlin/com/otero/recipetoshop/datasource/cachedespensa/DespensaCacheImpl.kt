package com.otero.recipetoshop.datasource.cachedespensa

import com.otero.recipetoshop.domain.model.despensa.Food


class DespensaCacheImpl(
    private val foodDataBase: FoodDataBase
) :DespensaCache{
    private val queries: FoodDBQueries = foodDataBase.foodDBQueries

    override fun insertFoodDespensa(food: Food) {
        if(food.id_food != null){
            queries.removeFoodById(food.id_food.toLong())
        }
        queries.insertFood(
            nombre = food.nombre,
            tipo = food.tipoUnidad.name,
            cantidad = food.cantidad.toLong(),
            active = food.active
        )
    }

    override fun insertFoodsDespensa(foods: List<Food>) {
        for(food in foods){
            insertFoodDespensa(food)
        }
    }

    override fun searchFoodDespensa(query: String): List<Food> {
        return queries.searchFoods(
            query = query,
        ).executeAsList().toFoodList()
    }

    override fun getAllFoodsDespensa(): List<Food> {
        return queries.getAllFoods()
            .executeAsList()
            .toFoodList()
    }

    override fun getFoodByActive(active: Boolean): List<Food>? {
        return queries.getFoodByActive(active = active)
            .executeAsList()
            .toFoodList()
    }

    override fun getFoodDespensaById(id_food: Int): Food? {
        return try {
            queries.getFoodById(id_food.toLong())
                .executeAsOne()
                .toFood()
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtener el alimento con id: ${id_food}")
            null
        }
    }

    override fun removeAllFoodsDespensa() {
        return queries.removeAllFoods()
    }

    override fun removeFoodDespensaById(id_food: Int) {
        return try {
            queries.removeFoodById(id_despensa = id_food.toLong())
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtener el alimento con id: ${id_food}")
        }
    }
}
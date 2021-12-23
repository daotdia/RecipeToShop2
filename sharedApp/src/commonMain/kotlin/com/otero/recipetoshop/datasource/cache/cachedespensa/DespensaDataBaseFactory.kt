package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.datasource.cachedespensa.Despensa_Entity
import com.otero.recipetoshop.datasource.cachedespensa.FoodDataBase
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.squareup.sqldelight.db.SqlDriver

class DespensaDataBaseFactory(
    private val driverFactory: FoodDriverFactory,
) {
    fun createDataBase(): FoodDataBase {
        return FoodDataBase(driverFactory.createDriver())
    }
}

expect class FoodDriverFactory{
    fun createDriver(): SqlDriver
}

fun Despensa_Entity.toFood(): Food {
    return Food(
        id_food = id_despensa.toInt(),
        nombre = nombre,
        tipoUnidad = parserTipoUnidad(tipo),
        cantidad = cantidad.toInt(),
        active = active
    )
}

private fun parserTipoUnidad(tipo: String): TipoUnidad{
    try{
        TipoUnidad.valueOf(tipo)
    } catch(e: Exception){
        println(e.message + " ////// El tipo de alimento no es correcto al parsear desde cache")
    }

    TipoUnidad.values().forEach { tipe ->
        if(tipe.name.equals(tipo)){
            return tipe
        }
    }
    return TipoUnidad.GRAMOS
}

fun List<Despensa_Entity>.toFoodList(): List<Food>{
    return map{it.toFood()}
}


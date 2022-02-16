package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.datasource.cachedespensa.Despensa_Entity
import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.squareup.sqldelight.db.SqlDriver

class DespensaDataBaseFactory(
    private val driverFactory: AlimentosDriverFactory,
) {
    fun createDataBase(): RecipeToShopDB {
        return RecipeToShopDB(driverFactory.createDriver())
    }
}

expect class AlimentosDriverFactory{
    fun createDriver(): SqlDriver
}

fun Despensa_Entity.toAlimento(): Alimento {
    return Alimento(
        id_alimento = id_despensa.toInt(),
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

fun List<Despensa_Entity>.toListaAlimentos(): List<Alimento>{
    return map{it.toAlimento()}
}


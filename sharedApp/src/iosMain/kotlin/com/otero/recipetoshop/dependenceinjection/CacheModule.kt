package com.otero.recipetoshop.dependenceinjection

import com.otero.recipetoshop.datasource.cache.cachedespensa.AlimentosDriverFactory
import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCacheImpl
import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaDataBaseFactory
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCacheImpl
import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB

class CacheModule {

    //Obtengo el driver de IOS.
    private val driverFactory: AlimentosDriverFactory by lazy {
        AlimentosDriverFactory()
    }

    //Obtengo el productor de base de datos
    private val cacheFactory: RecipeToShopDB by lazy {
        DespensaDataBaseFactory(
            driverFactory = driverFactory
        ).createDataBase()
    }

    //Obtengo las caches de despensa
    val despensaCache:  DespensaCache by lazy {
        DespensaCacheImpl(
            cestaCompraDataBase = cacheFactory
        )
    }

    //Obtengo la cache de recetas
    val recetasCache: RecetaCacheImpl by lazy {
        RecetaCacheImpl(
            cestaCompraDataBase = cacheFactory
        )
    }
}
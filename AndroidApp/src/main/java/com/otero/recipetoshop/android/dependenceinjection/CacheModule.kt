package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.android.BaseApplication
import com.otero.recipetoshop.datasource.cache.cachedespensa.*
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCacheImpl
import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Este es el modulo de inyección de dependencias para obtener el servicio de caché de la aplicacion.
Se utilizan en los casos de uso.
 */

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Singleton
    @Provides
    fun providesFoodDataBase(context: BaseApplication): RecipeToShopDB{
        return DespensaDataBaseFactory(driverFactory = AlimentosDriverFactory(context = context)).createDataBase()
    }
    @Singleton
    @Provides
    fun providesFoodCache(despensaDataBase: RecipeToShopDB): DespensaCache{
        return DespensaCacheImpl(cestaCompraDataBase = despensaDataBase)
    }
    @Singleton
    @Provides
    fun providesRecetaCache(recetaDataBase: RecipeToShopDB): RecetaCache{
        return RecetaCacheImpl(cestaCompraDataBase = recetaDataBase)
    }
}
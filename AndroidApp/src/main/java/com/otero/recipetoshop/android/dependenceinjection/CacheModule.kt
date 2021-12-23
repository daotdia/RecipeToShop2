package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.android.BaseApplication
import com.otero.recipetoshop.datasource.cache.cachedespensa.*
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCacheImpl
import com.otero.recipetoshop.datasource.cachedespensa.FoodDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

//    @Singleton
//    @Provides
//    fun providesRecipeDataBase(context: BaseApplication): RecipeDataBase {
//        return RecipeDataBaseFactory(driverFactory = com.otero.recipetoshop.datasource.cacherecipe.DriverFactory(context)).createDataBase()
//    }
//
//    @Singleton
//    @Provides
//    fun providesRecipeCache(
//        recipeDataBase: RecipeDataBase
//    ): RecipeCache {
//        return RecipeCacheImpl(
//            recipeDataBase = recipeDataBase
//        )
//    }

    @Singleton
    @Provides
    fun providesFoodDataBase(context: BaseApplication): FoodDataBase {
        return DespensaDataBaseFactory(driverFactory = FoodDriverFactory(context = context)).createDataBase()
    }

    @Singleton
    @Provides
    fun providesFoodCache(foodDataBase: FoodDataBase): DespensaCache{
        return DespensaCacheImpl(foodDataBase = foodDataBase)
    }

    @Singleton
    @Provides
    fun providesRecetaCache(recetaDataBase: FoodDataBase): RecetaCache{
        return RecetaCacheImpl(recetaDataBase = recetaDataBase)
    }
}
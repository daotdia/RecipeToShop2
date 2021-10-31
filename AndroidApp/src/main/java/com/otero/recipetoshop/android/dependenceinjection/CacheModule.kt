package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.android.BaseApplication
import com.otero.recipetoshop.datasource.cache.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun providesRecipeDataBase(context: BaseApplication): RecipeDataBase {
        return RecipeDataBaseFactory(driverFactory = DriverFactory(context)).createDataBase()
    }

    @Singleton
    @Provides
    fun provideRecipeCache(
        recipeDataBase: RecipeDataBase
    ): RecipeCache{
        return RecipeCacheImpl(
            recipeDataBase = recipeDataBase
        )
    }
}
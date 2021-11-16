package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.Interactors.RecipeList.SearchRecipes
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.recetas.AddNewListaReceta
import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.network.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

//    @Singleton
//    @Provides
//    fun provideSearchRecipes(
//        recipeService: RecipeService,
//        recipeCache: RecipeCache
//    ): SearchRecipes{
//        return SearchRecipes(
//            recipeService = recipeService,
//            recipeCache = recipeCache
//        )
//    }
//
//    @Singleton
//    @Provides
//    fun provideGetRecipe(
//        recipeCache: RecipeCache
//    ): GetRecipe {
//        return GetRecipe(recipeCache = recipeCache)
//    }

    @Singleton
    @Provides
    fun provideChangeCantidadFood(
        despensaCache: DespensaCache
    ): ChangeCantidadFood{
        return ChangeCantidadFood(foodCache = despensaCache)
    }

    @Singleton
    @Provides
    fun provideInsertNewFoodItem(
        despensaCache: DespensaCache
    ): InsertNewFoodItem{
        return InsertNewFoodItem(foodCache = despensaCache)
    }

    @Singleton
    @Provides
    fun provideGetFoods(
        despensaCache: DespensaCache
    ): GetFoods{
        return GetFoods(foodCache = despensaCache)
    }

    @Singleton
    @Provides
    fun provideDeleteFoods(
        despensaCache: DespensaCache
    ): DeleteFoods{
        return DeleteFoods(foodCache = despensaCache)
    }

    @Singleton
    @Provides
    fun provideDeleteFood(
        despensaCache: DespensaCache
    ): DeleteFood{
        return DeleteFood(foodCache = despensaCache)
    }

    @Singleton
    @Provides
    fun providesAddNewListaReceta(
        recetaCache: RecetaCache
    ): AddNewListaReceta{
        return AddNewListaReceta(recetaCache = recetaCache)
    }
}
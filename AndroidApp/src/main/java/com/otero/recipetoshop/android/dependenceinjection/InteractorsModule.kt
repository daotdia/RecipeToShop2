package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.Interactors.RecipeList.SearchRecipes
import com.otero.recipetoshop.Interactors.despensa.ChangeCantidadFood
import com.otero.recipetoshop.Interactors.despensa.InsertNewFoodItem
import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
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
}
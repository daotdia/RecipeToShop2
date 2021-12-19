package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.recetas.*
import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
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
    ): AddNewListaRecetas{
        return AddNewListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesOnEnterListaDeRecetas(
        recetaCache: RecetaCache
    ): OnEnterListaDeRecetas{
        return OnEnterListaDeRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesPrintListaDeListasRecetas(
        recetaCache: RecetaCache
    ): PrintListaDeListasRecetas{
        return PrintListaDeListasRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesAddElementoListaRecetas(
        recetaCache: RecetaCache
    ): AddElementoListaRecetas{
        return AddElementoListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesGetListaReceta(
        recetaCache: RecetaCache
    ): GetListaRecetas{
        return GetListaRecetas(recetaCache = recetaCache)
    }
}
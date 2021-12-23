package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.recetas.busquedarecetas.BuscarRecetas
import com.otero.recipetoshop.Interactors.recetas.listaldelistasrecetas.*
import com.otero.recipetoshop.Interactors.recetas.listarecetas.*
import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
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
    fun provideOnClickFoodDespensa(
        foodCache: DespensaCache
    ): OnCLickFoodDespensa{
        return OnCLickFoodDespensa(foodCache = foodCache)
    }
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
    ): AddNewListaRecetas {
        return AddNewListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesOnEnterListaDeRecetas(
        recetaCache: RecetaCache
    ): OnEnterListaDeRecetas {
        return OnEnterListaDeRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesPrintListaDeListasRecetas(
        recetaCache: RecetaCache
    ): PrintListaDeListasRecetas {
        return PrintListaDeListasRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesAddRecetaListaRecetas(
        recetaCache: RecetaCache
    ): AddRecetaListaRecetas {
        return AddRecetaListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesGetListaReceta(
        recetaCache: RecetaCache
    ): GetListaRecetas {
        return GetListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun provideAddAlimentoListaRecetas(
        recetaCache: RecetaCache
    ): AddAlimentoListaRecetas{
        return AddAlimentoListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesDeleteAlimentoListaRecetas(
        recetaCache: RecetaCache
    ): DeleteAlimentoListaRecetas{
        return DeleteAlimentoListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesDeleteRecetaListaRecetas(
        recetaCache: RecetaCache
    ): DeleteRecetaListaRecetas{
        return DeleteRecetaListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesUpdateReceta(
        recetaCache: RecetaCache
    ): UpdateReceta{
        return UpdateReceta(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesUpdateAlimentoListaRecetas(
        recetaCache: RecetaCache
    ): UpdateAlimentoListaRecetas{
        return UpdateAlimentoListaRecetas(recetaCache = recetaCache)
    }

    @Singleton
    @Provides
    fun providesBuscarRecetas(
        recetaCache: RecetaCache,
        recipeService: RecipeService
    ): BuscarRecetas{
        return BuscarRecetas(recetaCache = recetaCache, recipeService = recipeService)
    }
}
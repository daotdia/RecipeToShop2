package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.Interactors.RecipeList.SearchRecipes
import com.otero.recipetoshop.datasource.network.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideSearchRecipes(
        recipeService: RecipeService
    ): SearchRecipes{
        return SearchRecipes(recipeService = recipeService)
    }
}
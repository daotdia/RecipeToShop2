package com.otero.recipetoshop.android.presentation.navigation

sealed class Pantalla (val route: String)
{
    object RecipeList: Pantalla("recipeList")

    object RecipeDetail: Pantalla("recipeDetail")
}
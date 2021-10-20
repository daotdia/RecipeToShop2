package com.otero.recipetoshop.android.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.otero.recipetoshop.android.presentation.recipe_detail.DescripcionReceta
import com.otero.recipetoshop.android.presentation.recipe_detail.RecipeDetailViewModel
import com.otero.recipetoshop.android.presentation.recipe_list.ListaRecetas
import com.otero.recipetoshop.android.presentation.recipe_list.RecipeListViewModel

@Composable
fun Navegacion(){
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = Pantalla.RecipeList.route){
        composable(
            route = Pantalla.RecipeList.route
        ){ navBackStackEntry ->val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: RecipeListViewModel = viewModel("RecipeListViewModel", factory )
            ListaRecetas(
              onSelectedRecipe = { recipeId ->
                  navControlador.navigate(Pantalla.RecipeDetail.route + "/$recipeId")
              }
            )
            viewModel.loadRecipes()
        }
        composable(
            route = Pantalla.RecipeDetail.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId"){
                type = NavType.IntType
            })
        ){  navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: RecipeDetailViewModel = viewModel("RecipeDetailViewModel", factory )
            DescripcionReceta(
                recipe = viewModel.recipe.value,
            )
        }
    }
}
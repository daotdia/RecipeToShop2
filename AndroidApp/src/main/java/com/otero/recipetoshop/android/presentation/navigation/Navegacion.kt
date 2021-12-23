package com.otero.recipetoshop.android.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.otero.recipetoshop.android.presentation.components.despensa.FoodList
import com.otero.recipetoshop.android.presentation.components.recetas.ListaDeListaRectas
import com.otero.recipetoshop.android.presentation.components.recetas.BackDropListaItemsListaDeRecetas
import com.otero.recipetoshop.android.presentation.components.recetas.busquedacreacionrecetas.BusquedaCreacionRecetasScreen
import com.otero.recipetoshop.android.presentation.controllers.despensa.FoodListViewModel
import com.otero.recipetoshop.android.presentation.controllers.recetas.ListOfRecetasListViewModel
import com.otero.recipetoshop.android.presentation.controllers.recetas.RecetaListViewModel
import com.otero.recipetoshop.android.presentation.controllers.recetas.busquedaycreacion.PantallaCreacionBusquedaRecetasViewModel
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Navegacion(
    navController: NavHostController,
    padding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = RutasNavegacion.Despensa.route,
        modifier = Modifier.padding(paddingValues = padding)
    ){
//        composable(
//            route = RutasNavegacion.RecipeList.route
//        ){ navBackStackEntry ->
//            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
//            val viewModel: RecipeListViewModel = viewModel("RecipeListViewModel", factory )
//            ListaRecetas(
//                state = viewModel.state.value,
//                onTriggerEvent = viewModel::onTriggerEvent,
//                onClickRecipeListItem = { recipeId ->
//                    navController.navigate(RutasNavegacion.RecipeDetail.route + "/$recipeId")
//                }
//            )
//        }
//        composable(
//            route = RutasNavegacion.RecipeDetail.route + "/{recipeId}",
//            arguments = listOf(navArgument("recipeId"){
//                type = NavType.IntType
//            })
//        ){  navBackStackEntry ->
//            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
//            val viewModel: RecipeDetailViewModel = viewModel("RecipeDetailViewModel", factory )
//            DescripcionReceta(
//                recipe = viewModel.recipe.value,
//            )
//        }

        //Pantalla de despensa
        composable(
            route = RutasNavegacion.Despensa.route
        ){navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: FoodListViewModel = viewModel("FoodListViewModel", factory )
            Surface(color = secondaryLightColor){
                FoodList(
                    listState = viewModel.listState,
                    onTriggeEvent = viewModel::onTriggerEvent
                )
            }
        }

        //Pantalla de lista de listas de recetas.
        composable(
            route = RutasNavegacion.ListaDeListaRecetas.route
        ){navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val listOfRecetasViewModel: ListOfRecetasListViewModel = viewModel("ListOfRecetasListViewModel", factory )
            Surface(color = secondaryLightColor){
                ListaDeListaRectas(
                    navController = navController,
                    recetasState = listOfRecetasViewModel.listadelistarecetasstate,
                    onTriggeEvent = listOfRecetasViewModel::onTriggerEvent,
                )
            }
        }

        //Pantalla de lista de recetas.
        composable(
            route = RutasNavegacion.ListaRecetas.route + "/{listarecetasid}",
            arguments = listOf(navArgument("listarecetasid"){
                type = NavType.IntType
            })
        ){ navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val recetaListViewModel: RecetaListViewModel = viewModel("RecetaListViewModel", factory )

            BackDropListaItemsListaDeRecetas(
                stateListaRecetas = recetaListViewModel.listaRecetasState,
                onTriggeEventReceta = recetaListViewModel::onTriggerEvent,
                navController = navController
            )
        }

        //Pantalla dde búsqueda/creación de recetas.
        composable(
            route = RutasNavegacion.Busquedacreacionrecetas.route + "/{listarecetasid}",
            arguments = listOf(navArgument("listarecetasid"){
                type = NavType.IntType
            })
        ){ navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val busquedaCreacionRecetasViewModel: PantallaCreacionBusquedaRecetasViewModel = viewModel("PantallaCreacionBusquedaRecetasViewModel", factory )

            BusquedaCreacionRecetasScreen(
                busquedaCreacionState = busquedaCreacionRecetasViewModel.creacionBusquedarecetas,
                onTriggeEventReceta = busquedaCreacionRecetasViewModel::onTriggerEvent,
                navController = navController
            )
        }

        //Pantalla de Lista de la Compra.
        composable(
            route = RutasNavegacion.ListaCompra.route
        ){
            Surface(color = secondaryLightColor){}
        }

        //Pantalla de perfil
        composable(
            route = RutasNavegacion.Perfil.route
        ){
            Surface(color = secondaryLightColor){}
        }

        //Pantalla de FAQs
        composable(
            route = RutasNavegacion.FAQs.route
        ){
            Surface(color = secondaryLightColor){}
        }

        //Pantalla de Contacto.
        composable(
            route = RutasNavegacion.Contacto.route
        ){
            Surface(color = secondaryLightColor){}
        }
    }
}
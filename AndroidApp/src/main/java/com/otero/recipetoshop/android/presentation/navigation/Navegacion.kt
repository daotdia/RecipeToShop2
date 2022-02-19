package com.otero.recipetoshop.android.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.otero.recipetoshop.android.presentation.components.despensa.AlimentosDespensaLista
import com.otero.recipetoshop.android.presentation.components.cestacompra.ListaCestasCompra
import com.otero.recipetoshop.android.presentation.components.cestacompra.ContenidoCestaCompra
import com.otero.recipetoshop.android.presentation.components.cestacompra.ContenidoReceta
import com.otero.recipetoshop.android.presentation.components.cestacompra.contenidocesta.recetas.busquedarecetas.BusquedaRecetasScreen
import com.otero.recipetoshop.android.presentation.controllers.despensa.DespensaViewModel
import com.otero.recipetoshop.android.presentation.controllers.cestacompra.ListaCestasCompraListViewModel
import com.otero.recipetoshop.android.presentation.controllers.cestacompra.CestaCompraViewModel
import com.otero.recipetoshop.android.presentation.controllers.cestacompra.recetas.BusquedaRecetaAPIViewModel
import com.otero.recipetoshop.android.presentation.controllers.cestacompra.recetas.RecetaViewModel
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor

@ExperimentalFoundationApi
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
//            CestaCompra(
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
            val viewModel: DespensaViewModel = viewModel("DespensaViewModel", factory )
            Surface(color = secondaryLightColor){
                AlimentosDespensaLista(
                    listState = viewModel.despensaState,
                    onTriggeEvent = viewModel::onTriggerEvent
                )
            }
        }

        //Pantalla de lista de listas de recetas.
        composable(
            route = RutasNavegacion.ListaCestasCompra.route
        ){navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val listaCestasCompraViewModel: ListaCestasCompraListViewModel = viewModel("ListaCestasCompraListViewModel", factory )
            Surface(color = secondaryLightColor){
                ListaCestasCompra(
                    navController = navController,
                    listaCestaCompraState = listaCestasCompraViewModel.listadelistarecetasstate,
                    onTriggeEvent = listaCestasCompraViewModel::onTriggerEvent,
                )
            }
        }

        //Pantalla de cesta de la compra.
        composable(
            route = RutasNavegacion.CestaCompra.route + "/{cestacompraid}",
            arguments = listOf(navArgument("cestacompraid"){
                type = NavType.IntType
            })
        ){ navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val cestaCompraViewModel: CestaCompraViewModel = viewModel("CestaCompraViewModel", factory )
            ContenidoCestaCompra(
                stateCestaCompra = cestaCompraViewModel.cestaCompraState,
                onTriggeEventCestaCompra = cestaCompraViewModel::onTriggerEvent,
                navController = navController
            )
        }

        //Pantalla dde búsqueda/creación de recetas.
        composable(
            route = RutasNavegacion.BusquedaRecetas.route + "/{cestacompraid}",
            arguments = listOf(navArgument("cestacompraid"){
                type = NavType.IntType
            })
        ){ navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val busquedaCreacionRecetaAPIViewModel: BusquedaRecetaAPIViewModel = viewModel("BusquedaRecetaAPIViewModel", factory )

            BusquedaRecetasScreen(
                busquedaCreacionRecetasAPIState = busquedaCreacionRecetaAPIViewModel.busquedaRecetasAPIState,
                onTriggeEventReceta = busquedaCreacionRecetaAPIViewModel::onTriggerEvent,
                navController = navController
            )
        }

        //Pantalla del contenido de la receta.
        composable(
            route = RutasNavegacion.ContenidoReceta.route + "/{cestacompraid}" +"/{recetaid}",
            arguments = listOf(
                navArgument("cestacompraid"){ type = NavType.IntType },
                navArgument("recetaid"){ type = NavType.IntType })
        ){ navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val recetaViewModel: RecetaViewModel = viewModel("RecetaViewModel", factory )

            ContenidoReceta(
                recetaState = recetaViewModel.recetaState,
                onTriggeEventReceta = recetaViewModel::onTriggerEvent,
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
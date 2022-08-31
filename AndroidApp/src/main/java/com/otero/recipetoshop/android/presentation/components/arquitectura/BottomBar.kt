package com.otero.recipetoshop.android.presentation.components.arquitectura

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.otero.recipetoshop.android.R
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.presentationlogic.util.menus.bottombar.MenuBottomItems
import java.lang.Exception
/*
Este es el componente UI de la barra de navegación inferior.
 */
@Composable
fun BottomBar(navController: NavHostController){
    BottomNavigation(
        backgroundColor = primaryDarkColor,
        ) {
        /*
        Los botones dependen de la clase Util de MenuBottomItems,
        es un tipo enum con la información necesaria para navegación y pintar
        cada icono.
         */
        MenuBottomItems.BottomItems.forEach{ bottomItem ->
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute != null) {
                BottomNavigationItem(
                    icon = {
                        when(bottomItem.icon){
                            "Despensa" -> {
                                Icon(
                                    Icons.Filled.Kitchen,
                                    modifier = Modifier
                                        .size(32.dp)
                                    ,
                                    tint = Color.White,
                                    contentDescription = bottomItem.label,
                                )
                            }
                            "Recetas" -> {
                                Icon(
                                    Icons.Filled.MenuBook,
                                    modifier = Modifier
                                        .size(32.dp)
                                    ,
                                    tint = Color.White,
                                    contentDescription = bottomItem.label,
                                )
                            }
                            "ListaCompra" -> {
                                Icon(
                                    Icons.Filled.ShoppingCart,
                                    modifier = Modifier
                                        .size(32.dp)
                                    ,
                                    tint = Color.White,
                                    contentDescription = bottomItem.label,
                                )
                            }
                            else -> {
                                throw Exception("No se ha encontrado el icono del bottom item")
                            }

                        }
                    },
                    selected = currentRoute.contains(bottomItem.route),
                    label = {
                        Text(text = bottomItem.label)
                    },
                    onClick = {
                        navController.navigate(bottomItem.route)
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}



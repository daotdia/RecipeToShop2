package com.otero.recipetoshop.android.presentation.components.arquitectura

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

@Composable
fun BottomBar(navController: NavHostController){
    BottomNavigation(
        backgroundColor = primaryDarkColor,
        ) {
        MenuBottomItems.BottomItems.forEach{ bottomItem ->

            val painter: Painter = when(bottomItem.icon){
                "Despensa" -> {
                    painterResource(id = R.drawable.ic_despensa)
                }
                "Recetas" -> {
                    painterResource(id = R.drawable.ic_recetas)
                }
                "ListaCompra" -> {
                    painterResource(id = R.drawable.ic_listacompra)
                }
                else -> {
                    throw Exception("No se ha encontrado el icono del bottom item")
                }

            }

            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavigationItem(
                icon = {
                    Image(
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp),
                        painter = painter,
                        contentDescription = bottomItem.label,
                        contentScale = ContentScale.Crop
                    )
                },
                selected = currentRoute == bottomItem.route,
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

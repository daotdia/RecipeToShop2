package com.otero.recipetoshop.android.presentation.components.arquitectura

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.otero.recipetoshop.android.presentation.navigation.Navegacion
import com.otero.recipetoshop.android.presentation.theme.analogousColorBlue
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.primaryLightColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import kotlinx.coroutines.launch
/*
Este es la raíz de la UI de la app.
Se encuentra la estructura golbal de tipo Scaffold.
 */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun ScaffoldApp(){
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    Surface(color = secondaryLightColor) {
        Scaffold(
            topBar = {
                TopBar(
                    onMenuClicked = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomBar(navController = navController)
            },
            content = { padding ->
                Navegacion(
                    navController = navController,
                    padding = padding
                )
            },
            scaffoldState = scaffoldState,
            drawerContent = {
                LeftDrawer(navController = navController)
            }
        )
    }
}
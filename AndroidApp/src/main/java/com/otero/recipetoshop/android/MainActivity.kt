package com.otero.recipetoshop.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.otero.recipetoshop.android.presentation.components.arquitectura.BottomBar
import com.otero.recipetoshop.android.presentation.components.arquitectura.ScaffoldApp
import com.otero.recipetoshop.android.presentation.components.arquitectura.TopBar
import com.otero.recipetoshop.android.presentation.navigation.Navegacion
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.datasource.network.KtorClientFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ktorClient = KtorClientFactory().build()

        setContent{
          ScaffoldApp()
        }
    }
}

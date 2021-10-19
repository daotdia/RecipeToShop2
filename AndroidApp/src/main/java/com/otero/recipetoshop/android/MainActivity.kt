package com.otero.recipetoshop.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.otero.recipetoshop.android.presentation.navigation.Navegacion
import com.otero.recipetoshop.datasource.network.KtorClientFactory
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ktorClient = KtorClientFactory().build()

        setContent{
           Navegacion()
        }
    }
}

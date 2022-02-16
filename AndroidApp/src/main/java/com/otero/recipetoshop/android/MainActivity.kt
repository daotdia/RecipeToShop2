package com.otero.recipetoshop.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.otero.recipetoshop.android.presentation.components.arquitectura.ScaffoldApp
import com.otero.recipetoshop.datasource.network.KtorClientFactory
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalFoundationApi
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

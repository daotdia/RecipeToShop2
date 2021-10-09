package com.otero.recipetoshop.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.otero.recipetoshop.android.presentation.navigation.Navegacion


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
           Navegacion()
        }
    }
}

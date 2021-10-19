package com.otero.recipetoshop.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.otero.recipetoshop.android.dependenceinjection.Prueba
import com.otero.recipetoshop.android.presentation.navigation.Navegacion
import com.otero.recipetoshop.datasource.network.KtorClientFactory
import com.otero.recipetoshop.datasource.network.model.RecipeDto
import com.otero.recipetoshop.datasource.network.toRecipe
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

const val BASE_URL = "https://recipesapi.herokuapp.com/api/get?rId="
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ktorClient = KtorClientFactory().build()

        CoroutineScope(IO).launch {
            val recipeId = 41470
            val recipe = ktorClient.get<RecipeDto>{
                url("$BASE_URL$recipeId")
            }.toRecipe()
            println("KtorTest titulo: ${recipe.title}")
        }

        setContent{
           Navegacion()
        }
    }
}

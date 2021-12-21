package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.datasource.network.model.parsers.toRecipeList
import com.otero.recipetoshop.datasource.network.model.parsers.toRecipesList
import com.otero.recipetoshop.datasource.network.model.yummlyDTO.YummlySearchResponseDTO
import com.otero.recipetoshop.domain.model.Recipe
import com.otero.recipetoshop.domain.model.recetas.YummlyRecipe
import io.ktor.client.*
import io.ktor.client.request.*

class RecipeServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
): RecipeService {
    override suspend fun search(maxItems: Int, offset: Int, maxSeconds: Int, query: String): List<YummlyRecipe> {
        println("Realizo la búsqueda")
        return httpClient.get<YummlySearchResponseDTO>{
            headers{
                append("x-rapidapi-host", "yummly2.p.rapidapi.com")
                append("x-rapidapi-key", "f23925d5bbmsh95550bd56727c48p1eea81jsn364692bc7d7d")
            }
            url("https://yummly2.p.rapidapi.com/feeds/search?maxResult=${maxItems}&start${offset}=&q=${query}&maxTotalTimeInSeconds=${maxSeconds}")
        }.feed.toRecipesList()
    }

    companion object{
        const val BASE_URL = "https://yummly2.p.rapidapi.com/feeds/search?"
        const val MAXITEMSFORQUERY = 30
    }
}
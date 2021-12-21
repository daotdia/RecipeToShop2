package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.datasource.network.model.Antiguo.RecipeSearchResponse
import com.otero.recipetoshop.domain.model.Recipe
import io.ktor.client.*
import io.ktor.client.request.*

class RecipeServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
): RecipeService {
    override suspend fun search(maxItems: Int, offset: Int, maxSeconds: Int, query: String): List<Recipe> {
        return httpClient.get<RecipeSearchResponse>{
            headers{
                append("x-rapidapi-host", "yummly2.p.rapidapi.com")
                append("x-rapidapi-key", "f23925d5bbmsh95550bd56727c48p1eea81jsn364692bc7d7d")
            }
            url("https://yummly2.p.rapidapi.com/feeds/search?maxResult=${maxItems}&start${offset}=&q=${query}&maxTotalTimeInSeconds=${maxSeconds}")
        }.recipes.toRecipeList()
    }

    companion object{
        const val BASE_URL = "https://yummly2.p.rapidapi.com/feeds/search?"
        const val MAXITEMSFORQUERY = 30
    }
}
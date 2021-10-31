package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.datasource.network.model.RecipeDto
import com.otero.recipetoshop.datasource.network.model.RecipeSearchResponse
import com.otero.recipetoshop.domain.model.Recipe
import io.ktor.client.*
import io.ktor.client.request.*

class RecipeServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
): RecipeService {
    override suspend fun search(page: Int, query: String): List<Recipe> {
        return httpClient.get<RecipeSearchResponse>{
            url("$baseUrl/v2/recipes?q=$query&page=$page")
        }.recipes.toRecipeList()
    }

    override suspend fun get(id: Int): Recipe {
        return httpClient.get<RecipeDto>{
            url("$baseUrl/get?rId=$id")
        }.toRecipe()
    }

    companion object{
        const val BASE_URL = "https://recipesapi.herokuapp.com/api"
        const val RECIPE_PAGINATION_PAGE_SIZE = 30
    }
}
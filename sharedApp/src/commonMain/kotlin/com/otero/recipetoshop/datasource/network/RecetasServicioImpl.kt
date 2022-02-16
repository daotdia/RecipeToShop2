package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.datasource.network.model.parsers.toRecipesList
import com.otero.recipetoshop.datasource.network.model.yummlyDTO.YummlySearchResponseDTO
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.model.CestaCompra.YummlyRecipestoRecipeList
import io.ktor.client.*
import io.ktor.client.request.*

class RecetasServicioImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String,
): RecetasServicio {
    override suspend fun search(maxItems: Int, offset: Int, maxSeconds: Int, query: String): List<Receta> {
        println("Realizo la búsqueda")
        return httpClient.get<YummlySearchResponseDTO>{
            headers{
                append("x-rapidapi-host", "yummly2.p.rapidapi.com")
                append("x-rapidapi-key", "f23925d5bbmsh95550bd56727c48p1eea81jsn364692bc7d7d")
            }
            url("https://yummly2.p.rapidapi.com/feeds/search?maxResult=${maxItems}&start${offset}=&q=${query}&maxTotalTimeInSeconds=${maxSeconds}")
        }.feed.toRecipesList().YummlyRecipestoRecipeList()
    }

    companion object{
        const val BASE_URL = "https://yummly2.p.rapidapi.com/feeds/search?"
        const val MAXITEMSFORQUERY = 30
    }
}
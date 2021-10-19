package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.datasource.network.KtorClientFactory
import com.otero.recipetoshop.datasource.network.RecipeService
import com.otero.recipetoshop.datasource.network.RecipeServiceImpl
import com.otero.recipetoshop.datasource.network.RecipeServiceImpl.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory().build()
    }

    @Singleton
    @Provides
    fun provideRecipeService(httpClient: HttpClient): RecipeService{
        return RecipeServiceImpl(
            httpClient = httpClient,
            baseUrl = BASE_URL
        )
    }
}
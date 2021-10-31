package com.otero.recipetoshop.datasource.cache

import com.otero.recipetoshop.datasource.network.RecipeServiceImpl.Companion.RECIPE_PAGINATION_PAGE_SIZE
import com.otero.recipetoshop.domain.model.Recipe

class RecipeCacheImpl(
    private val recipeDataBase: RecipeDataBase
): RecipeCache{

    private val queries: RecipesDbQueries = recipeDataBase.recipesDbQueries
    override fun insert(recipe: Recipe) {
        queries.insertRecipe(
            id = recipe.id.toLong(),
            title = recipe.title,
            publisher = recipe.publisher,
            rating = recipe.rating.toLong(),
            source_url = recipe.sourceURL,
            //SqlDelight no puede guardar listas, las guarda en String, hay que transofrmarlas.
            ingredients = recipe.ingredients.convertIngredientListToString(),
            date_added = 1000.0,
            date_updated = 1000.0,
            featured_image = recipe.image
        )
    }

    override fun insert(recipes: List<Recipe>) {
       for(recipe in recipes){
           insert(recipe)
       }
    }

    override fun search(query: String, page: Int): List<Recipe> {
        return queries.searchRecipes(
            query = query,
            pageSize =RECIPE_PAGINATION_PAGE_SIZE.toLong(),
            offset = (page - 1) * RECIPE_PAGINATION_PAGE_SIZE.toLong()
        ).executeAsList().toRecipeList() // Faltan convertir las entidades de recetas a recetas.
    }

    override fun getAll(page: Int): List<Recipe> {
        return queries.getAllRecipes(
            pageSize =RECIPE_PAGINATION_PAGE_SIZE.toLong(),
            offset = (page - 1) * RECIPE_PAGINATION_PAGE_SIZE.toLong()
        ).executeAsList().toRecipeList() // Faltan convertir las entidades de recetas a recetas.
    }

    override fun get(recipeId: Int): Recipe? {
        return try {
            queries.getRecipeById(id = recipeId.toLong()).executeAsOne().toRecipe() // Faltan convertir las entidades de recetas a recetas.
        }catch (e: NullPointerException){
            println("No se ha podido obtener la receta con el id")
            null
        }
    }
}
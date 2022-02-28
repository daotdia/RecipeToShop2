package com.otero.recipetoshop.domain.model.CestaCompra

import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.TipoUnidad
/*
Clase necesaria para parsear la receta (bastante compleja) que llega de Yummly.
 */
data class YummlyRecipe(
    val recipeType: String?,
    val content: YumlyContentRecipe,
    val metaRecipe: YummlyMetaRecipe,
    val metaSearchrecipe: YummlyRecipeMetaSearch
){
    data class YumlyContentRecipe(
        val details: YummlyRecipeContentDetails,
        val ingredientsObject: List<YumlyRecipeContentIngredients>,
        val reviewsObject: YummlyRecipeContentReviews,
        val contentNutritionObject: YummlyRecipeContentNutrition
    ){
        data class YummlyRecipeContentDetails(
            val url: String?,
            val cookTime: String?,
            val detailsImageContenedor: List<YummlyRecipeContentDetailsImageSource>,
            val nombre: String?,
            val recipeId: String?,
            val comensales: Int?,
            val globalId: String?,
        ){
            data class YummlyRecipeContentDetailsImageSource(
                val imageUrl: String?
            )
        }

        data class YumlyRecipeContentIngredients(
            val ingredientCategory: String?,
            val ingredientAmountObject: YumlyRecipeContentIngredientAmount,
            val ingredientName: String?,
            val ingredientId: String?,
        ){
            data class YumlyRecipeContentIngredientAmount(
                val medidaSI: YumlyRecipeContentIngredientAmountSI
            ){
                data class YumlyRecipeContentIngredientAmountSI(
                    val metricSIDetails: YumlyRecipeContentIngredientAmountSIDetails,
                    val metricAmount: Float?
                ){
                    data class YumlyRecipeContentIngredientAmountSIDetails(
                        val metricName: String?,
                        val metricType: String?,
                        val isDecimal: Boolean?
                    )
                }
            }
        }
        data class YummlyRecipeContentReviews(
            val numReviews: Int?,
            val ratingReviews: Float?,
        )
        data class YummlyRecipeContentNutrition(
            val nutritionRecipeEstimates: List<YummlyRecipeContentNutritionEstimates>
        ){
            data class YummlyRecipeContentNutritionEstimates(
                val nameNutritionAtributeRecipe: String?,
                val valueNutritionAtributeRecipe: Float?,
                val unitObjectValueNutritionAtributeRecipe: YummlyRecipeContentNutritionEstimatesMetric
            ){
                data class YummlyRecipeContentNutritionEstimatesMetric(
                    val unitNameValueNutritionAtributeRecipe: String?,
                    val unitSiglaValueNutritionAtributeRecipe: String?,
                )
            }
        }
    }
    data class YummlyMetaRecipe(
        val sourceRecipeObject: YummlyRecipeMetaSource
    ){
        data class YummlyRecipeMetaSource(
            val sourceRecipe: String?
        )
    }
    data class YummlyRecipeMetaSearch(
        val metaSearchRecipeScore: Float?
    )
    
    fun YummlyIngredientsToFood(ojetoIngredientes: List<YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients>): List<Alimento>{
        val ingredientes: ArrayList<Alimento> = ArrayList()
        for(ingredient in content.ingredientsObject){
            ingredientes.add(Alimento(
                nombre = if(ingredient.ingredientName != null) ingredient.ingredientName else "Ingrediente Sin Nombre",
                cantidad = if(ingredient.ingredientAmountObject.medidaSI.metricAmount != null) ingredient.ingredientAmountObject.medidaSI.metricAmount.toInt() else 0,
                //PENDIENTE HACER PARSE DE UNIDADES
                tipoUnidad = TipoUnidad.GRAMOS,
                active = true
                ))
        }
        return ingredientes
    }

    fun YummlyRecipetoRecipeList(): Receta{
        return Receta(
            id_cestaCompra = -1,
            nombre = if(content.details.nombre != null) content.details.nombre else "Receta Sin Nombre",
            cantidad = -1,
            user = false,
            active = true,
            imagenSource = if(content.details.detailsImageContenedor.isNotEmpty()) content.details.detailsImageContenedor.first().imageUrl else "Imagen No Disponible",
            //NO OLVIDAR ACTUALIZAR ID INGREDIENTES CUANDO EL ID DE LA RECETA SE CONOZCA
            ingredientes = if(content.ingredientsObject.isNotEmpty()) YummlyIngredientsToFood(content.ingredientsObject) else emptyList(),
            rating = content.reviewsObject.ratingReviews
        )
    }
}

fun List<YummlyRecipe>.YummlyRecipestoRecipeList(): List<Receta>{
    return map { it.YummlyRecipetoRecipeList() }
}
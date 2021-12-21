package com.otero.recipetoshop.domain.model.recetas

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
}
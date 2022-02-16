package com.otero.recipetoshop.datasource.network.model.parsers

import com.otero.recipetoshop.datasource.network.model.yummlyDTO.*
import com.otero.recipetoshop.domain.model.CestaCompra.YummlyRecipe

val mapParser: HashMap<String, Any> = hashMapOf()

fun YummlySearchResponseDTO.toRecipeList(): List<YummlyRecipe> {
    return feed.toRecipesList()
}

fun YummlyRecipeDTO.toRecipeList(): YummlyRecipe{
    return YummlyRecipe(
        recipeType = recipeType,
        content = content.toRecipeList(),
        metaRecipe = metaRecipe.toRecipeList(),
        metaSearchrecipe = metaSearchRecipe.toRecipeList()
    )
}

fun List<YummlyRecipeDTO>.toRecipesList(): List<YummlyRecipe>{
    return map { it.toRecipeList() }
}

fun YummlyRecipeContentDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe {
    return YummlyRecipe.YumlyContentRecipe(
        details = details.toRecipeList(),
        ingredientsObject = ingredientsObject.toRecipesIngredientList(),
        reviewsObject = reviewsObject.toRecipeList(),
        contentNutritionObject = contentNutritionObject.toRecipeList()
    )
}

fun YummlyRecipeContentDetailsDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentDetails {
    return YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentDetails(
        url = url,
        cookTime = cookTime,
        detailsImageContenedor = detailsImageContenedor.toRecipesDetailList(),
        nombre = nombre,
        recipeId = recipeId,
        comensales = comensales,
        globalId = globalId
    )
}

fun YummlyRecipeContentDetailsImageSourceDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentDetails.YummlyRecipeContentDetailsImageSource{
    return YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentDetails.YummlyRecipeContentDetailsImageSource(
        imageUrl = imageUrl
    )
}

fun List<YummlyRecipeContentDetailsImageSourceDTO>.toRecipesDetailList(): List<YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentDetails.YummlyRecipeContentDetailsImageSource> {
    return map { it.toRecipeList() }
}

fun YumlyRecipeContentIngredientsDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients{
    return YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients(
        ingredientCategory = ingredientCategory,
        ingredientAmountObject = ingredientAmountObject.toRecipeList(),
        ingredientName = ingredientName,
        ingredientId = ingredientId,
    )
}

fun YumlyRecipeContentIngredientAmountDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients.YumlyRecipeContentIngredientAmount {
    return YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients.YumlyRecipeContentIngredientAmount(
        medidaSI = medidaSI.toRecipeList()
    )
}

fun YumlyRecipeContentIngredientAmountSIDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients.YumlyRecipeContentIngredientAmount.YumlyRecipeContentIngredientAmountSI {
    return YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients.YumlyRecipeContentIngredientAmount.YumlyRecipeContentIngredientAmountSI(
        metricAmount = metricAmount,
        metricSIDetails = metricSIDetails.toRecipeList()
    )
}

fun YumlyRecipeContentIngredientAmountSIDetailsDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients.YumlyRecipeContentIngredientAmount.YumlyRecipeContentIngredientAmountSI.YumlyRecipeContentIngredientAmountSIDetails {
    return YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients.YumlyRecipeContentIngredientAmount.YumlyRecipeContentIngredientAmountSI.YumlyRecipeContentIngredientAmountSIDetails(
        metricName = metricName,
        metricType = metricType,
        isDecimal = isDecimal
    )
}

fun List<YumlyRecipeContentIngredientsDTO>.toRecipesIngredientList(): List<YummlyRecipe.YumlyContentRecipe.YumlyRecipeContentIngredients>{
    return map { it.toRecipeList() }
}

fun YummlyRecipeContentReviewsDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentReviews{
    return YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentReviews(
        numReviews = numReviews,
        ratingReviews = ratingReviews
    )
}

fun YummlyRecipeContentNutritionDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition{
    return YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition(
        nutritionRecipeEstimates = nutritionRecipeEstimates.toRecipesNutritionList()
    )
}

fun YummlyRecipeContentNutritionEstimatesDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition.YummlyRecipeContentNutritionEstimates{
    return YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition.YummlyRecipeContentNutritionEstimates(
        nameNutritionAtributeRecipe = nameNutritionAtributeRecipe,
        valueNutritionAtributeRecipe = valueNutritionAtributeRecipe,
        unitObjectValueNutritionAtributeRecipe = unitObjectValueNutritionAtributeRecipe.toRecipeList()
    )
}

fun YummlyRecipeContentNutritionEstimatesMetricDTO.toRecipeList(): YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition.YummlyRecipeContentNutritionEstimates.YummlyRecipeContentNutritionEstimatesMetric {
    return YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition.YummlyRecipeContentNutritionEstimates.YummlyRecipeContentNutritionEstimatesMetric(
        unitNameValueNutritionAtributeRecipe = unitNameValueNutritionAtributeRecipe,
        unitSiglaValueNutritionAtributeRecipe = unitSiglaValueNutritionAtributeRecipe
    )
}

fun List<YummlyRecipeContentNutritionEstimatesDTO>.toRecipesNutritionList(): List<YummlyRecipe.YumlyContentRecipe.YummlyRecipeContentNutrition.YummlyRecipeContentNutritionEstimates>{
    return map { it.toRecipeList() }
}

fun YummlyRecipeMetaDTO.toRecipeList(): YummlyRecipe.YummlyMetaRecipe{
    return YummlyRecipe.YummlyMetaRecipe(
        sourceRecipeObject = sourceRecipeObject.toRecipeList()
    )
}

fun YummlyRecipeMetaSourceDTO.toRecipeList(): YummlyRecipe.YummlyMetaRecipe.YummlyRecipeMetaSource{
    return YummlyRecipe.YummlyMetaRecipe.YummlyRecipeMetaSource(
        sourceRecipe = sourceRecipe
    )
}

fun YummlyRecipeMetaSearchDTO.toRecipeList(): YummlyRecipe.YummlyRecipeMetaSearch{
    return YummlyRecipe.YummlyRecipeMetaSearch(
        metaSearchRecipeScore = metaSearchRecipeScore
    )
}


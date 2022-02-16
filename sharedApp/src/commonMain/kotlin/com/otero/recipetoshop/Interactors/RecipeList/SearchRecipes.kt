package com.otero.recipetoshop.Interactors.RecipeList

//class SearchRecipes (
//    private val recipeService: RecetasServicio,
//        ) {
//    fun execute(
//        page: Int,
//        query: String,
//    ): Flow<DataState<List<Recipe>>> = flow{
//        //Para emitir loading, es lo que de primeras hace el Flow.
//        emit(DataState.loading())
//
//        //Para emitir las recetas coincidentes, una vez comience a emitir el loading es false.
//        try {
//            val recipes = recipeService.search(
//                page = page,
//                query = query
//            )
//
//            delay(500)
//
//            if(query == "error"){
//                throw Exception("Es una forzado de error para probar la cola de errorres")
//            }
//////            recipeCache.insert(recipes)
//////
//////            println("Lista de recetas obtenidas de network: " + recipes)
//////            val cacheResult = if(query.isBlank()){
//////                //Obtengo todas las recetas en cache, sino se está buscando nada se muestran las recetas que se hanbuscao hasta le momento y que están en cache.
//////                recipeCache.getAll(page = page)
//////            }else{
//////                recipeCache.search(
//////                    query = query,
//////                    page = page
//////                )
//////            }
////            println("Lista de recetas guardadas en la network " + cacheResult)
////            emit(DataState.data(message = null, data = cacheResult))
//        } catch (e: Exception){
//            //Para emitir error
//            emit(DataState.error<List<Recipe>>(
//                message = GenericMessageInfo.Builder()
//                    .id("SearchRecipes Error")
//                    .title("Error")
//                    .uiComponentType(UIComponentType.Dialog)
//                    .description(e.message?: "Unknown Error")
//                    .positive(
//                        PositiveAction(
//                            positiveBtnTxt = "OK",
//                            onPositiveAction = {
//                                //Que hacer al cancelar el error.
//                            }
//                        )
//                    )
//                    .negative(
//                        NegativeAction(
//                            negativeBtnTxt = "Cancel",
//                            onNegativeAction = {
//                                //Que nhacer al cancelar el error
//                            }
//                        )
//                    )
//            ))
//        }
//    }
//}
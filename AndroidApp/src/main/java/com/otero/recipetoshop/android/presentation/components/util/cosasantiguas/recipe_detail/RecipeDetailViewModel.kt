package com.otero.recipetoshop.android.presentation.components.util.cosasantiguas.recipe_detail

//@HiltViewModel
//class RecipeDetailViewModel
//@Inject
//constructor(
//    private val savedStateHandle: SavedStateHandle,
//    //private val getRecipe: GetRecipe
//):ViewModel()
//{
//    val recipe: MutableState<Recipe?> = mutableStateOf(null)
//    init {
//        try {
//            savedStateHandle.get<Int>("recipeId")?.let{ recipeId ->
//                viewModelScope.launch {
//                   getRecipe(recipeid = 0)// En cacherecipe sólo está la última receta de la búsqueda de pollo porque las id de la API no tienen sentido y se sobrescriben todas como 0
//                }
//            }
//        } catch (e: Exception){
//            println("Algo salió mal")
//        }
//    }
//
//    private fun getRecipe(recipeid: Int){
//        getRecipe.execute(recipeid = recipeid).onEach { dataState ->
//            println("RecipeDetailVM: ${dataState.isLoading}")
//
//            dataState.data?.let { recipe ->
//                println("RecipeDetailVM: ${recipe}")
//                this.recipe.value = recipe
//            }
//
//            dataState.message?.let { message ->
//                println("RecipeDetailVM: ${message}")
//            }
//        }.launchIn(viewModelScope)
//    }
//}

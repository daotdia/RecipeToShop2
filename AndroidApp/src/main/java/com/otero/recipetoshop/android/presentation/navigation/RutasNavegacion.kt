package com.otero.recipetoshop.android.presentation.navigation

sealed class RutasNavegacion (val route: String)
{
    object RecipeList: RutasNavegacion("recipeList")

    object RecipeDetail: RutasNavegacion("recipeDetail")

    object Despensa: RutasNavegacion("despensa")

    object ListaDeListaRecetas: RutasNavegacion("listadelistarecetas")

    object ListaRecetas: RutasNavegacion("listareceta")

    object ListaCompra: RutasNavegacion("listacompra")

    object Perfil: RutasNavegacion("perfil")

    object FAQs: RutasNavegacion("faqs")

    object Contacto: RutasNavegacion("contacto")
}

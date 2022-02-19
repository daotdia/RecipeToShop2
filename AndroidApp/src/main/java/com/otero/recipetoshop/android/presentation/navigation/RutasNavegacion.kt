package com.otero.recipetoshop.android.presentation.navigation

sealed class RutasNavegacion (val route: String)
{
    object RecipeList: RutasNavegacion("recipeList")

    object RecipeDetail: RutasNavegacion("recipeDetail")

    object Despensa: RutasNavegacion("despensa")

    object ListaCestasCompra: RutasNavegacion("listaCestasCompra")

    object CestaCompra: RutasNavegacion("cestaCompra")

    object BusquedaRecetas: RutasNavegacion("busquedarecetas")

    object ListaCompra: RutasNavegacion("listacompra")

    object ContenidoReceta: RutasNavegacion("contenidoreceta")

    object Perfil: RutasNavegacion("perfil")

    object FAQs: RutasNavegacion("faqs")

    object Contacto: RutasNavegacion("contacto")
}

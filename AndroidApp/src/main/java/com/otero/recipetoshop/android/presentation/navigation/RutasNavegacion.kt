package com.otero.recipetoshop.android.presentation.navigation
/*
En esta clase se encuentran las rutas de navegación de la APP.
Siempre se pueden añadir más.
 */
sealed class RutasNavegacion (val route: String)
{
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

package com.otero.recipetoshop.domain.util

enum class SupermercadosEnum {
    CARREFOUR, DIA, MERCADONA;

    companion object {
        fun parseString(nombre: String): SupermercadosEnum{
            when(nombre.lowercase().trim()) {
                "carrefour" ->
                    return CARREFOUR
                "dia" ->
                    return DIA
                "mercadona" ->
                    return MERCADONA
            }
            return CARREFOUR
        }

        fun getImage(supermercado: SupermercadosEnum): String {
            when(supermercado){
                CARREFOUR ->
                    return "https://vams-loyalia-storage.s3.eu-west-1.amazonaws.com/images/deals/_720x495/carrefour.jpg"
                DIA ->
                    return "https://marketing4ecommerce.net/wp-content/uploads/2015/10/supermercado-dia-1.jpg"
                MERCADONA ->
                    return "https://www.mercadona.com/estaticos/images/mercadona_logo/es/mercadona-imagotipo-color-300.png"
            }
        }
    }
}


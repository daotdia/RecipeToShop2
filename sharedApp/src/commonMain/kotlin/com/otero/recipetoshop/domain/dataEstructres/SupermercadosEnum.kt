package com.otero.recipetoshop.domain.dataEstructres

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
                    return "https://thefoodtech.com/wp-content/uploads/2020/12/supermercados-dia-ofrece-espectaculo-para-consumidores.jpg"
                MERCADONA ->
                    return "https://1000marcas.net/wp-content/uploads/2021/09/Mercadona-Logo.png"
            }
        }
    }
}


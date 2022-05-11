package com.otero.recipetoshop.domain.util

enum class TipoUnidad{
    GRAMOS, ML,UNIDADES;

    companion object {
        fun parseAbrev(tipoUnidad: TipoUnidad): String{
            if(tipoUnidad.name.equals("GRAMOS")){
                return "Gr"
            } else if(tipoUnidad.name.equals("ML")){
                return "Ml."
            } else if(tipoUnidad.name.equals("UNIDADES")){
                return "Ud."
            } else{
                return "Gr."
            }
        }

        fun parseTipoUnidad(nombre: String): TipoUnidad {
            TipoUnidad.values().forEach { it ->
                if(nombre.equals(it.name)){
                    return it
                }
            }
            return TipoUnidad.GRAMOS
        }
    }
}



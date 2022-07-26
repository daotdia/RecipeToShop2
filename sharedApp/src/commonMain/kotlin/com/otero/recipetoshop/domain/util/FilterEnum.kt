package com.otero.recipetoshop.domain.util

enum class FilterEnum {
    BARATOS, LIGEROS, AJUSTADOS, UNICOBARATO, UNICOLIGERO, UNICOAJUSTADO;

    companion object {
        fun parseString(nombre_filter: String): FilterEnum {
            when(nombre_filter.trim().lowercase()){
                "baratos", "más baratos"->
                    return BARATOS
                "ligeros", "más ligeros"->
                    return LIGEROS
                "más baratos ajustados" ->
                    return AJUSTADOS
                "supermercado más barato" ->
                    return UNICOBARATO
                "supermercado más ligero" ->
                    return UNICOLIGERO
                "supermercado más ajustado" ->
                    return UNICOAJUSTADO
                else ->
                    return BARATOS
            }
        }
    }
}
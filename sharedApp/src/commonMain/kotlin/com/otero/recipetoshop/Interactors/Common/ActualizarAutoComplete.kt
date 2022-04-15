package com.otero.recipetoshop.Interactors.Common

import com.otero.recipetoshop.datasource.static.Alimentos


class ActualizarAutoComplete {
    fun actualizarAutoComplete(
        query: String
    ): List<String>{
        if(query.isNotEmpty() || query.isNotBlank()){
            val alimentos = Alimentos.alimentos()
            val resultadoBruto =  alimentos.filter{
                it.startsWith(query.lowercase())
            }
            val resultadoFiltrado = resultadoBruto.sorted().take(5)

            return resultadoFiltrado
        } else{
            return emptyList()
        }
    }
}
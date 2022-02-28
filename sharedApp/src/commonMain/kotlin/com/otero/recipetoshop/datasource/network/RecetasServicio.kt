package com.otero.recipetoshop.datasource.network

import com.otero.recipetoshop.domain.model.CestaCompra.Receta
/*
La interfaz del servicio de recetas externo.
 */
interface RecetasServicio {

    suspend fun search(
        maxItems: Int,
        offset: Int,
        maxSeconds: Int,
        query: String,
    ): List<Receta>
}
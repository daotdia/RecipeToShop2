package com.otero.recipetoshop.domain.model
/*
Utilizado anteriormente, pero hay componentes antiguos que todavían la utilizan aunque no los utiice en la APP.
SERÁ ELIMINADA.
 */
data class Recipe(
    val id: Int,
    val title: String,
    val publisher: String,
    val image: String,
    val rating: Double,
    val sourceURL: String,
    val ingredients: List<String> = listOf(),
    val isfavorita: Boolean = false,
)

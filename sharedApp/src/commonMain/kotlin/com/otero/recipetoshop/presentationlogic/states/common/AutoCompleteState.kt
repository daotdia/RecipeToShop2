package com.otero.recipetoshop.presentationlogic.states.common

data class AutoCompleteState(
    val resultado: List<String> = listOf(),
){
    constructor(): this(
        resultado = listOf()
    )
}
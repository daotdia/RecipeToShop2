package com.otero.recipetoshop.domain.model
/*
Utilizado anteriormente, pero hay componentes antiguos que todavían la utilizan aunque no los utiice en la APP.
SERÁ ELIMINADA.
 */
sealed class UIComponentType {
    object Dialog: UIComponentType()

    object Snackbar: UIComponentType()

    object None: UIComponentType()
}
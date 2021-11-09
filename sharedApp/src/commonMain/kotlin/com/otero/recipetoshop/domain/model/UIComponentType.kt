package com.otero.recipetoshop.domain.model

sealed class UIComponentType {
    object Dialog: UIComponentType()

    object Snackbar: UIComponentType()

    object None: UIComponentType()
}
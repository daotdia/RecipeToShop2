package com.otero.recipetoshop.datasource.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver{
        return NativeSqliteDriver(RecipeDataBase.Schema, "recipes.db")
    }
}
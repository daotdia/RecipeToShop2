package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class AlimentosDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecipeToShopDB.Schema, "alimento.db")
    }
}
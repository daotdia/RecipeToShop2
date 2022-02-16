package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB

actual class AlimentosDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecipeToShopDB.Schema, "alimento.db")
    }
}
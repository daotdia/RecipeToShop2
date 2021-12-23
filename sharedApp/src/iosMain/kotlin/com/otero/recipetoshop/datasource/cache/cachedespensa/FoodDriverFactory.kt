package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.datasource.cachedespensa.FoodDataBase

actual class FoodDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FoodDataBase.Schema, "food.db")
    }
}
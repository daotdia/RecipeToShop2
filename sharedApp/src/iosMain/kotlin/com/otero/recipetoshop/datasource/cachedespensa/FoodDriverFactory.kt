package com.otero.recipetoshop.datasource.cachedespensa

import com.otero.recipetoshop.datasource.cacherecipe.despensa.FoodDataBase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class FoodDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FoodDataBase.Schema, "food.db")
    }
}
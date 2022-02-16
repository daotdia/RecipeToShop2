package com.otero.recipetoshop.datasource.cache.cachedespensa

import android.content.Context
import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class AlimentosDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RecipeToShopDB.Schema, context, "alimento.db")
    }
}
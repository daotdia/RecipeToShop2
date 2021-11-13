package com.otero.recipetoshop.datasource.cachedespensa

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class FoodDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FoodDataBase.Schema, context, "food.db")
    }
}
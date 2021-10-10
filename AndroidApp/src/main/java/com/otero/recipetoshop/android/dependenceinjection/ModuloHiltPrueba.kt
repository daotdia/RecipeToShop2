package com.otero.recipetoshop.android.dependenceinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface Prueba {
    fun description(): String
}

class PruebaImpl (private val prueba: Int): Prueba {
    override fun description(): String {
        return "Esto devuelve 5 con Hilt de prueba: $prueba"
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ModuloHiltPrueba {
    @Singleton
    @Provides
    fun providePrueba(): Int {
        return 5
    }

    @Singleton
    @Provides
    fun providePruebaObjeto(prueba: Int): Prueba{
        return PruebaImpl(prueba = prueba)
    }
}
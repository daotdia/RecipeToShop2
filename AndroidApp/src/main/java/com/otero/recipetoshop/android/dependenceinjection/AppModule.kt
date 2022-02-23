package com.otero.recipetoshop.android.dependenceinjection

import android.content.Context
import com.otero.recipetoshop.android.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
    Este es el archivo raiz para gestionar la inyección de dependencias.
    Necesarion para utilizar Hilt en la aplicación.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication{
        return app as BaseApplication
    }
}
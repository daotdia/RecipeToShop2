package com.otero.recipetoshop.android.dependenceinjection

import com.otero.recipetoshop.Interactors.Common.ActualizarAutoComplete
import com.otero.recipetoshop.Interactors.cestascompra.AddNewCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.GetCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.PrintListaCestasCompra
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.cestascompra.recetas.busquedarecetas.BuscarRecetasAPI
import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.*
import com.otero.recipetoshop.Interactors.cestascompra.recetas.AddIngredienteReceta
import com.otero.recipetoshop.Interactors.cestascompra.recetas.DeleteIngredienteReceta
import com.otero.recipetoshop.Interactors.cestascompra.recetas.GetDatosReceta
import com.otero.recipetoshop.Interactors.cestascompra.recetas.GetRecetasFavoritas
import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.datasource.network.RecetasServicio
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/*
Aquí se encuentras las dependencias de los casos de uso; por supuesto necesitan
que se les pase los servicios de cahce/red anteriormente instanciados.
 */
@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {
    @Singleton
    @Provides
    fun provideOnClickFoodDespensa(
        despensaCache: DespensaCache
    ): OnCLickAlimento{
        return OnCLickAlimento(despensaCache = despensaCache)
    }
    @Singleton
    @Provides
    fun provideChangeCantidadFood(
        despensaCache: DespensaCache
    ): AlimentoCantidadChange{
        return AlimentoCantidadChange(despensaCache = despensaCache)
    }
    @Singleton
    @Provides
    fun provideInsertNewFoodItem(
        despensaCache: DespensaCache
    ): InsertNewAlimento{
        return InsertNewAlimento(despensaCache = despensaCache)
    }
    @Singleton
    @Provides
    fun provideGetFoods(
        despensaCache: DespensaCache
    ): GetAlimentos{
        return GetAlimentos(despensaCache = despensaCache)
    }
    @Singleton
    @Provides
    fun provideDeleteFoods(
        despensaCache: DespensaCache
    ): DeleteAlimentos{
        return DeleteAlimentos(despensaCache = despensaCache)
    }
    @Singleton
    @Provides
    fun provideDeleteFood(
        despensaCache: DespensaCache
    ): DeleteAlimento{
        return DeleteAlimento(despensaCache = despensaCache)
    }
    @Singleton
    @Provides
    fun providesAddNewListaReceta(
        recetaCache: RecetaCache
    ): AddNewCestaCompra {
        return AddNewCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesOnEnterListaDeRecetas(
        recetaCache: RecetaCache
    ): OnEnterCestaCompra {
        return OnEnterCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesPrintListaDeListasRecetas(
        recetaCache: RecetaCache
    ): PrintListaCestasCompra {
        return PrintListaCestasCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesAddRecetaListaRecetas(
        recetaCache: RecetaCache
    ): AddRecetaCestaCompra {
        return AddRecetaCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesGetListaReceta(
        recetaCache: RecetaCache
    ): GetCestaCompra {
        return GetCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun provideAddAlimentoListaRecetas(
        recetaCache: RecetaCache
    ): AddAlimentoCestaCompra{
        return AddAlimentoCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesDeleteAlimentoListaRecetas(
        recetaCache: RecetaCache
    ): DeleteAlimentoCestaCompra{
        return DeleteAlimentoCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesDeleteRecetaListaRecetas(
        recetaCache: RecetaCache
    ): DeleteRecetaCestaCompra{
        return DeleteRecetaCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesUpdateReceta(
        recetaCache: RecetaCache
    ): UpdateRecetaCestaCompra{
        return UpdateRecetaCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesUpdateAlimentoListaRecetas(
        recetaCache: RecetaCache
    ): UpdateAlimentoCestaCompra{
        return UpdateAlimentoCestaCompra(recetaCache = recetaCache)
    }
    @Singleton
    @Provides
    fun providesBuscarRecetas(
        recetaCache: RecetaCache,
        recetasServicio: RecetasServicio
    ): BuscarRecetasAPI{
        return BuscarRecetasAPI(recetaCache = recetaCache, recetasServicio = recetasServicio)
    }
    @Singleton
    @Provides
    fun provideAddIngredienteReceta(
        recetaCache: RecetaCache
    ): AddIngredienteReceta {
        return AddIngredienteReceta(
            recetaCache = recetaCache
        )
    }
    @Singleton
    @Provides
    fun provideGetIngredientesReceta(
        recetaCache: RecetaCache
    ): GetDatosReceta{
        return GetDatosReceta(
            recetaCache = recetaCache
        )
    }
    @Singleton
    @Provides
    fun provideDeleteIngredienteReceta(
        recetaCache: RecetaCache
    ): DeleteIngredienteReceta{
        return DeleteIngredienteReceta(
            recetaCache = recetaCache
        )
    }

    @Singleton
    @Provides
    fun provideGetRecetasFavoritas(
        recetaCache: RecetaCache
    ): GetRecetasFavoritas{
        return GetRecetasFavoritas(
            recetaCache = recetaCache
        )
    }

    @Singleton
    @Provides
    fun provideActualizarAutoCompletado(): ActualizarAutoComplete { return ActualizarAutoComplete() }
}
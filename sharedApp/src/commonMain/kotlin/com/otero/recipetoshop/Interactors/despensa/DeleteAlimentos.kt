package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.flow

class DeleteAlimentos (
    private val despensaCache: DespensaCache
) {
    fun deleteAlimentos(): CommonFLow<DataState<String>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Obtengo los alimentos actuales.
        val foods = despensaCache.getAllAlimentosDespensa()
        //Si hay recetas guardadas en cache.
        if(foods != null){
            despensaCache.removeAllAlimentosDespensa()
            emit(DataState.data(message = null, data = "Unit"))
        } else{
            //En caso contrario emito un error.
            emit(
                DataState.error<String>(
                    message = GenericMessageInfo.Builder()
                        .id("Error Actualizando Cantidad Alimento")
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description("Invalid Event")
                )
            )
        }
    }.asCommonFlow()
}
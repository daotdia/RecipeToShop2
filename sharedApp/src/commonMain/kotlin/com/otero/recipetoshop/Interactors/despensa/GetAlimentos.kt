package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAlimentos(
    private val despensaCache: DespensaCache
) {
    fun getAlimentos(): CommonFLow<DataState<List<Alimento>>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Obtengo los alimentos actuales.
        val alimentos: List<Alimento>? = despensaCache.getAllAlimentosDespensa()
        //Si hay recetas guardadas en cache.
        if(alimentos != null){
            // Emito el alimento ya seteado en cantidad
            emit(DataState.data(message = null, data = alimentos))
        } else{
            //En caso contrario emito un error.
            emit(
                DataState.error<List<Alimento>>(
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
package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAlimento (
    private val despensaCache: DespensaCache
) {
    fun deleteAlimento(
        alimento: Alimento
    ): Flow<DataState<Unit>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())
        //Obtengo los alimentos actuales.
        val foods = despensaCache.getAllAlimentosDespensa()
        //Si hay recetas guardadas en cache.
        if(foods != null){
            //Elimino el alimento de la lista.
            despensaCache.removeAlimentoDespensaById(alimento.id_alimento!!)
            emit(DataState.data(message = null, data = Unit))
        } else{
            //En caso contrario emito un error.
            emit(
                DataState.error<Unit>(
                    message = GenericMessageInfo.Builder()
                        .id("Error Actualizando Cantidad Alimento")
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description("Invalid Event")
                )
            )
        }
    }
}
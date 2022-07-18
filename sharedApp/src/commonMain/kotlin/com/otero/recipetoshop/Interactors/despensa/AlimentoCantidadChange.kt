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

class AlimentoCantidadChange(
    private val despensaCache: DespensaCache
) {
    fun updateCantidad(
        cantidad: String,
        alimento: Alimento
    ): CommonFLow<DataState<Alimento>> = flow{
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Actualizo la cantidad del alimento y emito el alimento nuevo en el caso de que éste exista claro.
        val currentAlimentos = despensaCache.getAllAlimentosDespensa()
        if(currentAlimentos != null){
            currentAlimentos.forEach { element ->
                if (element.nombre.equals(alimento.nombre)) {
                    //Seteo su nueva cantidad.
                    element.cantidad = cantidad.toInt()
                    //Guardo en cache el alimento con la cantidad modificada
                    despensaCache.insertarAlimentoDespensa(element)
                    // Emito el alimento ya seteado en cantidad
                    emit(DataState.data(message = null, data = element))
                }
            }
        } else{
            //En caso contrario emito un error.
            emit(DataState.error<Alimento>(
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
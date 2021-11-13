package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFoods(
    private val foodCache: DespensaCache
) {
    fun getFoods(): Flow<DataState<List<Food>>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Obtengo los alimentos actuales.
        val foods = foodCache.getAll()
        //Si hay recetas guardadas en cache.
        if(!foods.isEmpty()){
            // Emito el alimento ya seteado en cantidad
            emit(DataState.data(message = null,data = foods))
        } else{
            //En caso contrario emito un error.
            emit(
                DataState.error<List<Food>>(
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
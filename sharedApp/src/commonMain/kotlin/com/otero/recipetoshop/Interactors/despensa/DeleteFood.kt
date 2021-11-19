package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteFood (
    private val foodCache: DespensaCache
) {
    fun deleteFood(
       food: Food
    ): Flow<DataState<Unit>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())
        //Obtengo los alimentos actuales.
        val foods = foodCache.getAllFoodsDespensa()
        //Si hay recetas guardadas en cache.
        if(foods != null){
            //Elimino el alimento de la lista.
            foodCache.removeFoodDespensaById(food.id_food)
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
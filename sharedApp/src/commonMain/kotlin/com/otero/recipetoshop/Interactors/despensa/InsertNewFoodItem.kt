package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertNewFoodItem(
    private val foodCache: DespensaCache
) {
    fun insertFoodItem(
        food: Food
    ): Flow<DataState<Unit>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Compruebo que el alimento no se encuentre ya.
        var igual: Boolean = false
        val currentFoods = foodCache.getAllFoodsDespensa()
        if(currentFoods != null){
            currentFoods.forEach { element ->
                if (element.nombre.equals(food.nombre)) {
                    igual = true
                }
            }
        }
        //Si no se ha encontrado
        if(!igual){
            //Guardo en cache el alimento con la cantidad modificada
            foodCache.insertFoodDespensa(food)
            // Emito el alimento ya seteado en cantidad
            emit(DataState.data(message = null,data = Unit))
        } else{
            //En caso contrario emito un error.
            emit(DataState.error<Unit>(
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
package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangeCantidadFood(
    private val foodCache: DespensaCache
) {
    fun updateCantidad(
        cantidad: String,
        food: Food
    ): Flow<DataState<Food>> = flow{
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Actualizo la cantidad del alimento y emito el alimento nuevo en el caso de que éste exista claro.
        var alimento: Food? = null
        val currentFoods = foodCache.getAll()
        currentFoods.forEach { element ->
            if(element.nombre.equals(food.nombre)){
                alimento = element
            }
            //Seteo su nueva cantidad.
            alimento?.cantidad = cantidad.toInt()
        }
        if(alimento != null){
            //Guardo en cache el alimento con la cantidad modificada
                foodCache.insert(alimento!!)
            // Emito el alimento ya seteado en cantidad
            emit(DataState.data(message = null,data = alimento))
        } else{
            //En caso contrario emito un error.
            emit(DataState.error<Food>(
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
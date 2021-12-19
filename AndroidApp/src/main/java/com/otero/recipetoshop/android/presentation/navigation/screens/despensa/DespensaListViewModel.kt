package com.otero.recipetoshop.android.presentation.navigation.screens.despensa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.android.presentation.components.despensa.NewFoodPopUp
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentattion.screens.despensa.FoodListState
import com.otero.recipetoshop.presentattion.screens.despensa.FoodState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class FoodListViewModel
@Inject
constructor(
    private val changeCantidadFood: ChangeCantidadFood,
    private val insertNewFoodItem: InsertNewFoodItem,
    private val getFoods: GetFoods,
    private val deleteFoods: DeleteFoods,
    private val deleteFood: DeleteFood,
    private val onCLickFoodDespensa: OnCLickFoodDespensa
): ViewModel(){
    val listState: MutableState<FoodListState> = mutableStateOf(FoodListState())

    init{
        obtainFoodsCache()
    }

    fun onTriggerEvent(event: FoodListEvents){
        when(event){
            is FoodListEvents.onCantidadChange -> {
                updateAlimento(food = event.food, cantidad = event.cantidad, )
            }
            is FoodListEvents.onAddFood -> {
                val food = createFood(
                    nombre = event.nombre,
                    nombretipo = event.tipo,
                    cantidad = event.cantidad
                )
                insertFoodItem(food = food)
            }
            is FoodListEvents.onSelectedNestedMenuItem -> {
                if(event.option.equals("Eliminar Despensa")){
                    removeFoodsCache()
                }
            }
            is FoodListEvents.onFoodDelete -> {
                deleteFood(event.food)
            }
            is FoodListEvents.onClickFood -> {
                updateAlimento(food = event.alimento, active = event.active)
            }
            else -> {
                //Manejar los errores.
//                handleError(
//                    GenericMessageInfo.Builder()
//                        .id(UUID.randomUUID().toString())
//                        .title("Error")
//                        .uiComponentType(UIComponentType.Dialog)
//                        .description("Invalid Event")
//                )
            }
        }
    }

    private fun deleteFood(food: Food) {
        deleteFood.deleteFood(food = food).onEach { dataState ->
            dataState.data?.let {
                //Elimino de caché el alimento.
                //ELimino del estado actual el alimento
                println("Ha llegado hasta el modelo vista")
                val currentFoods = ArrayList(listState.value.allAlimentos)
                deleteFood.deleteFood(food)
                currentFoods.remove(food)
                listState.value = listState.value.copy(allAlimentos = currentFoods)
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)

    }

    private fun removeFoodsCache() {
        deleteFoods.deletFoods().onEach { dataState ->
            dataState.data?.let {
                listState.value = listState.value.copy(allAlimentos = listOf())
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun obtainFoodsCache() {
        getFoods.getFoods().onEach { dataState ->
            dataState.data?.let { foods ->
                listState.value = listState.value.copy(allAlimentos = foods)
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun insertFoodItem(food: Food){
        //Actualizo el estado del viewModel confoeme llegan daaStates asíncronos.
        insertNewFoodItem.insertFoodItem(
            food = food
        ).onEach { dataState ->
            dataState.data?.let { food ->
                rePrintFoods()
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    //Es importante que cada vez que se inserte un nuevo elemnto se refrescque la lista obteniendolo de cache parar así obteneer los items cons sus id (auotincrementados).
    private fun rePrintFoods() {
        getFoods.getFoods().onEach { dataState ->
            dataState.data?.let { currentAlimentos ->
                listState.value = listState.value.copy(allAlimentos = currentAlimentos)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateAlimento(cantidad: String? = null, food: Food, active: Boolean = true){
        if(cantidad != null){
            //Actualizo el estado del viewModel confoeme llegan daaStates asíncronos.
            changeCantidadFood.updateCantidad(
                cantidad = cantidad,
                food = food
            ).onEach { dataState ->
                dataState.data?.let { updatedFood ->
                    updateFood(updatedFood,food)
                }
                dataState.message?.let { message ->
                    //handleError(message)
                }
            }.launchIn(viewModelScope)
        } else {
            onCLickFoodDespensa.onCLickFoodDespensa(food = food, active = active).onEach { dataState ->
                dataState.data?.let { updatedFood ->
                    updateFood(updatedFood,food)
                }
            }
        }
    }

   private fun refreshFoods(currentFoods: List<Food>){
       listState.value = listState.value.copy(allAlimentos = listOf())
       listState.value = listState.value.copy(allAlimentos = currentFoods)
   }

    private fun updateFood(updatedFood: Food, food: Food) {
        val currentFoods = ArrayList(listState.value.allAlimentos)
        val currentIndex = currentFoods.indexOf(food)
        currentFoods.set(currentIndex,updatedFood)
        //Reseteo el estado de lista de limetnos actual.
        refreshFoods(currentFoods = currentFoods)
    }

    private fun createFood(nombre: String, nombretipo: String, cantidad: String): Food{
        var tipo: TipoUnidad = TipoUnidad.GRAMOS
        TipoUnidad.values().forEach { it ->
            if(it.name.equals(nombretipo)){
                tipo = it
            }
        }
        return Food(
            nombre = nombre,
            tipoUnidad = tipo,
            cantidad = cantidad.toInt(),
            active = true
        )
    }
}

package com.otero.recipetoshop.android.presentation.navigation.screens.despensa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.despensa.ChangeCantidadFood
import com.otero.recipetoshop.Interactors.despensa.DeleteFoods
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.Interactors.despensa.GetFoods
import com.otero.recipetoshop.Interactors.despensa.InsertNewFoodItem
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
    private val deleteFoods: DeleteFoods
): ViewModel(){
    val listState: MutableState<FoodListState> = mutableStateOf(FoodListState())
    val foodState: MutableState<FoodState> = mutableStateOf(FoodState())

    init{
        obtainFoodsCache()
    }


    fun onTriggerEvent(event: FoodListEvents){
        when(event){
            is FoodListEvents.onCantidadChange -> {
                updateCantidadAlimento(event.cantidad, event.food)
            }
            is FoodListEvents.onAddFood -> {
                //Elimino el popup
                listState.value = listState.value.copy(onNewFood = false)
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

    private fun removeFoodsCache() {
        deleteFoods.deletFoods().onEach { dataState ->
            dataState.data?.let {
                listState.value = listState.value.copy(alimentos = listOf())
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun obtainFoodsCache() {
        getFoods.getFoods().onEach { dataState ->
            dataState.data?.let { foods ->
                listState.value = listState.value.copy(alimentos = foods)
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
                appendFood(food)
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateCantidadAlimento(cantidad: String, food: Food){
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
    }

    private fun updateFood(updatedFood: Food, food: Food) {
        val currentFoods = ArrayList(listState.value.alimentos)
        val currentIndex = currentFoods.indexOf(food)
        currentFoods.set(currentIndex,updatedFood)
        //Reseteo el estado de lista de limetnos actual.
        listState.value = listState.value.copy(alimentos = listOf())
        listState.value = listState.value.copy(alimentos = currentFoods)
    }

    private fun appendFood(food: Food){
        val currentFoods = ArrayList(listState.value.alimentos)
        currentFoods.add(food)
        listState.value = listState.value.copy(alimentos = currentFoods)
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
            cantidad = cantidad.toInt()
        )
    }
}
@ExperimentalComposeUiApi
@Preview
@Composable
fun ComposanlePreview(){
    NewFoodPopUp(
        onAddFood = { nombre, tipo, cantidad ->
            {}
        },
        listState = remember {mutableStateOf(FoodListState())}
    )
}
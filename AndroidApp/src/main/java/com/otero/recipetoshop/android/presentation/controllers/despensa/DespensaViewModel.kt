package com.otero.recipetoshop.android.presentation.controllers.despensa

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.Common.ActualizarAutoComplete
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.events.despensa.DespensaEventos
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentationlogic.states.despensa.ListaAlimentosState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class DespensaViewModel
@Inject
constructor(
    private val alimentoCantidadChange: AlimentoCantidadChange,
    private val insertNewAlimento: InsertNewAlimento,
    private val getAlimentos: GetAlimentos,
    private val deleteAlimentos: DeleteAlimentos,
    private val deleteAlimento: DeleteAlimento,
    private val onCLickAlimento: OnCLickAlimento,
    private val actualizarAutoComplete: ActualizarAutoComplete
): ViewModel(){
    val despensaState: MutableState<ListaAlimentosState> = mutableStateOf(ListaAlimentosState())

    init{
        obtainFoodsCache()
    }

    fun onTriggerEvent(event: DespensaEventos){
        when(event){
            is DespensaEventos.onClickAutoCompleteElement -> {
                despensaState.value = despensaState.value.copy(resultadoAutoCompletado = emptyList())
            }
            is DespensaEventos.onAutoCompleteChange -> {
                updateAutocomplete(event.nombre)
            }
            is DespensaEventos.onCantidadChange -> {
                updateAlimento(alimento = event.alimento, cantidad = event.cantidad, )
            }
            is DespensaEventos.onAddAlimento -> {
                val alimento = createAlimento(
                    nombre = event.nombre,
                    nombretipo = event.tipo,
                    cantidad = event.cantidad
                )
                insertFoodItem(alimento = alimento)
            }
            is DespensaEventos.onSelectedNestedMenuItem -> {
                if(event.option.equals("Eliminar Despensa")){
                    removeAlimentosCache()
                }
            }
            is DespensaEventos.onAlimentoDelete -> {
                deleteAlimento(event.alimento)
            }
            is DespensaEventos.onClickAlimento -> {
                updateAlimento(alimento = event.alimento, active = event.active)
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

    private fun updateAutocomplete(nombre: String) {
        val response = actualizarAutoComplete.actualizarAutoComplete(query = nombre)
        despensaState.value = despensaState.value.copy(resultadoAutoCompletado = response)
    }

    private fun deleteAlimento(alimento: Alimento) {
        deleteAlimento.deleteAlimento(alimento = alimento).onEach { dataState ->
            dataState.data?.let {
                //Elimino de caché el alimento.
                //ELimino del estado actual el alimento
                println("Ha llegado hasta el modelo vista")
                val currentAlimentos = ArrayList(despensaState.value.allAlimentos)
                deleteAlimento.deleteAlimento(alimento)
                currentAlimentos.remove(alimento)
                despensaState.value = despensaState.value.copy(allAlimentos = currentAlimentos)
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)

    }

    private fun removeAlimentosCache() {
        deleteAlimentos.deleteAlimentos().onEach { dataState ->
            dataState.data?.let {
                despensaState.value = despensaState.value.copy(allAlimentos = listOf())
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun obtainFoodsCache() {
        getAlimentos.getAlimentos().onEach { dataState ->
            dataState.data?.let { alimentos ->
                despensaState.value = despensaState.value.copy(allAlimentos = alimentos)
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    private fun insertFoodItem(alimento: Alimento){
        //Actualizo el estado del viewModel confoeme llegan daaStates asíncronos.
        insertNewAlimento.insertAlimento(
            alimento = alimento
        ).onEach { dataState ->
            dataState.data?.let { food ->
                rePrintAlimentos()
            }
            dataState.message?.let { message ->
                //handleError(message)
            }
        }.launchIn(viewModelScope)
    }

    //Es importante que cada vez que se inserte un nuevo elemnto se refrescque la lista obteniendolo de cache parar así obteneer los items cons sus id (auotincrementados).
    private fun rePrintAlimentos() {
        getAlimentos.getAlimentos().onEach { dataState ->
            dataState.data?.let { currentAlimentos ->
                despensaState.value = despensaState.value.copy(allAlimentos = currentAlimentos)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateAlimento(cantidad: String? = null, alimento: Alimento, active: Boolean = true){
        if(cantidad != null){
            //Actualizo el estado del viewModel confoeme llegan daaStates asíncronos.
            alimentoCantidadChange.updateCantidad(
                cantidad = cantidad,
                alimento = alimento
            ).onEach { dataState ->
                dataState.data?.let { updatedAlimento ->
                    updateAlimento(updatedAlimento,alimento)
                }
                dataState.message?.let { message ->
                    //handleError(message)
                }
            }.launchIn(viewModelScope)
        } else {
            onCLickAlimento.onCLickAlimento(alimento = alimento, active = active).onEach { dataState ->
                dataState.data?.let { updatedAlimento ->
                    updateAlimento(updatedAlimento,alimento)
                }
            }
        }
    }

   private fun refreshAlimentos(currentAlimentos: List<Alimento>){
       despensaState.value = despensaState.value.copy(allAlimentos = listOf())
       despensaState.value = despensaState.value.copy(allAlimentos = currentAlimentos)
   }

    private fun updateAlimento(updatedAlimento: Alimento, alimento: Alimento) {
        val currentFoods = ArrayList(despensaState.value.allAlimentos)
        val currentIndex = currentFoods.indexOf(alimento)
        currentFoods.set(currentIndex,updatedAlimento)
        //Reseteo el estado de lista de limetnos actual.
        refreshAlimentos(currentAlimentos = currentFoods)
    }

    private fun createAlimento(nombre: String, nombretipo: String, cantidad: String): Alimento{
        var tipo: TipoUnidad = TipoUnidad.GRAMOS
        TipoUnidad.values().forEach { it ->
            if(it.name.equals(nombretipo)){
                tipo = it
            }
        }
        return Alimento(
            nombre = nombre,
            tipoUnidad = tipo,
            cantidad = cantidad.toInt(),
            active = true
        )
    }
}

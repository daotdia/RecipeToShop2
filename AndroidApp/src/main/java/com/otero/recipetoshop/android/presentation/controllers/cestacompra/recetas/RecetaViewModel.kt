package com.otero.recipetoshop.android.presentation.controllers.cestacompra.recetas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.Common.ActualizarAutoComplete
import com.otero.recipetoshop.Interactors.cestascompra.recetas.AddIngredienteReceta
import com.otero.recipetoshop.Interactors.cestascompra.recetas.DeleteIngredienteReceta
import com.otero.recipetoshop.Interactors.cestascompra.recetas.EditIngrediente
import com.otero.recipetoshop.Interactors.cestascompra.recetas.GetDatosReceta
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.cestacompra.receta.RecetaEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecetaViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val addIngredienteReceta: AddIngredienteReceta,
    private val getDatosReceta: GetDatosReceta,
    private val deleteIngredienteReceta: DeleteIngredienteReceta,
    private val actualizarAutoComplete: ActualizarAutoComplete,
    private val editIngrediente: EditIngrediente
): ViewModel() {
    val recetaState = mutableStateOf(RecetaState())

    init {
        try {
            savedStateHandle.get<Int>("recetaid")?.let{ recetaid ->
                viewModelScope.launch {
                    recetaState.value = recetaState.value.copy(receta_id = recetaid)
                    if (recetaState.value.receta_id == -1){
                        throw Exception("ID receta == -1")
                    }
                }
            }
            savedStateHandle.get<Int>("cestacompraid")?.let{ cestacompraid ->
                viewModelScope.launch {
                    recetaState.value = recetaState.value.copy(cestaCompra_id = cestacompraid)
                    if (recetaState.value.receta_id == -1){
                        throw Exception("ID cestacompraid == -1")
                    }
                }
            }
            //Pinto los ingredientes cogidos.
            reprintContenidoReceta(id_receta = recetaState.value.receta_id)
        } catch (e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }
    }

    fun onTriggerEvent(event: RecetaEventos){
        when(event){
            is RecetaEventos.onAutocompleteRecetaChange -> {
                val response = actualizarAutoComplete.actualizarAutoComplete(query = event.query)
                recetaState.value = recetaState.value.copy(resultadoAutoComplete = response)
            }
            is RecetaEventos.onClickAutocompleteReceta -> {
                recetaState.value = recetaState.value.copy(resultadoAutoComplete = emptyList())
            }
            is RecetaEventos.onaAddIngrediente -> {
                addAlimentoReceta(nombre = event.nombre, cantidad = event.cantidad, tipoUnidad = event.tipoUnidad)
            }
            is RecetaEventos.onDeleteIngrediente -> {
                deleteIngredienteReceta(id_ingrediente = event.id_ingrediente)
            }
            is RecetaEventos.onEditIngrediente -> {
                editarIngrediente(
                    id_alimento = event.id_ingrediente,
                    nombre = event.nombre,
                    cantidad = event.cantidad,
                    tipo = event.tipoUnidad
                )
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun editarIngrediente(id_alimento: Int, nombre: String, cantidad: Int, tipo: String) {
        editIngrediente.editIngrediente(
            id_receta = recetaState.value.receta_id,
            id_cestaCompra = recetaState.value.cestaCompra_id,
            id_alimento = id_alimento,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = TipoUnidad.parseTipoUnidad(tipo)
        ).onEach { dataState ->
            //Actualizo la receta.
            reprintContenidoReceta(recetaState.value.receta_id)
        }.launchIn(viewModelScope)
    }

    private fun deleteIngredienteReceta(id_ingrediente: Int) {
        deleteIngredienteReceta.deleteIngredientereceta(
            id_ingrediente = id_ingrediente
        ).onEach { dataState ->
            dataState.data?.let {
                //Reseteo la lista de ingredientes con el nuevo ingrediente añadido.
                reprintContenidoReceta(id_receta = recetaState.value.receta_id)
            }
        }.launchIn(viewModelScope)
    }

    private fun addAlimentoReceta(nombre: String, cantidad: Int, tipoUnidad: TipoUnidad) {
        addIngredienteReceta.addIngredienteReceta(
            id_cestaCompra = recetaState.value.cestaCompra_id,
            id_receta = recetaState.value.receta_id,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad
        ).onEach { dataState ->
            dataState.data?.let {
                //Reseteo la lista de ingredientes con el nuevo ingrediente añadido.
                reprintContenidoReceta(id_receta = recetaState.value.receta_id)
            }
        }.launchIn(viewModelScope)
    }

    private fun reprintContenidoReceta(id_receta: Int) {
        //Reseteo la lista de ingredientes.
        recetaState.value = recetaState.value.copy(ingredientes = listOf())

        //Obtengo los ingredientes de la Base de datos renovados.
        getDatosReceta.getDatosReceta(id_receta = id_receta).onEach { dataState ->
            dataState.data?.let { receta ->
                //Los refresco de nuevo.
                recetaState.value = recetaState.value.copy(
                    cestaCompra_id = receta.id_cestaCompra,
                    receta_id = receta.id_Receta!!,
                    nombre = receta.nombre,
                    ingredientes = receta.ingredientes,
                    cantidad = receta.cantidad.toString(),
                    imagen = if(receta.imagenSource == null) "" else receta.imagenSource!!,
                )
            }
        }.launchIn(viewModelScope)
    }

}
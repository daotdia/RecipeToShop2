package com.otero.recipetoshop.android.presentation.controllers.cestacompra

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otero.recipetoshop.Interactors.Common.ActualizarAutoComplete
import com.otero.recipetoshop.Interactors.cestascompra.GetCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.*
import com.otero.recipetoshop.Interactors.cestascompra.recetas.GetRecetasFavoritas
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.events.cestacompra.CestaCompraEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.CestaCompraState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CestaCompraViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val onEnterCestaCompra: OnEnterCestaCompra,
    private val addAlimentoCestaCompra: AddAlimentoCestaCompra,
    private val deleteAlimentoCestaCompra: DeleteAlimentoCestaCompra,
    private val deleteRecetaCestaCompra: DeleteRecetaCestaCompra,
    private val updateRecetaCestaCompra: UpdateRecetaCestaCompra,
    private val updateAlimentoCestaCompra: UpdateAlimentoCestaCompra,
    private val getCestaCompra: GetCestaCompra,
    private val actualizarAutoComplete: ActualizarAutoComplete,
    private val getRecetasFavoritas: GetRecetasFavoritas
): ViewModel() {
    val cestaCompraState = mutableStateOf(CestaCompraState())
    init {
        //Obtengo en primer lugar el id de la lista de recetas actual desde navegacion.
        try {
            savedStateHandle.get<Int>("cestacompraid")?.let{ cestaCompraID ->
                viewModelScope.launch {
                    cestaCompraState.value = cestaCompraState.value.copy(id_cestaCompra_actual = cestaCompraID)
                    if (cestaCompraState.value.id_cestaCompra_actual == null){
                        throw Exception("ID listarecteaactual == null")
                    }
                    rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
                }
            }
        } catch (e: Exception){
            println("No se ha podido obtener el identificador de la lista de recetas: " + e.message)
        }
        //Obtengo las recetas favoritas
        try{
            getRecetasFavoritas.getRecetasFavoritas().onEach { dataState ->
                dataState.data?.let { favoritas ->
                    cestaCompraState.value = cestaCompraState.value.copy(recetasFavoritas = favoritas)
                }
            }.launchIn(viewModelScope)
        }catch (e: Exception){

        }
    }

    fun onTriggerEvent(event: CestaCompraEventos){
        when(event){
            is CestaCompraEventos.onReducirCantidadReceta -> {
                if (event.receta.cantidad > 0) {
                    updateReceta(receta = event.receta, cantidad = event.receta.cantidad - 1)
                }
            }
            is CestaCompraEventos.onAumentarCantidadReceta -> {
                updateReceta(receta = event.receta, cantidad = event.receta.cantidad + 1)
            }
            is CestaCompraEventos.onAddReceta -> {
                val newReceta = Receta(
                    active = true,
                    cantidad = event.cantidad,
                    nombre = event.nombre,
                    user = true,
                    id_cestaCompra = cestaCompraState.value.id_cestaCompra_actual!!
                )
                addReceta(newReceta)
            }
            is CestaCompraEventos.onClickAutocompleteAlimento -> {
                cestaCompraState.value = cestaCompraState.value.copy(resultadosAutoCompleteAlimentos = emptyList())
            }
            is CestaCompraEventos.onAutocompleteAlimentoChange -> {
                val response = actualizarAutoComplete.actualizarAutoComplete(event.query)
                cestaCompraState.value = cestaCompraState.value.copy(resultadosAutoCompleteAlimentos = response)
            }
            is CestaCompraEventos.onAddAlimento -> {
                addAlimentoListaReceta(event.nombre, event.cantidad, event.tipoUnidad)
            }
            is CestaCompraEventos.onDeleteAlimento -> {
                deleteAlimento(event.alimento)
            }
            is CestaCompraEventos.onDeleteReceta -> {
                deleteReceta(event.receta)
            }
            is CestaCompraEventos.onRecetaClick ->  {
                //Que hacer cuando se de a la receta,
            }
            is CestaCompraEventos.onAlimentoClick -> {
                updateAlimento(alimento = event.alimento, active = event.active)
            }
            is CestaCompraEventos.onUpdateRecetaActive -> {
                updateReceta(receta = event.receta, active = event.active)
            }
            is CestaCompraEventos.onUpdateRecetaFavorita -> {
                val receta = event.receta.copy(isFavorita = event.isFavorita)
                updateReceta(receta)
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    private fun addReceta(newReceta: Receta) {
        updateRecetaCestaCompra.updateRecetaCestaCompra(receta = newReceta, active = newReceta.active, cantidad = newReceta.cantidad).onEach { dataState ->
            dataState.data?.let { id ->
                cestaCompraState.value = cestaCompraState.value.copy(id_receta_actual = id)
                rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
            }
        }.launchIn(viewModelScope)
    }


    private fun updateAlimento(alimento: Alimento, active: Boolean, cantidad: Int? = null) {
        updateAlimentoCestaCompra.updateAlimentoCestaCompra(
            alimento = alimento,
            active = active,
            cantidad = cantidad).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateReceta(receta: Receta, active: Boolean = true, cantidad: Int? = null) {
        updateRecetaCestaCompra.updateRecetaCestaCompra(receta = receta, active = active, cantidad = cantidad).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteReceta(receta: Receta) {
        deleteRecetaCestaCompra.deleteRecetaCestaCompra(receta = receta).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAlimento(alimento: Alimento) {
        deleteAlimentoCestaCompra.deleteAlimentoCestaCompra(alimento = alimento).onEach { dataState ->
            dataState.data?.let {
                rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
            }
        }.launchIn(viewModelScope)
    }

    private fun addAlimentoListaReceta(nombre: String, cantidad: Int, tipoUnidad: TipoUnidad) {
        val alimento = Alimento(
            id_cestaCompra = cestaCompraState.value.id_cestaCompra_actual!!,
            nombre = nombre,
            cantidad = cantidad,
            tipoUnidad = tipoUnidad,
            active = true
        )
        addAlimentoCestaCompra.insertAlimentosCestaCompra(alimento = alimento).onEach { dataState ->
            dataState.data?.let { exito ->
                if(exito){
                    rePrintElementosDeListaRecetas(cestaCompraState.value.id_cestaCompra_actual!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    //Metodo para imprimir los elementos guardados en la lista de recetas en cache.
    private fun rePrintElementosDeListaRecetas(id_listaRecetas: Int) {
        //Reseteo el estado actual de recetas y alimentos (tantos los activados como los inactivados).
        cestaCompraState.value = cestaCompraState.value.copy(recetasActive = listOf())
        cestaCompraState.value = cestaCompraState.value.copy(recetasInactive = listOf())
        cestaCompraState.value = cestaCompraState.value.copy(recetasFavoritas = listOf())

        //Actualizo las recetas favoritas
        getRecetasFavoritas.getRecetasFavoritas().onEach { dataState ->
            dataState.data?.let { favoritas ->
                cestaCompraState.value = cestaCompraState.value.copy(recetasFavoritas = favoritas)
            }
        }.launchIn(viewModelScope)

        //Obtener las recetas de la lista de recetas clicckada.
        onEnterCestaCompra.getRecetasCestaCompra(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let { recetas: Pair<List<Receta>?, List<Receta>?> ->
                if(recetas.first != null){
                    cestaCompraState.value = cestaCompraState.value.copy(recetasActive = recetas.first!!)
                }
                if(recetas.second != null){
                    cestaCompraState.value = cestaCompraState.value.copy(recetasInactive = recetas.second!!)
                }
            }
            //Actualizo las recetas activas e inactivas actuales para poder pintarlo.
            val recetas_actuales: ArrayList<Receta> = arrayListOf()
            recetas_actuales.addAll(cestaCompraState.value.recetasActive)
            recetas_actuales.addAll(cestaCompraState.value.recetasInactive)
            //Necesito añadir sólo las recetas favoritas que no sean de la propia cesta de la compra.
            val recetasFavoritas = cestaCompraState.value.recetasFavoritas
            val recetasFavoritasExternas = recetasFavoritas.filter {
                it.id_cestaCompra != cestaCompraState.value.id_cestaCompra_actual
            }
            val recetasFaboritasExternasPreparadas: ArrayList<Receta> = arrayListOf()
            for (receta in recetasFavoritasExternas){
                recetasFaboritasExternasPreparadas.add(receta.copy(active = false))
            }
            val recetasFaboritasExternasPreparadasDistintas: ArrayList<Receta> = arrayListOf()
            for(receta in recetasFaboritasExternasPreparadas){
                val recetas_aux = recetas_actuales.filter {
                    it.nombre == receta.nombre
                }
                if (recetas_aux.isEmpty()){
                    recetasFaboritasExternasPreparadasDistintas.add(receta)
                }
            }
            recetas_actuales.addAll(recetasFaboritasExternasPreparadasDistintas)
            cestaCompraState.value = cestaCompraState.value.copy(allrecetas = recetas_actuales)
        }.launchIn(viewModelScope)

        cestaCompraState.value = cestaCompraState.value.copy(alimentosActive = listOf())
        cestaCompraState.value = cestaCompraState.value.copy(alimentosInactive = listOf())
        //Obtener los alimentos de la lista de recetas clicada.
        onEnterCestaCompra.getAlimentosCestaCompra(id_listaReceta = id_listaRecetas).onEach { dataState ->
            dataState.data?.let {  alimentos: Pair<List<Alimento>?, List<Alimento>?> ->
                println("Numero de alimentos inactivos: " + alimentos.second?.size)
                if(alimentos.first != null){
                    cestaCompraState.value = cestaCompraState.value.copy(alimentosActive = alimentos.first!!)
                }
                if(alimentos.second != null){
                    cestaCompraState.value = cestaCompraState.value.copy(alimentosInactive = alimentos.second!!)
                }
            }
            //Actualizo los alimentos activos e inactivos para poder pintarlos.
            val alimentos_actuales: ArrayList<Alimento> = arrayListOf()
            alimentos_actuales.addAll(cestaCompraState.value.alimentosActive)
            alimentos_actuales.addAll(cestaCompraState.value.alimentosInactive)
            println("Numero de alimentos inactivos guardados en esstado: " + cestaCompraState.value.alimentosInactive)
            cestaCompraState.value = cestaCompraState.value.copy(allAlimentos = alimentos_actuales)
        }.launchIn(viewModelScope)
    }
}
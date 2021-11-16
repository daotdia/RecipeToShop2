package com.otero.recipetoshop.android.presentation.navigation.screens.recetas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.recetas.AddNewListaReceta
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecetasListViewModel
@Inject
constructor(
    private val addNewListaReceta: AddNewListaReceta
): ViewModel(){
    val recetasState: MutableState<RecetasListState> = mutableStateOf(RecetasListState())
    fun onTriggerEvent(event: RecetasListEvents){
        when(event){
            is RecetasListEvents.onAddreceta -> {
                addReceta()
            }
            else -> {
                throw Exception("ERROR")
            }
        }
    }

    fun addReceta() {
    }
}



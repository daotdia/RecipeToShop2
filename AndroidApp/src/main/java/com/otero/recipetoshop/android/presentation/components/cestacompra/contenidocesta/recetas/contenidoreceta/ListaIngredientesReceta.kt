package com.otero.recipetoshop.android.presentation.components.cestacompra.contenidocesta.recetas.contenidoreceta

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.otero.recipetoshop.android.presentation.components.util.cards.IngredienteCard
import com.otero.recipetoshop.events.cestacompra.receta.RecetaEventos
import com.otero.recipetoshop.presentationlogic.states.recetas.RecetaState
/*
Este es el componente que implementa la lista de ingredientes dentro de una receta.
 */
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun ListaIngredientesReceta(
    recetaState: MutableState<RecetaState>,
    onTriggerEvent: (RecetaEventos) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Fixed(3)
    ){
        items(
            items = recetaState.value.ingredientes,
        ) { item ->
            IngredienteCard(
                nombre = item.nombre,
                cantidad = item.cantidad,
                tipoUnidad = item.tipoUnidad,
                onDelete = {
                    onTriggerEvent(RecetaEventos.onDeleteIngrediente(item.id_alimento!!))
                },
                onClickAlimento = { id_alimento, nombre, tipo, cantidad ->
                    //LLamo al viewmodel para que actualice el ingrediente
                    onTriggerEvent(
                        RecetaEventos.onEditIngrediente(
                            id_ingrediente = id_alimento,
                            nombre = nombre,
                            cantidad = cantidad.toInt(),
                            tipoUnidad = tipo
                        )
                    )
                },
                alimento_actual = item

            )
        }
    }
}